package com.grayben.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

public class ElementEmphasisScorer extends ElementScorer {
	

	@Override
	public int score(Element element) {
		
		return 0;
	}

	@Override
	public void useDefaultMap() {
		
		Map<Tag, Integer> tagScores = this.getTagScores();
		List<Tag> tags = new ArrayList<Tag>();
		tags.add(Tag.valueOf("b"));
		tags.add(Tag.valueOf("strong"));
		tags.add(Tag.valueOf("h1"));
		tags.add(Tag.valueOf("h2"));
		tags.add(Tag.valueOf("h3"));
		tags.add(Tag.valueOf("h4"));
		tags.add(Tag.valueOf("h5"));
		tags.add(Tag.valueOf("u"));
		// tags.add(Tag.valueOf("font"));
		// tags.add(Tag.valueOf("b"));
		// tags.add(Tag.valueOf("b"));
		
		this.setTagScores(tagScores);
		
	}

}
