package com.grayben.riskExtractor.htmlScorer.partScorers.elementScorers;

import com.grayben.riskExtractor.htmlScorer.partScorers.ScorerTest;
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
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static com.grayben.riskExtractor.htmlScorer.partScorers.elementScorers.TestHelper.*;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by beng on 28/11/2015.
 */

@RunWith(MockitoJUnitRunner.class)
public class EmphasisElementScorerTest extends ScorerTest<Element> {

    EmphasisElementScorer emphasisElementScorerSUT;

    @Mock
    public Element elementToBeScoredMock;

    @Before
    public void setUp() throws Exception {
        this.emphasisElementScorerSUT = new EmphasisElementScorer();
        super.setScorerSUT(this.emphasisElementScorerSUT);
        super.setArgumentToBeScoredMock(elementToBeScoredMock);
        super.setUp();
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }



    @Override
    @Test
    public void
    test_ScoreReturnsInteger_WhenArgumentIsNotEmpty() throws Exception {
        Tag tag = stubTag("font");
        Attributes attributes
                = convertListToAttributes(
                stubAttributes(
                        randomMap(5)
                )
        );

        elementToBeScoredMock = stubElement(tag, attributes);

        Integer returned = emphasisElementScorerSUT.score(elementToBeScoredMock);

        assertNotNull(returned);
    }

    @Test
    public void
    test_ScoreThrowsNullPointerException_WhenTagIsNull() throws Exception {
        thrown.expect(NullPointerException.class);

        Attributes attributes
                = convertListToAttributes(
                stubAttributes(
                        randomMap(5)
                )
        );

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

    @Test
    public void
    test_ScoreThrowsIllegalArgumentException_WhenTagIsEmpty() throws Exception {
        Tag tagStub = stubTag("");
        Mockito.when(tagStub.isEmpty()).thenReturn(true);

        Attributes attributes
                = convertListToAttributes(
                stubAttributes(
                        randomMap(5)
                )
        );

        elementToBeScoredMock = stubElement(tagStub, attributes);

        thrown.expect(IllegalArgumentException.class);

        emphasisElementScorerSUT.score(elementToBeScoredMock);
    }

    @Override
    @Test
    public void
    test_ScoreGivesExpectedResult_WhenSimpleInput() throws Exception {
        //////////////////////////////////////////////////////////////////////////////
        //// GENERATE TARGET INPUT/OUTPUT PAIRS //////////////////////////////////////
        Map<Element, Integer> expectedOutput = new HashMap<>();

        //add Map.Entry<Element, Integer> entries to expectedOutput
        //based on the tagAndAttributeScoreMap
        Map<Element, Integer> scoredMapBasedOnTagAndAttributes
                = stubElementsAndScoresConformingToTagAndAttributeScoreMap(
                TagAndAttributeScorer.defaultMap()
        );
        expectedOutput.putAll(scoredMapBasedOnTagAndAttributes);

        //add Map.Entry<Element, Integer> entries to expectedOutput
        //based on the tagEmphasisScoreMap
        Map<Element, Integer> scoresMapBasedOnTags
                = stubElementsAndScoresConformingToTagScoreMap(
                TagEmphasisScorer.defaultMap()
        );
        expectedOutput.putAll(scoresMapBasedOnTags);

        assert expectedOutput.size()
                == scoredMapBasedOnTagAndAttributes.size()
                + scoresMapBasedOnTags.size();

        //////////////////////////////////////////////////////////////////////////////
        //// GENERATE DUMMY INPUT/OUTPUT PAIRS ///////////////////////////////////////


        //TODO: define input
    }






}