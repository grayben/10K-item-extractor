package com.grayben.riskExtractor.htmlScorer.nodeVisitor;

import com.grayben.riskExtractor.htmlScorer.ScoringAndFlatteningNodeVisitor;
import com.grayben.riskExtractor.htmlScorer.partScorers.Scorer;
import org.jsoup.nodes.Element;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.jsoup.helper.Validate.fail;

/**
 * Created by beng on 28/11/2015.
 */
@RunWith(MockitoJUnitRunner.class)
@Ignore
public class ScoringAndFlatteningNodeVisitorTest
        extends NodeVisitorTest {

    private ScoringAndFlatteningNodeVisitor scoringAndFlatteningNodeVisitorSUT;

    @Mock
    public Scorer<Element>[] elementScorersMock;

    @Before
    @Override
    public void setUp() throws Exception {
        scoringAndFlatteningNodeVisitorSUT
                = new ScoringAndFlatteningNodeVisitor(
                elementScorersMock
        );
        super.setNodeVisitorSUT(scoringAndFlatteningNodeVisitorSUT);
        super.setUp();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void
    test_InitThrowsNullPointerException_WhenElementScorersIsNull
            () throws Exception {
        fail("Test not implemented");
    }

    @Test
    public void
    test_InitThrowsNullPointerException_WhenAnyElementScorerIsNull
            () throws Exception {
        fail("Test not implemented");
    }

    @Test
    public void
    test_GetScoredTextReturnsNonNull_ImmediatelyAfterSUTIsInitialised
            () throws Exception {
        fail("Test not implemented");
    }

    @Test
    public void
    test_GetScoredTextReturnsEmpty_ImmediatelyAfterSUTIsInitialised
            () throws Exception {
        fail("Test not implemented");
    }

    @Test
    public void
    test_GetScoredTextReturnsEmpty_AfterSingleVisitToNonElementNode
            () throws Exception {
        fail("Test not implemented");
    }

    @Test
    public void
    test_GetScoredTextReturnsEmpty_AfterSingleVisitToElementWithNoText
            () throws Exception {
        fail("Test not implemented");
    }

    @Test
    public void
    test_GetScoredTextReturnsNotEmpty_AfterSingleVisitToElementNodeWithText
            () throws Exception {
        fail("Test not implemented");
    }

    @Test
    public void
    test_GetScoredTextReturnsExpectedText_AfterSingleVisitToElementWithText
            () throws Exception {
        fail("Test not implemented");
    }

    @Test
    public void
    test_GetScoredTextReturnsExpectedScores_AfterSingleVisitToElementWithText
            () throws Exception {
        fail("Test not implemented");
    }

    @Test
    public void
    test_GetScoredTextReturnsExpectedText_AfterVisitsToManyElementsWithText
            () throws Exception {
        fail("Test not implemented");
    }

    @Test
    public void
    test_GetScoredTextReturnsExpectedScores_AfterVisitsToManyElementsWithText
            () throws Exception {
        fail("Test not implemented");
    }
}