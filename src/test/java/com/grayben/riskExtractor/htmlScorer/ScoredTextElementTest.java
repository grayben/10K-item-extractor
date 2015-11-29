package com.grayben.riskExtractor.htmlScorer;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by beng on 28/11/2015.
 */
public class ScoredTextElementTest {

    ScoredTextElement scoredTextElement;

    @Mock
    Map<String, Integer> scoresMock;

    @Mock
    String textElementMock;

    @Before
    public void setUp() throws Exception {
        scoredTextElement = new ScoredTextElement(textElementMock, scoresMock);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    private void test_GetTextElement_IsNotNull_Always(){
        String textElementReturned = scoredTextElement.getTextElement();

        assertNotNull(textElementReturned);
    }

    @Test
    private void test_GetScores_IsNotNull_Always(){
        Map<String, Integer> scoresReturned = scoredTextElement.getScores();

        assertNotNull(scoresReturned);
    }

    @Test
    public void testGetTextElement_ReturnsMockedScores_Always() throws Exception {
        Map scoresReturned = scoredTextElement.getScores();

        assertEquals(scoresMock, scoresReturned);

    }

    @Test
    public void testGetTextElement_ReturnsMockedTextElement_Always() throws Exception {
        String textElementReturned;
        textElementReturned = scoredTextElement.getTextElement();

        assertEquals(textElementMock, textElementReturned);

    }

    @Ignore
    @Test
    public void testGetScores() throws Exception {

    }
}