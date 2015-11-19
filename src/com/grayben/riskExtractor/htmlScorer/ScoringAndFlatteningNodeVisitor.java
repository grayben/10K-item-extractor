package com.grayben.riskExtractor.htmlScorer;

import java.util.HashMap;
import java.util.Map;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.NodeVisitor;

import com.grayben.riskExtractor.htmlScorer.elementScorers.ElementScorer;
import com.grayben.riskExtractor.htmlScorer.elementScorers.EmphasisElementScorer;
import com.grayben.riskExtractor.htmlScorer.elementScorers.SegmentationElementScorer;

public class ScoringAndFlatteningNodeVisitor implements NodeVisitor {
	
	ArrayListScoredText flatText;
	
	int emphasisScore = 0;
	int separationScore = 0;
	ElementScorer<Element> emphScorer;
	ElementScorer<Element> segmScorer;
	

	public ScoringAndFlatteningNodeVisitor() {
		super();
		this.flatText = new ArrayListScoredText();
		this.emphScorer = new EmphasisElementScorer();
		this.segmScorer = new SegmentationElementScorer();
	}

	@Override
	public void head(Node node, int depth) {
		Element element = null;
		if(!node.getClass().equals(Element.class)){
			/* System.err.println("The node was not an " + Element.class
					+ ": " + node.toString());
					*/
			return;
		} else {
			element = (Element) node;
		}
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
		// TODO Auto-generated method stub

	}

}
