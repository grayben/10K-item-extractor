package com.grayben.riskExtractor.htmlScorer;

import java.util.ArrayList;
import java.util.List;

public class ScoredText {
	List<ScoredTextElement> elements;
	
	public ScoredText(){
		super();
		this.elements = new ArrayList<ScoredTextElement>(1000);
	}
	
	public ScoredText(int initialCapacity){
		super();
		this.elements = new ArrayList<ScoredTextElement>(initialCapacity);
	}

	public void add(ScoredTextElement st) {
		this.elements.add(st);
		
	}
}
