package com.grayben.parser;

import java.util.HashMap;
import java.util.Map;

import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

public class ElementScorer {
	
	final private Map<Tag, Integer> tagScores;
	
	private int defaultScore = 0;

	public int score(Element element){
		Tag tag = element.tag();
		if(tagScores.containsKey(tag)){
			return tagScores.get(tag).intValue();
		} else {
			return defaultScore;
		}
	}
	
	public ElementScorer(Map<Tag, Integer> tagScores) {
		super();
		this.tagScores = tagScores;
	}
	
	public ElementScorer() {
		super();
		this.tagScores = new HashMap<Tag, Integer>();
	}
	
	public ElementScorer(int initialCapacity, float loadFactor) {
		super();
		this.tagScores = 
				new HashMap<Tag, Integer>(initialCapacity, loadFactor);
		
	}

	public Map<Tag, Integer> getTagScores() {
		return tagScores;
	}
	
	public int getDefaultScore() {
		return defaultScore;
	}

	public void setDefaultScore(int defaultScore) {
		this.defaultScore = defaultScore;
	}
}
