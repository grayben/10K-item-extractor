package com.grayben.riskExtractor.htmlScorer.partScorers;


import java.util.function.Function;

/**
 * Element scorer interface. Provide an implementing class to
 * {@link com.grayben.riskExtractor.htmlScorer.ScoringAndFlatteningNodeVisitor}.
 * <p>
 * This interface contains a single method that takes an {@link org.jsoup.nodes.Element} and
 * returns a score.
 * <p>
 * Created by Ben Gray, 2015
 */
public abstract class Scorer<T> implements Function<T, Integer>{

    /**
     * The string (uniquely) labelling this class.
     * <p>
     * Used to identify this class in a collection of {@link Scorer}s.
     */
    private String scoreLabel;

    /**
     * The score to be returned when the scorer cannot explicitly determine
     * a score for the argument.
     */
    public final static int DEFAULT_SCORE = 0;

    /**
     * Construct a scorer with the specified name.
     * @param scoreLabel the class-unique label for this scorer
     */
    protected Scorer(String scoreLabel){
        this.scoreLabel = scoreLabel;
    }
	
	/**
	 * Determine a score for the argument object
	 * @param input the object to score
	 * @return the score of the object
	 */
	public int score(T input){
        return this.apply(input);
    }

    /**
     * @return the class-unique score label passed in at construction.
     */
    public String getScoreLabel(){
        return this.scoreLabel;
    }
}
