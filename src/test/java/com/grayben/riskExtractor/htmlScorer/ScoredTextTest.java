package com.grayben.riskExtractor.htmlScorer;

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
@Ignore
@RunWith(MockitoJUnitRunner.class)
public class ScoredTextTest {

    private ScoredText scoredTextSUT;

    @Mock
    public ScoredTextElement scoredTextElementMock;

    @Before
    public void setUp() throws Exception {
        scoredTextSUT = new ScoredText();
    }

    @After
    public void tearDown() throws Exception {
        scoredTextSUT = null;
    }

    @Test
    public void
    test_ToStringReturnsNonNull_WhenNothingWasAdded
            () throws Exception {
        fail("Test not implemented");
    }

    @Test
    public void
    test_AddChangesWhatToStringReturns
            () throws Exception {
        fail("Test not implemented");
    }

    @Test
    public void
    test_AddThrowsNullPointerException_WhenParamIsNull
            () throws Exception {
        fail("Test not implemented");
    }

    @Test
    public void
    test_AddThrowsIllegalArgumentException_WhenParamDoesNotHaveScores
            () throws Exception {
        fail("Test not implemented");
    }

    @Test
    public void
    test_AddThrowsIllegalArgumentException_WhenParamDoesNotHaveText
            () throws Exception {
        fail("Test not implemented");
    }

    @Test
    public void
    test_AddThrowsIllegalArgumentException_WhenParamHasEmptyText
            () throws Exception {
        fail("Test not implemented");
    }

    @Test
    public void
    test_AddThrowsIllegalArgumentException_WhenParamHasEmptyScores
            () throws Exception {
        fail("Test not implemented");
    }

    @Test
    public void
    test_GetTextElementReturnsExpectedOutput_OnSimpleInputCase
            () throws Exception {
        fail("Test not implemented");
    }
}