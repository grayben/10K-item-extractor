package com.grayben.riskExtractor.htmlScorer;

import java.io.File;

import org.jsoup.parser.Parser;

public class TreeHtmlScorer implements HtmlScorer {
	
	Parser parser;
	HtmlDocumentScorer documentScorer;
	

	/**
	 * @param parser
	 * @param documentScorer
	 */
	public TreeHtmlScorer(Parser parser, HtmlDocumentScorer documentScorer) {
		super();
		this.parser = parser;
		this.documentScorer = documentScorer;
	}


	@Override
	public ScoredText scoreHtml(File htmlFile) {
		// TODO Auto-generated method stub
		return null;
	}

}
