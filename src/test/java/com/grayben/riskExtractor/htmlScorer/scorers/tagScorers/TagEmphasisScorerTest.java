package com.grayben.riskExtractor.htmlScorer.scorers.tagScorers;

import com.grayben.riskExtractor.htmlScorer.scorers.ScorerTest;
import org.jsoup.parser.Tag;
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
public class TagEmphasisScorerTest
        extends ScorerTest<Tag> {

    public TagEmphasisScorer tagEmphasisScorerSUT;

    @Mock
    public Tag tagToBeScoredMock;

    @Before
    public void setUp() throws Exception {
        this.tagEmphasisScorerSUT = new TagEmphasisScorer
                (TagEmphasisScorer.defaultMap());
        super.setScorerSUT(tagEmphasisScorerSUT);
        super.setArgumentToBeScoredMock(tagToBeScoredMock);
        super.setUp();
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Override
    @Test
    public void test_ScoreGivesExpectedResult_WhenSimpleInput() throws Exception {
        fail("This test has not been implemented");
    }

    @Test
    public void testScore() throws Exception {

    }

    @Test
    public void testUseDefaultMap() throws Exception {

    }
}