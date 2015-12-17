package com.grayben.riskExtractor.htmlScorer.partScorers.tagScorers;

import com.grayben.riskExtractor.htmlScorer.partScorers.Scorer;

import java.util.Map;

/**
 * Created by beng on 17/12/2015.
 */
public abstract class MapScorer<T> extends Scorer<T> {

    Map<T, Integer> scoresMap;

    protected MapScorer(String scoreLabel, Map<T, Integer> scoresMap) {
        super(scoreLabel);
        if(scoresMap == null){
            throw new NullPointerException(
                    "The scores map can not be null"
            );
        }
        if(scoresMap.isEmpty()){
            throw new IllegalArgumentException(
                    "The scores map cannot be empty"
            );
        }
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
