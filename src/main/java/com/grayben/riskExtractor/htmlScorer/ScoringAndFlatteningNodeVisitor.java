package com.grayben.riskExtractor.htmlScorer;

import com.grayben.riskExtractor.htmlScorer.partScorers.Scorer;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.NodeVisitor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScoringAndFlatteningNodeVisitor implements NodeVisitor {
	
	ScoredText flatText;
	
	int emphasisScore = 0;
	int separationScore = 0;
	List<Scorer<Element>> elementScorers;


	public ScoringAndFlatteningNodeVisitor(List<Scorer<Element>> elementScorers) {
		super();
		this.elementScorers = elementScorers;
		this.flatText = new ScoredText();
	}

	@Override
	public void head(Node node, int depth) {
        validateInput(node, depth);
        if(! isElement(node))
            return;
		Element element = (Element) node;

		// if the node has emphasis, add a score to this node.
		if(/* test for attributes */ true){
			// flatStructure.element(id).incrementEmphasis();
		}
		Map<String, Integer> scores = new HashMap<String, Integer>();
		scores.put("emphasis", this.emphasisScore);
		scores.put("segmentation", this.separationScore);
		ScoredTextElement st = new ScoredTextElement(element.ownText(),
							scores);
		
		flatText.add(st);

	}

	@Override
	public void tail(Node node, int depth) {
        validateInput(node, depth);
        if (! isElement(node))
            return;
        Element element = (Element) node;
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
}