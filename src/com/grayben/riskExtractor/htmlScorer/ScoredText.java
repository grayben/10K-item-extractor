package com.grayben.riskExtractor.htmlScorer;

public class ScoredText {
	String text = null;
	int emphasisScore = 0;
	int segregationScore = 0;
	
	public ScoredText(String text, int emphasisScore, int segregationScore) {
		super();
		this.text = text;
		this.emphasisScore = emphasisScore;
		this.segregationScore = segregationScore;
	}
	public ScoredText() {
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
		return "ScoredText [text=" + text + ", emphasisScore=" + emphasisScore + ", segregationScore="
				+ segregationScore + "]";
	}
	
	

}
