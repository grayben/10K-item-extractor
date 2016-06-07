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
import java.util.function.Predicate;

import static org.junit.Assert.fail;

/**
 * Created by beng on 4/06/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class ElectorTest {

    private Elector electorSUT;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        Predicate<ScoredTextElement> predicate = element -> true;
        electorSUT = new Elector(predicate);
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
    public void test_ElectedTextGeneratesSameOutputAsElect_WithSameNominator
            () throws Exception {
        fail("Not implemented");
    }

    @Ignore
    @Test
    public void test_ElectedTextGeneratedCorrectOutput_OnRandomisedInputs
            () throws Exception {
        fail("Not implemented");
    }



}