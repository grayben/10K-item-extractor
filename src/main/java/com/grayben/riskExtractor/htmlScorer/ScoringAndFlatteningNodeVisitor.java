package com.grayben.riskExtractor.htmlScorer;

import com.grayben.riskExtractor.htmlScorer.partScorers.Scorer;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.NodeVisitor;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ScoringAndFlatteningNodeVisitor implements NodeVisitor {

    /**
     * The set of element scorers to apply to arguments to {@link #updateScores(Element, Operation)}.
     */
    private Set<? extends Scorer<Element>> elementScorers;

    /**
     * @return the set of scorers registered with this visitor
     */
    public Set<Scorer<Element>> getElementScorers() {
        return Collections.unmodifiableSet(elementScorers);
    }

    /**
     * The incrementally built list of decorated text elements generated by this visitor
     */
    private ScoredText flatText;

    /**
     * @return the text, decorated with scores, so far produced by this visitor
     */
    public ScoredText getFlatText() {
        return flatText;
    }

    /**
     * The current scores of the element currently being examined
     */
    private Map<String, Integer> currentScores;

    /**
     * @return the map of scores currently held by this visitor
     */
    public Map<String, Integer> getCurrentScores(){
        return Collections.unmodifiableMap(currentScores);
    }

    /**
     * The string currently examined by this visitor
     */
    private String currentString;

    /**
     * Used to communicate the current operation in progress. Passed to helper methods to branch behaviour.
     */
    private enum Operation {
        HEAD,
        TAIL
    }

    /**
     * Construct a visitor relying upon the specified element scorers.
     *
     * @param elementScorers the scorers which this visitor will use to score elements it encounters
     */
	public ScoringAndFlatteningNodeVisitor(Set<? extends Scorer<Element>> elementScorers) {
		super();
		this.elementScorers = elementScorers;
		this.flatText = new ScoredText();
        this.currentScores = new HashMap<>();

        /**
         * Declare required keys for {@link currentScores}, initialise to DEFAULT_SCORE
         */
        for (Scorer<Element> scorer: this.elementScorers){
             currentScores.put(scorer.getScoreLabel(), scorer.DEFAULT_SCORE);
        }
    }

	@Override
	public void head(Node node, int depth) {
        validateInput(node, depth);
        if(! isElement(node))
            return;
		Element element = (Element) node;
        processElement(element, Operation.HEAD);
	}

	@Override
	public void tail(Node node, int depth) {
        validateInput(node, depth);
        if (! isElement(node))
            return;
        Element element = (Element) node;
        processElement(element, Operation.TAIL);
	}

    /**
     * Validate the arguments to {@link #head(Node, int)} or {@link #tail(Node, int)}.
     *
     * @param node the first argument
     * @param depth the second argument
     */
    private void validateInput(Node node, int depth){
        if (node == null)
            throw new NullPointerException(
                    "Node was null"
            );
        if (depth < 0)
            throw new IllegalArgumentException(
                    "Depth was less than 0"
            );
    }

    /**
     *
     * @param node the node argument
     * @return {@code true} if and only if the argument can be considered a member of the class {@link Element}
     */
    private boolean isElement(Node node){
        return Element.class.isAssignableFrom(node.getClass());
    }

    /**
     * Perform processing on the argument element
     * @param element the element to process
     * @param operation at the top level, the type of operation being performed by this visitor
     */
    private void processElement(Element element, Operation operation){
        this.currentString = element.ownText();
        updateScores(element, operation);
        if(operation == Operation.HEAD && ! element.ownText().isEmpty())
            addScoredTextEntry();
    }

    /**
     * Apply each member of {@link #elementScorers} to the specified element
     * @param element the element to score
     * @param operation at the top level, the type of operation being performed by this visitor
     */
    private void updateScores(Element element, Operation operation){
        for (Scorer<Element> scorer :
                elementScorers) {
            /**
             * The initialisation in {@link #getScoreLabel()} was already entered into {@link currentScores}.
             */
            assert this.currentScores.containsKey(scorer.getScoreLabel());

            /**
             * Score the value to be modified in a temporary local variable
             */
            int currentScore = this.currentScores.get(scorer.getScoreLabel());
            int elementScore = scorer.score(element);
            int newScore;

            /**
             * If this visitor is encountering this element for the first time (HEAD in a Depth-first Search),
             * increment {@link currentScore}. Otherwise, decrement it.
             */
            switch (operation){
                case HEAD:
                    newScore = currentScore + elementScore;
                    break;
                case TAIL:
                    newScore = currentScore - elementScore;
                    break;
                default:
                    /**
                     * This shouldn't be possible unless this class is inconsistent and a member of {@link Operation}
                     * is not used by this method and hence redundant.
                     */
                    throw new IllegalArgumentException(
                            "The operation specified is not supported"
                    );
            }
            /**
             * Update {@link #currentScores} with the new value of {@link scorer.getScoreLabel()}.
             */
            this.currentScores.put(scorer.getScoreLabel(), newScore);
        }
    }

    /**
     * Encapsulates the aggregation of the current text element and its scores.
     */
    private void addScoredTextEntry() {
        ScoredTextElement textElement = new ScoredTextElement(
                this.currentString, new HashMap<>(this.currentScores)
        );
        this.flatText.add(textElement);
    }
}