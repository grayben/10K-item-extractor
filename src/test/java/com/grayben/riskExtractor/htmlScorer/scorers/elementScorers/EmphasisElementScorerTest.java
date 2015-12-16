package com.grayben.riskExtractor.htmlScorer.scorers.elementScorers;

import com.grayben.riskExtractor.htmlScorer.scorers.ScorerTest;
import org.jsoup.nodes.Element;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.TestCase.fail;

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
    public void test_ScoreReturnsInteger_WhenArgumentIsNonNull() throws Exception {
        fail("Test not implemented");
    }

    @Override
    @Test
    public void test_ScoreGivesExpectedResult_WhenSimpleInput() throws Exception {
        fail("This test has not been implemented");
    }
}