package com.grayben.riskExtractor.htmlScorer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScoredText {
	private List<ScoredTextElement> text;

	public ScoredText() {
		super();
		text = new ArrayList<>();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (ScoredTextElement element : text) {
			sb.append(element.getTextElement());
			sb.append(" ");
		}
		return sb.toString().trim();
	}

	public void add(ScoredTextElement st) {
        if(st.getScores() == null){
            throw new NullPointerException(
                    "param.getScores() must not be null"
            );
        }
        if(st.getScores().isEmpty()){
            throw new IllegalArgumentException(
                    "param.getScores() must not be empty"
            );
        }
        if(st.getTextElement() == null){
            throw new NullPointerException(
                    "param.getTextElement() must not be null"
            );
        }
        if(st.getTextElement().isEmpty()){
            throw new IllegalArgumentException(
                    "param.getTextElement() must not be empty"
            );
        }
		text.add(st);
	}

	public List<ScoredTextElement> getList(){
        return Collections.unmodifiableList(this.text);
    }
}
