package com.grayben.riskExtractor.htmlScorer.partScorers;


import java.util.function.Function;

/**
 * Element scorer interface. Provide an implementing class to
 * {@link com.grayben.riskExtractor.htmlScorer.ScoringAndFlatteningNodeVisitor}.
 * <p>
 * This interface contains a single method that takes an {@link org.jsoup.nodes.Element} and returns a score.
 * @author beng
 *
 */
public abstract class Scorer<T> implements Function<T, Integer>{

    private String scoreLabel;

    public final static int DEFAULT_SCORE = 0;

    protected Scorer(String scoreLabel){
        this.scoreLabel = scoreLabel;
    }
	
	/**
	 * 
	 * @param input the Element to score
	 * @return the score of element
	 */
	public int score(T input){
        return this.apply(input);
    }

    public String getScoreLabel(){
        return this.scoreLabel;
    }
}
