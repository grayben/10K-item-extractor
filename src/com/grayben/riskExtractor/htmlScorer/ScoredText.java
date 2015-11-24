package com.grayben.riskExtractor.htmlScorer;

import java.util.ArrayList;
import java.util.List;

public class ScoredText {
	private List<ScoredTextElement> text;
	
	/**
	 * @param text
	 */
	public ScoredText() {
		super();
		text = new ArrayList<ScoredTextElement>();
	}

	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		for(ScoredTextElement element : text){
			sb.append(element.getTextElement());
		}
		return sb.toString();
	}

	public void add(ScoredTextElement st) {
		text.add(st);
	}
}
