package com.grayben.riskExtractor.headingMarker.elector;

import com.grayben.riskExtractor.headingMarker.Elector;
import com.grayben.riskExtractor.headingMarker.Nominator;
import com.grayben.riskExtractor.htmlScorer.ScoredText;
import com.grayben.riskExtractor.htmlScorer.ScoredTextElement;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static org.junit.Assert.fail;

/**
 * Created by beng on 4/06/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class ElectorTest {

    private Function<Nominator.NominatedText, List<Integer>> computeElecteeIndices;
    private Elector electorSUT;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        this.computeElecteeIndices = nominatedText -> {
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
        electorSUT = new Elector(this.computeElecteeIndices);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Ignore
    @Test
    public void test_InitThrowsNullPointerException_WhenFunctionIsNull
            () throws Exception {
        thrown.expect(NullPointerException.class);
        electorSUT = new Elector(null);
    }

    @Ignore
    @Test
    public void test_ElectThrowsNullPointerException_WhenNominatedTextIsNull
            () throws Exception {
        thrown.expect(NullPointerException.class);
        electorSUT.elect(null);
    }

    @Ignore
    @Test
    public void test_ElectedTextThrowsNullPointerException_WhenNominatorIsNull
            () throws Exception {
        thrown.expect(NullPointerException.class);
        electorSUT.electedText(null, new ScoredText());
    }

    @Ignore
    @Test
    public void test_ElectedTextThrowsNullPointerException_WhenScoredTextIsNull
            () throws Exception {
        thrown.expect(NullPointerException.class);
        Function<ScoredText, List<Integer>> getNomineeIndices = scoredText -> {
            List<Integer> indices = new ArrayList<>();
            List<ScoredTextElement> scoredTextElements = scoredText.getList();
            for (int i = 0; i <  scoredTextElements.size(); i++){
                ScoredTextElement element = scoredTextElements.get(i);
                if (element.hashCode() % 4 == 0){
                    indices.add(i);

                }
            }
            return indices;
        };

        electorSUT.electedText(new Nominator(getNomineeIndices), null);
    }

    @Ignore
    @Test
    public void test_ElectedTextGeneratedCorrectOutput_OnRandomisedInputs
            () throws Exception {
        fail("Not implemented");
    }

    @Ignore
    @Test
    public void test_ElectedTextGeneratedCorrectOutput_OnInput1
            () throws Exception {
        fail("Not implemented");
    }

    @Ignore
    @Test
    public void test_ElectedTextGeneratesSameOutputAsElect_WithSameNominator
            () throws Exception {
        fail("Not implemented");
    }



}