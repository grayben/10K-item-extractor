package com.grayben.riskExtractor.HtmlScorer;


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
	 * @param element the Element to score
	 * @return the score of element
	 */
	public int score(T input);
}
