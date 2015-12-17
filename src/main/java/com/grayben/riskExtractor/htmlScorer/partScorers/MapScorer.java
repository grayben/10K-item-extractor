package com.grayben.riskExtractor.htmlScorer.partScorers;

/**
 * Created by beng on 17/12/2015.
 */
public abstract class MapScorer<T> extends Scorer<T> {
    protected MapScorer(String scoreLabel) {
        super(scoreLabel);
    }
}
