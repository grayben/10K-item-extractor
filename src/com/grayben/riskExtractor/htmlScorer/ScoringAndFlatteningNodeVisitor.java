package com.grayben.riskExtractor.htmlScorer;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.*;
import org.jsoup.select.NodeVisitor;

import com.grayben.riskExtractor.htmlScorer.elementScorers.ElementScorer;

public class ScoringAndFlatteningNodeVisitor implements NodeVisitor {
	
	ScoredText flatText = new ScoredText();
	
	int emphasisScore = 0;
	int separationScore = 0;
	ElementScorer<Element> emphScorer;
	ElementScorer<Element> segrScorer;
	

	public ScoringAndFlatteningNodeVisitor(/* the flat structure to populate */) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void head(Node node, int depth) {
		Element element = null;
		if(!node.getClass().equals(Element.class)){
			System.err.println("The node was not an " + Element.class
					+ ": " + node.toString());
			return;
		} else {
			element = (Element) node;
		}
		// if the node has emphasis, add a score to this node.
		if(/* test for attributes */ true){
			// flatStructure.element(id).incrementEmphasis();
		}
		ScoredTextElement st = new ScoredTextElement(element.ownText(),
							this.emphasisScore,
							this.separationScore);
		
		flatText.add(st);

	}

	@Override
	public void tail(Node node, int depth) {
		// TODO Auto-generated method stub

	}
	
	private static boolean isEmphasisTag(Element element){
		
		return false;
		
	}

}
