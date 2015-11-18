package com.grayben.riskExtractor.htmlScorer;

import java.util.ListIterator;

public interface ScoredText {
	ListIterator<ScoredTextElement> getListIterator();
	ListIterator<ScoredTextElement> getListIterator(int index);
	
}
