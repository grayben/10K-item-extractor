package com.grayben.riskExtractor.htmlScorer;

import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.TestCase.*;

/**
 * Created by beng on 28/11/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class ScoredTextTest {

    private ScoredText scoredTextSUT;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

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
        scoredTextSUT = new ScoredText();

        assertNotNull(scoredTextSUT.toString());
    }

    @Test
    public void
    test_AddChangesWhatToStringReturns
            () throws Exception {
        String beforeAdd = scoredTextSUT.toString();
        scoredTextSUT.add(scoredTextElementMock);
        String afterAdd = scoredTextSUT.toString();

        assertNotSame(beforeAdd, afterAdd);
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