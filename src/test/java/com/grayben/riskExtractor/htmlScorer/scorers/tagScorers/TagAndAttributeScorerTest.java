package com.grayben.riskExtractor.htmlScorer.scorers.tagScorers;

import com.grayben.riskExtractor.htmlScorer.TagAndAttribute;
import com.grayben.riskExtractor.htmlScorer.scorers.ScorerTest;
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
public class TagAndAttributeScorerTest
        extends ScorerTest <TagAndAttribute> {

    TagAndAttributeScorer tagAndAttributeSUT;

    @Mock
    public TagAndAttribute tagAndAttributeToBeScoredMock;

    @Before
    public void setUp() throws Exception {
        tagAndAttributeSUT = new TagAndAttributeScorer();
        super.setArgumentToBeScoredMock(tagAndAttributeToBeScoredMock);
        super.setScorerSUT(tagAndAttributeSUT);
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

    @Override
    @Test
    public void test_ScoreReturnsInteger_WhenArgumentIsNonNull() throws Exception {
        fail("Test not implemented");
    }
}