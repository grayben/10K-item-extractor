package com.grayben.riskExtractor.htmlScorer;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by beng on 28/11/2015.
 */
public class ScoredTextElementTest {

    ScoredTextElement scoredTextElement;
    Map<String, Integer> mockedScores;

    @Before
    public void setUp() throws Exception {
        mockedScores = Mockito.mock(Map.class);
        scoredTextElement = new ScoredTextElement("Section 1A: Risk", mockedScores);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetTextElement_ReturnsScores_Always() throws Exception {
        //setup data


        //setup expectation


        //execute
        Map scores;
        scores = scoredTextElement.getScores();

        //verify
        assertEquals(mockedScores, scores);

    }

    @Ignore
    @Test
    public void testGetScores() throws Exception {

    }
}