package com.grayben.riskExtractor.htmlScorer;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * An immutable class which decorates a String with zero or more scores.
 * <p>
 * Created by Ben Gray, 2015.
 */
public class ScoredTextElement {

    /**
     * The string to decorate.
     */
    private String textElement = null;

    /**
     * The scores with which to decorate {@link #textElement}.
     */
    Map<String, Integer> scores = new HashMap<>(8);

    /**
     * Aggregate the specified arguments.
     * @param text the text to decorate with scores
     * @param scores the scores with which to decorate the specified text
     */
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

    /**
     * @return the text element without its scores
     */
	public String getTextElement() {
		return textElement;
	}

    /**
     * @return the scores attached to this object and associated
     * with the text returned by {@link #getTextElement()}.
     */
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
