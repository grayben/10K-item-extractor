package com.grayben.riskExtractor.htmlScorer.partScorers.elementScorers;

import com.grayben.riskExtractor.htmlScorer.partScorers.ScorerTest;
import com.grayben.riskExtractor.htmlScorer.partScorers.tagScorers.TagEmphasisScorer;
import org.jsoup.nodes.Element;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.fail;

/**
 * Created by beng on 28/11/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class SegmentationElementScorerTest
        extends ScorerTest<Element> {

    SegmentationElementScorer elementScorerSUT;

    @Mock
    public Element elementToBeScoredMock;

    @Before
    public void setUp() throws Exception {
        TagEmphasisScorer tagScorer
                = new TagEmphasisScorer(TagEmphasisScorer.defaultMap());
        elementScorerSUT = new SegmentationElementScorer(tagScorer);
        super.setScorerSUT(elementScorerSUT);
        super.setArgumentToBeScoredMock(elementToBeScoredMock);
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
        Integer returned = elementScorerSUT.score(elementToBeScoredMock);

        assertEquals(Integer.class, returned.getClass());
    }

    @Override
    @Test
    public void
    test_ScoreGivesExpectedResult_WhenSimpleInput() throws Exception {
        fail("Test not implemented");
    }

    @Test
    public void
    test_ScoreThrowsIllegalArgumentException_WhenEmptyInput() throws Exception {
        fail("Test not implemented: decide whether appropriate");
    }

}