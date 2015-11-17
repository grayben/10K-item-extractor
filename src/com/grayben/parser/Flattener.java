package com.grayben.parser;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.*;
import org.jsoup.select.NodeVisitor;

public class Flattener implements NodeVisitor {
	
	final List<ScoredText> flatText = new ArrayList<ScoredText>(500);
	
	int emphasisScore = 0;
	int separationScore = 0;
	Scorer emphScorer;
	Scorer segrScorer;
	

	public Flattener(/* the flat structure to populate */) {
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
		ScoredText st = new ScoredText(element.ownText(),
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
