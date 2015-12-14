package com.grayben.riskExtractor.htmlScorer.elementScorers;


import org.jsoup.nodes.Element;

/**
 * Element scorer interface. Provide an implementing class to {@link ScoringAndFlatteningVisitor}.
 * <p/>
 * This interface contains a single method that takes an {@link org.jsoup.nodes.Element} and returns a score.
 * @author beng
 *
 */
public interface Scorer<T> {
	
	/**
	 * 
	 * @param input the Element to score
	 * @return the score of element
	 */
	int score(T input);

    String getScoreLabel();
}
