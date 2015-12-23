package com.grayben.riskExtractor.htmlScorer.partScorers.elementScorers;

import com.grayben.riskExtractor.htmlScorer.partScorers.Scorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.tagScorers.TagAndAttributeScorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.tagScorers.TagEmphasisScorer;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static com.grayben.riskExtractor.htmlScorer.partScorers.TestHelper.*;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by beng on 28/11/2015.
 */

@RunWith(MockitoJUnitRunner.class)
public class EmphasisElementScorerTest extends ElementScorerBaseTest {

    EmphasisElementScorer emphasisElementScorerSUT;

    @Mock
    public Element elementToBeScoredMock;

    @Before
    public void setUp() throws Exception {
        TagAndAttributeScorer tagAndAttributeScorer
                = new TagAndAttributeScorer(
                TagAndAttributeScorer.defaultMap());
        TagEmphasisScorer tagEmphasisScorer
                = new TagEmphasisScorer(TagEmphasisScorer.defaultMap());
        this.emphasisElementScorerSUT
                = new EmphasisElementScorer(
                tagEmphasisScorer,
                tagAndAttributeScorer
        );
        super.setScorerSUT(this.emphasisElementScorerSUT);
        super.setArgumentToBeScored(elementToBeScoredMock);
        super.setUp();
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void
    test_InitThrowsNullPointerException_WhenTagScorerIsNull
            () throws Exception {
        TagEmphasisScorer scorer1 = null;
        TagAndAttributeScorer scorer2 = new TagAndAttributeScorer(
                TagAndAttributeScorer.defaultMap()
        );

        thrown.expect(NullPointerException.class);

        emphasisElementScorerSUT = new EmphasisElementScorer(scorer1, scorer2);
    }

    @Test
    public void
    test_InitThrowsNullPointerException_WhenTagAndAttributeScorerIsNull
            () throws Exception {
        TagEmphasisScorer scorer1 = new TagEmphasisScorer(
                TagEmphasisScorer.defaultMap()
        );
        TagAndAttributeScorer scorer2 = null;

        thrown.expect(NullPointerException.class);

        emphasisElementScorerSUT = new EmphasisElementScorer(scorer1, scorer2);
    }

    @Override
    @Test
    public void
    test_ScoreReturnsInteger_WhenArgumentIsNotEmpty() throws Exception {
        Tag tag = stubTag("font");
        Attributes attributes = dummyAttributes();

        elementToBeScoredMock = stubElement(tag, attributes);

        Integer returned = emphasisElementScorerSUT.score(elementToBeScoredMock);

        assertNotNull(returned);
    }

    @Test
    public void
    test_ScoreThrowsNullPointerException_WhenTagIsNull() throws Exception {
        thrown.expect(NullPointerException.class);

        Attributes attributes = dummyAttributes();

        elementToBeScoredMock = stubElement(null, attributes);

        emphasisElementScorerSUT.score(elementToBeScoredMock);
    }

    @Test
    public void
    test_ScoreThrowsNullPointerException_WhenAttributesIsNull() throws Exception {
        thrown.expect(NullPointerException.class);

        elementToBeScoredMock = stubElement(stubTag("font"), null);

        emphasisElementScorerSUT.score(elementToBeScoredMock);
    }

    @Override
    @Test
    public void
    test_ScoreGivesExpectedResult_WhenSimpleInput() throws Exception {
        //////////////////////////////////////////////////////////////////////////////
        //// GENERATE TARGET INPUT/OUTPUT PAIRS //////////////////////////////////////
        Map<Element, Integer> targetOutput = new HashMap<>();

        //add Map.Entry<Element, Integer> entries to targetOutput
        //based on the tagAndAttributeScoreMap
        Map<Element, Integer> scoredMapBasedOnTagAndAttributes
                = stubElementsAndScoresByTagAndAttributeScores(
                TagAndAttributeScorer.defaultMap()
        );
        targetOutput.putAll(scoredMapBasedOnTagAndAttributes);

        //add Map.Entry<Element, Integer> entries to targetOutput
        //based on the tagEmphasisScoreMap
        Map<Element, Integer> scoresMapBasedOnTags
                = stubElementsAndScoresByTagScores(
                TagEmphasisScorer.defaultMap()
        );
        targetOutput.putAll(scoresMapBasedOnTags);

        assert targetOutput.size()
                == scoredMapBasedOnTagAndAttributes.size()
                + scoresMapBasedOnTags.size();

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
            actualOutputs.put(input, emphasisElementScorerSUT.score(input));
        }

        //////////////////////////////////////////////////////////////////////////////
        //// COMPARE INPUT AND OUTPUT ////////////////////////////////////////////////
        assertEquals(expectedOutput, actualOutputs);
    }
}