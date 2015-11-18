package com.grayben.riskExtractor.htmlScorer;

public class ScoredTextElement {
	String text = null;
	int emphasisScore = 0;
	int segregationScore = 0;
	
	public ScoredTextElement(String text, int emphasisScore, int segregationScore) {
		super();
		this.text = text;
		this.emphasisScore = emphasisScore;
		this.segregationScore = segregationScore;
	}
	public ScoredTextElement() {
		super();
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getEmphasisScore() {
		return emphasisScore;
	}
	public void setEmphasisScore(int emphasisScore) {
		this.emphasisScore = emphasisScore;
	}
	public int getSegregationScore() {
		return segregationScore;
	}
	public void setSegregationScore(int segregationScore) {
		this.segregationScore = segregationScore;
	}
	
	@Override
	public String toString() {
		return "ScoredTextElement [text=" + text + ", emphasisScore=" + emphasisScore + ", segregationScore="
				+ segregationScore + "]";
	}
	
	

}
