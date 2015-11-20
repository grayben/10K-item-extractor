package com.grayben.riskExtractor.htmlScorer;

import java.io.File;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.NodeTraversor;

public class TreeHtmlScorer implements HtmlScorer {
	
	NodeTraversor nt;
	ScoringAndFlatteningNodeVisitor nv;
	

	/**
	 * @param parser
	 * @param documentScorer
	 */
	public TreeHtmlScorer() {
		super();
		this.nv = new ScoringAndFlatteningNodeVisitor();
		this.nt = new NodeTraversor(this.nv);
	}
	
	private ScoredText traverse(Document doc)
			throws NullPointerException {
		if(doc != null){
			nt.traverse(doc);
		} else {
			throw new NullPointerException();
		}
		return nv.flatText;
	}


	@Override
	public ScoredText scoreHtml(File htmlFile, String charsetName) {
		Document doc = parseHtmlFile(htmlFile, charsetName);
		return traverse(doc);
	}
	
	@Override
	public ScoredText scoreHtml(String url) {
		Document doc = parseHtmlUrl(url);
		return traverse(doc);
	}
	
	private Document parseHtmlUrl(String url) {
		Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return doc;
	}

	private Document parseHtmlFile(File htmlFile, String charsetName){
		Document doc = null;
		try {
			doc = Jsoup.parse(htmlFile, charsetName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return doc;
	}


	

}
