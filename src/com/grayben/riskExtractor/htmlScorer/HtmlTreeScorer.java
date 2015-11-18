package com.grayben.riskExtractor.htmlScorer;

import java.io.File;

import org.jsoup.parser.Parser;

public class HtmlTreeScorer implements HtmlScorer {
	
	Parser parser;
	DocumentScorer documentScorer;
	

	/**
	 * @param parser
	 * @param documentScorer
	 */
	public HtmlTreeScorer(Parser parser, DocumentScorer documentScorer) {
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
