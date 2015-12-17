package com.grayben.riskExtractor.htmlScorer.partScorers;

import java.util.Map;

/**
 * Created by beng on 17/12/2015.
 */
public abstract class MapScorer<T> extends Scorer<T> {

    Map<T, Integer> scoresMap;

    protected MapScorer(String scoreLabel, Map<T, Integer> scoresMap) {
        super(scoreLabel);
        this.scoresMap = scoresMap;
    }

    @Override
    public int score(T input){
        validateScoreInput(input);
        boolean containsKey = scoresMap.containsKey(input);
        if (containsKey)
            return scoresMap.get(input);
        else
            return 0;
    }

    private void validateScoreInput(T input) {
        if(input == null){
            throw new NullPointerException(
                    "Input cannot be null"
            );
        }
    }
}
