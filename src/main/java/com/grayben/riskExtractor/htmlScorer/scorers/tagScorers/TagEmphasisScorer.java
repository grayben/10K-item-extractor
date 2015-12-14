package com.grayben.riskExtractor.htmlScorer.scorers.tagScorers;

import com.grayben.riskExtractor.htmlScorer.scorers.Scorer;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TagEmphasisScorer
		extends Scorer<Tag> {

    public static final String SCORE_LABEL = "tag-emphasis";
	
	Map<Tag, Integer> tagScores = null;

	TagEmphasisScorer(Map<Tag, Integer> tagScores) {
		super(SCORE_LABEL);
        this.tagScores = tagScores;
	}

	public int score(Element element) {
		
		return 0;
	}

	public static final Map<Tag, Integer> defaultMap() {
		
		//Map<Tag, Integer> tagScores = this.getTagScores();
		Map<Tag, Integer> tags = new HashMap<>();
		tags.put(Tag.valueOf("b"), 1);
		tags.put(Tag.valueOf("strong"), 1);
		tags.put(Tag.valueOf("h1"), 1);
		tags.put(Tag.valueOf("h2"), 1);
		tags.put(Tag.valueOf("h3"), 1);
		tags.put(Tag.valueOf("h4"), 1);
		tags.put(Tag.valueOf("h5"), 1);
		tags.put(Tag.valueOf("u"), 1);
		
		//this.setTagScores(tagScores);
		
	}

	@Override
	public int score(Tag input) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getScoreLabel() {
		return null;
	}


}
