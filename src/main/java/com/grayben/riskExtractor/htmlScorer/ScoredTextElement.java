package com.grayben.riskExtractor.htmlScorer;

import java.util.Collections;
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
        if(text.equals("")){
            throw new IllegalArgumentException(
                    "Tried to pass in empty String"
            );
        }
        if(text.matches("^\\s*$")){
            throw new IllegalArgumentException(
                    "Tried to pass in string with no non-whitespace characters"
            );
        }
		if(scores == null) {
            throw new NullPointerException(
                    "Tried to pass in null currentScores:Map<Integer, Integer>"
            );
        }
		if(scores.isEmpty()){
			throw new IllegalArgumentException(
                    "Tried to pass in empty currentScores:Map<Integer, Integer>"
            );
		}
		this.textElement = text;
		this.scores = scores;
	}
	
	public String getTextElement() {
		return textElement;
	}
	
	public Map<String, Integer> getScores() {
		return Collections.unmodifiableMap(scores);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ScoredTextElement that = (ScoredTextElement) o;

		if (!textElement.equals(that.textElement)) return false;
		return scores.equals(that.scores);

	}

	@Override
	public int hashCode() {
		int result = textElement.hashCode();
		result = 31 * result + scores.hashCode();
		return result;
	}
}
