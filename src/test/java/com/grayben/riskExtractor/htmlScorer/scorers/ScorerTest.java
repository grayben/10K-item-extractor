package com.grayben.riskExtractor.htmlScorer.scorers;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

/**
 * Created by beng on 28/11/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class ScorerTest<T> {

    public Scorer scorerSUT;

    @Mock
    public T scoredArgumentMock;

    @Rule
    ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp(Scorer scorer) throws Exception {
        scorerSUT = scorer;
    }

    @After
    public void tearDown() throws Exception {
        scorerSUT = null;
    }

    @Test
    public void test_ScoreReturnsInteger_WhenArgumentIsNonNull
            () throws Exception {
        scorerSUT.score(scoredArgumentMock);
    }

    @Test
    public void test_ScoreThrowsNullPointerException_WhenArgumentIsNull
            () throws Exception {
        thrown.expect(NullPointerException.class);

        scorerSUT.score(null);
    }

    @Test
    public void test_GetScoreLabelReturnsNonNull
            () throws Exception {
        assertNotNull(scorerSUT.getScoreLabel());
    }

    @Test
    public void test_GetScoreLabelReturnsNonEmptyString
            () throws Exception {
        assertNotEquals("", scorerSUT.getScoreLabel());
    }
}