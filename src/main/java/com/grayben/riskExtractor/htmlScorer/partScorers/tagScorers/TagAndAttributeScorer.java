package com.grayben.riskExtractor.htmlScorer.partScorers.tagScorers;

import com.grayben.riskExtractor.htmlScorer.partScorers.Scorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.TagAndAttribute;

import java.util.Map;

/**
 * Scores {@link TagAndAttribute} objects.
 * <p>
 * Created by Ben Gray, 2015.
 */
public class TagAndAttributeScorer extends MapScorer<TagAndAttribute> {

    /**
     * The string (uniquely) labelling this class.
     * <p>
     * Used to identify this class in a collection of {@link Scorer}s.
     */
    public final static String SCORE_LABEL = "tag-and-attribute-emphasis";

    /**
     * Construct with the specified map.
     * @param tagScores the map with which to construct this instance.
     */
    public TagAndAttributeScorer(Map<TagAndAttribute, Integer> tagScores) {
        super(SCORE_LABEL, tagScores);
    }

    /**
     * Use the underlying {@link MapScorer} to score the specified input.
     * @param input the input to score
     * @return the score
     */
    @Override
	public int score(TagAndAttribute input) {
        validateScoreInput(input);
		return super.score(input);
	}

    /**
     * @param input the input to validate
     */
    private void validateScoreInput(TagAndAttribute input){
        if(input == null){
            throw new NullPointerException(
                    "The input cannot be null"
            );
        }
        if (input.getAttribute() == null){
            throw new NullPointerException(
                    "The input cannot have null Attribute"
            );
        }
        if (input.getTag() == null){
            throw new NullPointerException(
                    "The input cannot have null Tag"
            );
        }
        if(input.getAttribute().getKey().isEmpty()){
            throw new IllegalArgumentException(
                    "The input cannot have an empty Attribute"
            );
        }
        if(input.getTag().getName().isEmpty()){
            throw new IllegalArgumentException(
                    "The input cannot have an empty Tag"
            );
        }
    }

}
