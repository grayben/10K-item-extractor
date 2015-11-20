package com.grayben.riskExtractor.htmlScorer;

import java.util.HashMap;
import java.util.Map;

public class ScoredTextElement {
	String text = null;
	Map<String, Integer> scores = new HashMap<String, Integer>(8, 0.5f);
	
	public ScoredTextElement(String text, Map<String, Integer> scores) {
		super();
		this.text = text;
		this.scores = scores;
	}
	
	public ScoredTextElement() {
		super();
	}
	
	public String getText() {
		return text;
	}
	
	public Map<String, Integer> getScores() {
		return scores;
	}
	
	@Override
	public String toString(){
		return this.getText();
	}
	
}
