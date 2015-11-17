package com.grayben.riskExtractor.HtmlScorer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

public class TagScorer implements Scorer<Tag> {
	
	List<Tag> tagScores = null;
	
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

}
