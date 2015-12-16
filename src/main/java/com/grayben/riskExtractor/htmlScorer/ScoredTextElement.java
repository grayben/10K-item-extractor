package com.grayben.riskExtractor.htmlScorer;

import java.util.HashMap;
import java.util.Map;

public class ScoredTextElement {
	private String textElement = null;
	Map<String, Integer> scores = new HashMap<String, Integer>(8, 0.5f);
	
	public ScoredTextElement(String text, Map<String, Integer> scores) {
		super();
		if(text == null){
			throw new NullPointerException(
					"Tried to pass in null text:String"
			);
		}
		if(scores == null){
			throw new NullPointerException(
					"Tried to pass in null scores:Map<Integer, Integer>"
			);
		}
		this.textElement = text;
		this.scores = scores;
	}
	
	public String getTextElement() {
		return textElement;
	}
	
	public Map<String, Integer> getScores() {
		return scores;
	}
	
}
