package com.grayben.riskExtractor.htmlScorer.scorers;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by beng on 28/11/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public abstract class ScorerTest<T> {

    private Scorer<T> scorerSUT;

    protected void setScorerSUT(Scorer<T> scorerSUT){
        this.scorerSUT = scorerSUT;
    }

    private T argumentToBeScoredMock;

    public void setArgumentToBeScoredMock(T argumentToBeScoredMock){
        this.argumentToBeScoredMock = argumentToBeScoredMock;
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
        scorerSUT = null;
    }

    @Test
    public void test_ScoreReturnsInteger_WhenArgumentIsNonNull
            () throws Exception {
        scorerSUT.score(argumentToBeScoredMock);
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

    @Test
    public abstract void test_ScoreGivesExpectedResult_WhenSimpleInput
            () throws Exception;

}