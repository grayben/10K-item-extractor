package com.grayben.riskExtractor.htmlScorer.scorers;


import org.jsoup.nodes.Element;

/**
 * Element scorer interface. Provide an implementing class to {@link ScoringAndFlatteningVisitor}.
 * <p/>
 * This interface contains a single method that takes an {@link org.jsoup.nodes.Element} and returns a score.
 * @author beng
 *
 */
public abstract class Scorer<T> {

    private String scoreLabel;

    protected Scorer(String scoreLabel){
        this.scoreLabel = scoreLabel;
    }
	
	/**
	 * 
	 * @param input the Element to score
	 * @return the score of element
	 */
	public abstract int score(T input);

    public String getScoreLabel(){
        return this.scoreLabel;
    }
}
