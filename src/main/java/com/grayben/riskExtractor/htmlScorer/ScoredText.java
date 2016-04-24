package com.grayben.riskExtractor.htmlScorer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a list of {@link ScoredTextElement}s.
 */
public class ScoredText {
	private List<ScoredTextElement> text;

    /**
     * Construct an empty object.
     */
	public ScoredText() {
		super();
		text = new ArrayList<>();
	}

    /**
     * @return the text only, without the scores.
     */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (ScoredTextElement element : text) {
			sb.append(element.getTextElement());
			sb.append(" ");
		}
		return sb.toString().trim();
	}

    /**
     * Append the specified element.
     * @param st the element to append.
     */
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

    /**
     * @return the underlying list
     */
	public List<ScoredTextElement> getList(){
        return Collections.unmodifiableList(this.text);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ScoredText that = (ScoredText) o;

        return text != null ? text.equals(that.text) : that.text == null;

    }

    @Override
    public int hashCode() {
        return text != null ? text.hashCode() : 0;
    }
}
