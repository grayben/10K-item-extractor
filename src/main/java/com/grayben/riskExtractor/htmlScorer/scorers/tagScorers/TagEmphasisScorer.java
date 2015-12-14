package com.grayben.riskExtractor.htmlScorer.scorers.tagScorers;

import com.grayben.riskExtractor.htmlScorer.scorers.Scorer;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

import java.util.ArrayList;
import java.util.List;

public class TagEmphasisScorer
		extends Scorer<Tag> {
	
	List<Tag> tagScores = null;

	TagEmphasisScorer(String scoreLabel) {
		super(scoreLabel);
	}

	public int score(Element element) {
		
		return 0;
	}

	public void useDefaultMap() {
		
		//Map<Tag, Integer> tagScores = this.getTagScores();
		List<Tag> tags = new ArrayList<Tag>();
		tags.add(Tag.valueOf("b"));
		tags.add(Tag.valueOf("strong"));
		tags.add(Tag.valueOf("h1"));
		tags.add(Tag.valueOf("h2"));
		tags.add(Tag.valueOf("h3"));
		tags.add(Tag.valueOf("h4"));
		tags.add(Tag.valueOf("h5"));
		tags.add(Tag.valueOf("u"));
		
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
