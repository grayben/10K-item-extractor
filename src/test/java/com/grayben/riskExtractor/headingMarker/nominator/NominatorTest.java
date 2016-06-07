package com.grayben.riskExtractor.headingMarker.nominator;

import com.grayben.riskExtractor.headingMarker.Nominator;
import com.grayben.riskExtractor.helpers.ElementScorerSetFunction;
import com.grayben.riskExtractor.helpers.ScoredTextGenerator;
import com.grayben.riskExtractor.htmlScorer.ScoredText;
import com.grayben.riskExtractor.htmlScorer.ScoredTextElement;
import com.grayben.riskExtractor.htmlScorer.partScorers.Scorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.elementScorers.SegmentationElementScorer;
import org.apache.commons.collections4.list.SetUniqueList;
import org.jsoup.nodes.Element;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;
import java.util.function.Predicate;

import static org.junit.Assert.assertEquals;

/**
 * Created by beng on 5/06/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class NominatorTest {

    private Nominator nominatorSUT;

    private ScoredText input;

    private Predicate<ScoredTextElement> isNominee;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        this.isNominee = scoredTextElement -> (scoredTextElement.hashCode() % 2 == 0);
        this.nominatorSUT = new Nominator(this.isNominee);
        Set<ElementScorerSetFunction.Content> contents = new HashSet<>();
        contents.add(ElementScorerSetFunction.Content.EMPHASIS_ELEMENT_SCORER);
        contents.add(ElementScorerSetFunction.Content.SEGMENTATION_ELEMENT_SCORER);
        Set<Scorer<Element>> elementScorerSet = new ElementScorerSetFunction().apply(contents);
        this.input = ScoredTextGenerator.randomScoredText(new Random(42L), elementScorerSet);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void test_InitThrowsNullPointerException_WhenPredicateIsNull
            () throws Exception {
        thrown.expect(NullPointerException.class);
        nominatorSUT = new Nominator(null);
    }

    @Test
    public void test_NominateThrowsNullPointerException_WhenScoredTextIsNull
            () throws Exception {
        thrown.expect(NullPointerException.class);
        nominatorSUT.nominate(null);
    }

    @Test
    public void test_NominateReturnsExpectedResult_AccordingToAlternateImplementation
            () throws Exception {

        this.nominatorSUT = new Nominator(isNominee);

        List<Integer> nomineeIndices = new ArrayList<>();
        List<ScoredTextElement> scoredTextElements = this.input.getList();
        for (int i = 0; i < scoredTextElements.size(); i++){
            ScoredTextElement element = scoredTextElements.get(i);
            if (isNominee.test(element)){
                nomineeIndices.add(i);
            }
        }

        Nominator.NominatedText expected = new Nominator.NominatedText(
                this.input.getText(), SetUniqueList.setUniqueList(nomineeIndices)
        );

        Nominator.NominatedText actual = nominatorSUT.nominate(input);

        assertEquals(expected, actual);
    }

    @Test
    public void test_NominateReturnsExpectedResult_WithInput1
            () throws Exception {

        Random random = new Random(1024L);

        input = new ScoredText();

        String label = SegmentationElementScorer.SCORE_LABEL;
        Map<String, Integer> positiveScore = new HashMap<>();
        positiveScore.put(label, 1);
        Map<String, Integer> negativeScore = new HashMap<>();
        negativeScore.put(label, 0);
        Predicate<ScoredTextElement> isNominee = element -> element.getScores().get(label) > 0;

        List<String> expectedStringList = new ArrayList<>();
        List<Integer> expectedNomineeIndices = new ArrayList<>();

        for(int i = 0; i < 50; i++){
            String text = Integer.toString(i);
            expectedStringList.add(text);
            if (random.nextInt() % 4 == 0){
                input.add(new ScoredTextElement(text, positiveScore));
                expectedNomineeIndices.add(i);
            } else {
                input.add(new ScoredTextElement(text, negativeScore));
            }
        }

        Nominator.NominatedText expectedOutput = new Nominator.NominatedText(
                expectedStringList, SetUniqueList.setUniqueList(expectedNomineeIndices)
        );

        this.nominatorSUT = new Nominator(isNominee);
        Nominator.NominatedText actualOutput = this.nominatorSUT.nominate(input);

        assertEquals(expectedOutput, actualOutput);
    }

}