package com.grayben.riskExtractor.htmlScorer.partScorers.elementScorers;

import com.grayben.riskExtractor.htmlScorer.partScorers.Scorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.tagScorers.TagSegmentationScorer;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static com.grayben.riskExtractor.helpers.TestHelper.*;
import static junit.framework.Assert.assertEquals;

/**
 * Created by beng on 28/11/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class SegmentationElementScorerTest
        extends ElementScorerBaseTest {

    SegmentationElementScorer elementScorerSUT;

    public Element elementToBeScoredMock;

    @Before
    public void setUp() throws Exception {
        TagSegmentationScorer tagScorer
                = new TagSegmentationScorer(TagSegmentationScorer.defaultMap());
        elementScorerSUT = new SegmentationElementScorer(tagScorer);
        super.setScorerSUT(elementScorerSUT);
        super.setArgumentToBeScored(elementToBeScoredMock);
        super.setUp();
    }

    @After
    public void tearDown() throws Exception {
        elementScorerSUT = null;
        super.tearDown();
    }

    @Override
    @Test
    public void
    test_ScoreReturnsInteger_WhenArgumentIsNotEmpty() throws Exception {
        Tag tagStub = stubTag("a-tag-name");
        Attributes attributeStubs = dummyAttributes();
        elementToBeScoredMock = stubElement(tagStub, attributeStubs);

        Integer returned = elementScorerSUT.score(elementToBeScoredMock);

        assertEquals(Integer.class, returned.getClass());
    }

    @Override
    @Test
    public void
    test_ScoreGivesExpectedResult_WhenSimpleInput() throws Exception {
        //////////////////////////////////////////////////////////////////////////////
        //// GENERATE TARGET INPUT/OUTPUT PAIRS //////////////////////////////////////
        Map<Element, Integer> targetOutput = new HashMap<>();

        //add Map.Entry<Element, Integer> entries to targetOutput
        //based on the tagEmphasisScoreMap
        Map<Element, Integer> scoresMapBasedOnTags
                = stubElementsAndScoresByTagScores(
                TagSegmentationScorer.defaultMap()
        );
        targetOutput.putAll(scoresMapBasedOnTags);

        //////////////////////////////////////////////////////////////////////////////
        //// GENERATE DUMMY INPUT/OUTPUT PAIRS ///////////////////////////////////////
        Map<Element, Integer> nonTargetOutput = new HashMap<>();
        nonTargetOutput.put(
                stubElement(
                        stubTag("don't use this!"),
                        dummyAttributes()
                ),
                Scorer.DEFAULT_SCORE
        );
        nonTargetOutput.put(
                stubElement(
                        stubTag("really, don't use it, it's not a good tag."),
                        dummyAttributes()
                ),
                Scorer.DEFAULT_SCORE
        );

        Map<Element, Integer> expectedOutput = new HashMap<>();
        expectedOutput.putAll(targetOutput);
        expectedOutput.putAll(nonTargetOutput);

        //////////////////////////////////////////////////////////////////////////////
        //// RUN THE OUTPUT GENERATION ///////////////////////////////////////////////

        Map<Element, Integer> actualOutputs = new HashMap<>();
        for (Element input :
                expectedOutput.keySet()) {
            actualOutputs.put(input, elementScorerSUT.score(input));
        }

        //////////////////////////////////////////////////////////////////////////////
        //// COMPARE INPUT AND OUTPUT ////////////////////////////////////////////////
        assertEquals(expectedOutput, actualOutputs);
    }

    @Test
    public void
    test_ScoreThrowsNullPointerException_WhenTagIsNull() throws Exception {
        thrown.expect(NullPointerException.class);

        Attributes attributes = dummyAttributes();

        elementToBeScoredMock = stubElement(null, attributes);

        elementScorerSUT.score(elementToBeScoredMock);
    }

    @Test
    public void
    test_ScoreThrowsNullPointerException_WhenAttributesIsNull() throws Exception {
        thrown.expect(NullPointerException.class);

        elementToBeScoredMock = stubElement(stubTag("font"), null);

        elementScorerSUT.score(elementToBeScoredMock);
    }
}