package com.grayben.riskExtractor.htmlScorer.partScorers.tagScorers;

import com.grayben.riskExtractor.htmlScorer.partScorers.Scorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.ScorerTest;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

/**
 * Created by beng on 17/12/2015.
 */
public abstract class MapScorerTest<T> extends ScorerTest<T> {

    private MapScorer<T> mapScorerSUT;

    @Override
    protected void setScorerSUT(Scorer<T> scorerSUT){
        super.setScorerSUT(scorerSUT);
    }
    protected void setMapScorerSUT(MapScorer<T> mapScorerSUT){
        this.mapScorerSUT = mapScorerSUT;
        this.setScorerSUT(mapScorerSUT);
    }

    private T argumentToBeScoredMock;

    @Override
    protected void setArgumentToBeScored(T argumentToBeScoredMock){
        this.argumentToBeScoredMock = argumentToBeScoredMock;
    }

    @Before
    @Override
    public void setUp() throws Exception {
        assert this.mapScorerSUT != null;
        super.setScorerSUT(this.mapScorerSUT);

        assert this.argumentToBeScoredMock != null;
        super.setArgumentToBeScored(this.argumentToBeScoredMock);

        super.setUp();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    abstract public void
    test_InitThrowsNullPointerException_WhenMapParamIsNull
            () throws Exception;

    protected void
    testHelper_ScoreGivesExpectedResult_WhenSimpleInput(
            MapScorer<T> mapScorerSUT,
            Map<T, Integer> expectedResults
            ) throws Exception {

        this.mapScorerSUT = mapScorerSUT;

        for (T input: expectedResults.keySet()) {
            Assert.assertEquals(
                    (int) expectedResults.get(input),
                    this.mapScorerSUT.score(input)
            );
        }


    }
}