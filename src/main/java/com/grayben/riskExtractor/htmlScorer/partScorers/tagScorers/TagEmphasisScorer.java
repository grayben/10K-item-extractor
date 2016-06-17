package com.grayben.riskExtractor.htmlScorer.partScorers.tagScorers;

import com.grayben.riskExtractor.htmlScorer.partScorers.Scorer;
import org.jsoup.parser.Tag;

import java.util.Map;

/**
 * Scores the emphasis of {@link Tag}s passed in to {@link #score(Tag)}.
 * <p>
 * Created by Ben Gray, 2015.
 */
public class TagEmphasisScorer
		extends MapScorer<Tag> {

    /**
     * The string (uniquely) labelling this class.
     * <p>
     * Used to identify this class in a collection of {@link Scorer}s.
     */
    public static final String SCORE_LABEL = "tag-emphasis";

    /**
     * Construct with the specified map.
     * @param tagScores the map with which to construct this instance.
     */
	public TagEmphasisScorer(Map<Tag, Integer> tagScores) {
		super(SCORE_LABEL, tagScores);
	}

    /**
     * Use the underlying {@link MapScorer} to score the specified input.
     * @param input the input to score
     * @return the score
     */
	@Override
	public int score(Tag input) {
		validateTagInput(input);
        return super.score(input);
	}

    /**
     * @param input the input to validate
     */
    public static void validateTagInput(Tag input){
        if(input.getName().isEmpty()){
            throw new IllegalArgumentException(
                    "Input Tag must have a non-empty name"
            );
        }
    }


}
