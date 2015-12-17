package com.grayben.riskExtractor.htmlScorer.partScorers.tagScorers;

import com.grayben.riskExtractor.htmlScorer.partScorers.MapScorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.TagAndAttribute;

import java.util.HashMap;
import java.util.Map;

public class TagAndAttributeScorer
		extends MapScorer<TagAndAttribute> {

    public final static String SCORE_LABEL
            = "tag-and-attribute-emphasis";

    protected TagAndAttributeScorer(Map<TagAndAttribute, Integer> tagScores) {
        super(SCORE_LABEL, tagScores);
    }

    @Override
	public int score(TagAndAttribute input) {
		return super.score(input);
	}

	@Override
	public String getScoreLabel() {
		return null;
	}

	public static final Map<TagAndAttribute, Integer> defaultMap() {

		Map<TagAndAttribute, Integer> scoreMap = new HashMap<>();
//		scoreMap.put(Tag.valueOf("b"), 1);
//		scoreMap.put(Tag.valueOf("strong"), 1);
//		scoreMap.put(Tag.valueOf("h1"), 1);
//		scoreMap.put(Tag.valueOf("h2"), 1);
//		scoreMap.put(Tag.valueOf("h3"), 1);
//		scoreMap.put(Tag.valueOf("h4"), 1);
//		scoreMap.put(Tag.valueOf("h5"), 1);
//		scoreMap.put(Tag.valueOf("u"), 1);

		return scoreMap;

	}


}
