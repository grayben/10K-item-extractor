package com.grayben.riskExtractor.htmlScorer.partScorers.tagScorers;

import com.grayben.riskExtractor.htmlScorer.partScorers.Scorer;
import org.jsoup.parser.Tag;

import java.util.HashMap;
import java.util.Map;

public class TagEmphasisScorer
		extends Scorer<Tag> {

    public static final String SCORE_LABEL = "tag-emphasis";
	
	Map<Tag, Integer> tagScores = null;

	TagEmphasisScorer(Map<Tag, Integer> tagScores) {
		super(SCORE_LABEL);
        this.tagScores = tagScores;
	}

	public static final Map<Tag, Integer> defaultMap() {
		
		//Map<Tag, Integer> tagScores = this.getTagScores();
		Map<Tag, Integer> tagScoreMap = new HashMap<>();
		tagScoreMap.put(Tag.valueOf("b"), 1);
		tagScoreMap.put(Tag.valueOf("strong"), 1);
		tagScoreMap.put(Tag.valueOf("h1"), 1);
		tagScoreMap.put(Tag.valueOf("h2"), 1);
		tagScoreMap.put(Tag.valueOf("h3"), 1);
		tagScoreMap.put(Tag.valueOf("h4"), 1);
		tagScoreMap.put(Tag.valueOf("h5"), 1);
		tagScoreMap.put(Tag.valueOf("u"), 1);
		
		return tagScoreMap;
		
	}

	@Override
	public int score(Tag input) {
		validateTagInput(input);
        boolean containsKey = tagScores.containsKey(input);
        if (containsKey)
            return tagScores.get(input);
        else
            return 0;
	}

    public static void validateTagInput(Tag input){
        if(input == null){
            throw new NullPointerException(
                    "Input cannot be null"
            );
        }
        if(input.isEmpty()){
            throw new IllegalArgumentException(
                    "Input cannot be empty"
            );
        }
        if(input.getName().isEmpty()){
            throw new IllegalArgumentException(
                    "Input Tag must have a non-empty name"
            );
        }
    }


}
