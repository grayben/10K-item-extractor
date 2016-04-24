package com.grayben.riskExtractor.htmlScorer.partScorers.tagScorers;

import com.grayben.riskExtractor.htmlScorer.partScorers.Scorer;

import java.util.Collections;
import java.util.Map;

/**
 * Maps a set of objects to their scores.
 * <p>
 * Created by Ben Gray on 17/12/2015.
 *
 * @param <T> the type of object to score
 */
public abstract class MapScorer<T> extends Scorer<T> {

    /**
     * The map of scores for known objects.
     */
    Map<T, Integer> scoresMap;

    /**
     * @return an unmodifiable view of the map of scores for known objects
     */
    public Map<T, Integer> getScoresMap() {
        return Collections.unmodifiableMap(scoresMap);
    }

    /**
     * Construct. Label the constructed object with the specified label, base this scorer on the specified map.
     * <p>
     * @param scoreLabel the label to apply to this object
     * @param scoresMap the map of scores for known objects
     */
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

    /**
     * @param input the input to score
     * @return the score held by the map specified at construction, if it holds input as a key.
     * Otherwise, return {@code 0}.
     */
    @Override
    public Integer apply(T input){
        validateScoreInput(input);
        boolean containsKey = scoresMap.containsKey(input);
        if (containsKey)
            return scoresMap.get(input);
        else
            return 0;
    }

    /**
     * @param input the input to {@link #apply(Object)} to validate
     */
    private void validateScoreInput(T input) {
        if(input == null){
            throw new NullPointerException(
                    "Input cannot be null"
            );
        }
    }
}
