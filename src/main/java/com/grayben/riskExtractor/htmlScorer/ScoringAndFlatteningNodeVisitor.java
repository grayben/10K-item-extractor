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

    private Set<Scorer<Element>> elementScorers;

    public Set<Scorer<Element>> getElementScorers() {
        return Collections.unmodifiableSet(elementScorers);
    }

    private ScoredText flatText;

    public ScoredText getFlatText() {
        return flatText;
    }

    private Map<String, Integer> currentScores;

    public Map<String, Integer> getCurrentScores(){
        return Collections.unmodifiableMap(currentScores);
    }

    private String currentString;

    private enum Operation {
        INCREMENT,
        DECREMENT
    }


	public ScoringAndFlatteningNodeVisitor(Set<Scorer<Element>> elementScorers) {
		super();
		this.elementScorers = elementScorers;
		this.flatText = new ScoredText();
        this.currentScores = new HashMap<>();

        //initialise currentScores to DEFAULT_SCORE (probably 0)
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
        processElement(element, Operation.INCREMENT);
	}

	@Override
	public void tail(Node node, int depth) {
        validateInput(node, depth);
        if (! isElement(node))
            return;
        Element element = (Element) node;
        processElement(element, Operation.DECREMENT);
	}

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

    private boolean isElement(Node node){
        if (node.getClass().equals(Element.class))
            return true;
        else
            return false;
    }

    private void processElement(Element element, Operation operation){
        this.currentString = element.ownText();
        updateScores(element, operation);
        addScoredTextEntry();
    }

    private void updateScores(Element element, Operation operation){
        for (Scorer<Element> scorer :
                elementScorers) {
            assert this.currentScores.containsKey(scorer.getScoreLabel());
            int currentScore = this.currentScores.get(scorer.getScoreLabel());
            int elementScore = scorer.score(element);
            int newScore;
            switch (operation){
                case INCREMENT:
                    newScore = currentScore + elementScore;
                    break;
                case DECREMENT:
                    newScore = currentScore - elementScore;
                    break;
                default:
                    throw new IllegalArgumentException(
                            "The operation specified is not supported"
                    );
            }
            this.currentScores.put(scorer.getScoreLabel(), newScore);
        }
    }

    private void addScoredTextEntry() {
        ScoredTextElement textElement = new ScoredTextElement(
                this.currentString, this.currentScores
        );
        this.flatText.add(textElement);
    }
}