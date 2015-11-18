package com.grayben.riskExtractor.htmlScorer;

import java.util.ArrayList;
import java.util.List;

public class ArrayListScoredText {
	List<ScoredTextElement> elements;
	
	public ArrayListScoredText(){
		super();
		this.elements = new ArrayList<ScoredTextElement>(100000);
	}

	public void add(ScoredTextElement st) {
		this.elements.add(st);
		
	}
}
