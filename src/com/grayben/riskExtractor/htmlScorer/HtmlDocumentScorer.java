package com.grayben.riskExtractor.htmlScorer;

import org.jsoup.select.NodeTraversor;
import org.jsoup.select.NodeVisitor;

public class HtmlDocumentScorer {
	
	NodeTraversor nt;
	NodeVisitor nv;


	/**
	 * @param nt the NodeTraversor to traverse the 
	 * @param nv
	 */
	public HtmlDocumentScorer(NodeTraversor nt, NodeVisitor nv) {
		super();
		this.nt = nt;
		this.nv = nv;
	}
	
	
}
