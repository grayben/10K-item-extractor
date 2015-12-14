package com.grayben.riskExtractor.htmlScorer.scorers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by beng on 28/11/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public abstract class ScorerTest {

    abstract void test_ScoreReturnsInteger_WhenArgumentIsNonNull
            () throws Exception;

    abstract void test_ScoreThrowsNullPointerException_WhenArgumentIsNull
            () throws Exception;

    abstract void test_GetScoreLabelReturnsNonNull
            () throws Exception;

    @Test
    abstract void test_GetScoreLabelReturnsNonEmptyString
            () throws Exception;
}