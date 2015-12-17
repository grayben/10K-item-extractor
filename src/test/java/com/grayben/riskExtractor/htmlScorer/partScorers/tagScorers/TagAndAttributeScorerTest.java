package com.grayben.riskExtractor.htmlScorer.partScorers.tagScorers;

import com.grayben.riskExtractor.htmlScorer.partScorers.TagAndAttribute;
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
        extends MapScorerTest<TagAndAttribute> {

    TagAndAttributeScorer tagAndAttributeSUT;

    @Mock
    public TagAndAttribute tagAndAttributeToBeScoredMock;

    @Before
    public void setUp() throws Exception {
        tagAndAttributeSUT = new TagAndAttributeScorer(
                TagAndAttributeScorer.defaultMap()
        );
        super.setArgumentToBeScoredMock(tagAndAttributeToBeScoredMock);
        super.setScorerSUT(tagAndAttributeSUT);
        super.setUp();
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Override
    public void test_InitThrowsNullPointerException_WhenMapParamIsNull() throws Exception {
        fail("Test not implemented");
    }

    @Override
    public void test_ScoreReturnsInteger_WhenArgumentIsNotEmpty() throws Exception {
        fail("Test not implemented");
    }

    @Override
    @Test
    public void
    test_ScoreGivesExpectedResult_WhenSimpleInput() throws Exception {
        fail("This test has not been implemented");
    }

    @Test
    public void
    test_ScoreThrowsIllegalArgumentException_WhenEmptyInput() throws Exception {
        fail("Test not implemented: decide whether appropriate");
    }
}