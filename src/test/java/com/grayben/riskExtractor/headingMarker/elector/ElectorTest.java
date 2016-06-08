package com.grayben.riskExtractor.headingMarker.elector;

import com.grayben.riskExtractor.headingMarker.Elector;
import com.grayben.riskExtractor.headingMarker.Nominator;
import com.grayben.riskExtractor.headingMarker.nominator.NominatorTest;
import com.grayben.riskExtractor.helpers.ScoredTextGenerator;
import com.grayben.riskExtractor.htmlScorer.ScoredText;
import org.apache.commons.collections4.list.SetUniqueList;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;

/**
 * Created by beng on 4/06/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class ElectorTest {

    private Function<Nominator.NominatedText, List<Integer>> computeElecteeIndices;
    private Elector electorSUT;
    private Nominator nominator;
    private ScoredText scoredText;

    public static Elector buildDefaultElector(){
        return new Elector(buildDefaultElectorFunction());
    }

    public static Function<Nominator.NominatedText, List<Integer>> buildDefaultElectorFunction(){
        return nominatedText -> {
            List<String> text = nominatedText.getUnmodifiableText().getStringList();
            List<Integer> nomineeIndices = nominatedText.getNomineeIndices();
            int highestHashcode = Integer.MIN_VALUE;
            int bestIndex = 0;
            for (int index :
                    nomineeIndices) {
                String element = text.get(index);
                int hashCode = element.hashCode();
                if (hashCode > highestHashcode){
                    bestIndex = index;
                    highestHashcode = hashCode;
                }
            }
            List<Integer> electeeIndices = new ArrayList<>();
            electeeIndices.add(bestIndex);
            return electeeIndices;

        };
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        this.computeElecteeIndices = buildDefaultElectorFunction();
        this.electorSUT = buildDefaultElector();
        this.scoredText = ScoredTextGenerator.randomScoredTextWithDefaultScorers();
        this.nominator = NominatorTest.buildDefaultNominator();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void test_InitThrowsNullPointerException_WhenFunctionIsNull
            () throws Exception {
        thrown.expect(NullPointerException.class);
        electorSUT = new Elector(null);
    }

    @Test
    public void test_ElectThrowsNullPointerException_WhenNominatedTextIsNull
            () throws Exception {
        thrown.expect(NullPointerException.class);
        electorSUT.elect(null);
    }

    @Test
    public void test_ElectedTextThrowsNullPointerException_WhenNominatorIsNull
            () throws Exception {
        thrown.expect(NullPointerException.class);
        electorSUT.elect(null, new ScoredText());
    }

    @Test
    public void test_ElectThrowsNullPointerException_WhenScoredTextIsNull
            () throws Exception {
        thrown.expect(NullPointerException.class);
        Nominator nominator = NominatorTest.buildDefaultNominator();
        electorSUT.elect(nominator, null);
    }

    @Test
    public void test_ElectedTextGeneratedCorrectOutput_OnInput1
            () throws Exception {

        Nominator.NominatedText nominatedText = nominator.nominate(scoredText);
        List<Integer> expectedElecteeIndices = this.computeElecteeIndices.apply(nominatedText);
        Elector.ElectedText expected = new Elector.ElectedText(
                nominatedText,
                SetUniqueList.setUniqueList(expectedElecteeIndices)
        );

        Elector.ElectedText actual = this.electorSUT.elect(nominatedText);

        assertEquals(expected, actual);
    }

    @Test
    public void test_ElectedTextGeneratesSameOutputAsElect_WithSameNominator
            () throws Exception {
        Nominator.NominatedText nominatedText = nominator.nominate(scoredText);
        assertEquals(electorSUT.elect(nominatedText), electorSUT.elect(nominator, scoredText));
    }



}