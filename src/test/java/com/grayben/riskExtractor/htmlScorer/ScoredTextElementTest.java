package com.grayben.riskExtractor.htmlScorer;

import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by beng on 28/11/2015.
 */
@Ignore
@RunWith(MockitoJUnitRunner.class)
public class ScoredTextElementTest {

    ScoredTextElement scoredTextElementSUT;

    @Mock
    Map<String, Integer> scoresMock;

    String textElementArgument;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        textElementArgument = "foo";
        scoredTextElementSUT = new ScoredTextElement(textElementArgument, scoresMock);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void
    test_InitThrowsNullPointerException_WhenTextElementArgumentIsNull
            () throws Exception {
        thrown.expect(NullPointerException.class);

        new ScoredTextElement(null, scoresMock);
    }

    @Test
    public void
    test_InitThrowsNullPointerException_WhenScoresMapIsNull
            () throws Exception {
        thrown.expect(NullPointerException.class);

        new ScoredTextElement(textElementArgument, null);
    }

    @Test
    public void test_GetTextElement_IsNotNull_Always
            () throws Exception {
        String textElementReturned = scoredTextElementSUT.getTextElement();

        assertNotNull(textElementReturned);
    }

    @Test
    public void test_GetScores_IsNotNull_Always
            () throws Exception {
        Map<String, Integer> scoresReturned = scoredTextElementSUT.getScores();

        assertNotNull(scoresReturned);
    }

    @Test
    public void testGetTextElement_ReturnsMockedScores_Always() throws Exception {
        Map scoresReturned = scoredTextElementSUT.getScores();

        assertEquals(scoresMock, scoresReturned);

    }

    @Test
    public void testGetTextElement_ReturnsMockedTextElement_Always() throws Exception {
        String textElementReturned;
        textElementReturned = scoredTextElementSUT.getTextElement();

        assertEquals(textElementArgument, textElementReturned);

    }

    @Test
    public void testGetScores() throws Exception {

    }
}