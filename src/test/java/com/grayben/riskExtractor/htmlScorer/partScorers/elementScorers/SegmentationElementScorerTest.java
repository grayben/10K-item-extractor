package com.grayben.riskExtractor.htmlScorer.partScorers.elementScorers;

import com.grayben.riskExtractor.htmlScorer.partScorers.MapScorerTest;
import com.grayben.riskExtractor.htmlScorer.partScorers.Scorer;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.TestCase.fail;

/**
 * Created by beng on 28/11/2015.
 */
@Ignore
@RunWith(MockitoJUnitRunner.class)
public class SegmentationElementScorerTest
        extends MapScorerTest<Element> {

    SegmentationElementScorer elementScorerSUT;

    @Mock
    public Scorer<Tag> tagScorerMock;
    @Mock
    public Element elementToBeScoredMock;

    @Before
    public void setUp() throws Exception {
        elementScorerSUT = new SegmentationElementScorer(tagScorerMock);
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
        Mockito.when(tagScorerMock.score(Mockito.any())).thenReturn(1);

        Integer returned = elementScorerSUT.score(elementToBeScoredMock);

        assert returned.getClass() == Integer.class;
    }

    @Override
    @Test
    public void
    test_ScoreGivesExpectedResult_WhenSimpleInput() throws Exception {
        fail("Test not implemented");
    }

    @Override
    @Test
    public void
    test_ScoreThrowsIllegalArgumentException_WhenEmptyInput() throws Exception {
        fail("Test not implemented");
    }

}