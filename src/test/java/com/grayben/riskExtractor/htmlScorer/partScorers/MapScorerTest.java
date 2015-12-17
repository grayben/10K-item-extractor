package com.grayben.riskExtractor.htmlScorer.partScorers;

import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;

import java.util.Map;

/**
 * Created by beng on 17/12/2015.
 */
public abstract class MapScorerTest<T> extends ScorerTest<T> {

    private MapScorer<T> mapScorerSUT;

    protected void setMapScorerSUT(MapScorer<T> mapScorerSUT){
        this.mapScorerSUT = mapScorerSUT;
    }

    private T argumentToBeScoredMock;

    protected void setArgumentToBeScoredMock(T argumentToBeScoredMock){
        this.argumentToBeScoredMock = argumentToBeScoredMock;
    }

    @Before
    @Override
    public void setUp() throws Exception {
        assert this.mapScorerSUT != null;
        super.setScorerSUT(this.mapScorerSUT);

        assert this.argumentToBeScoredMock != null;
        super.setArgumentToBeScoredMock(this.argumentToBeScoredMock);

        super.setUp();
    }

    @After
    public void tearDown() throws Exception {

    }

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