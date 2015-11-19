package com.grayben.riskExtractor.htmlScorer;

import java.io.File;
import java.io.IOException;
import java.util.List;

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


	@Override
	public List<ScoredTextElement> scoreHtml(File htmlFile, String charsetName) {
		Document doc = parseHtmlFile(htmlFile, charsetName);
		if(doc != null){
			nt.traverse(doc);
		} else {
			return null;
		}
		return nv.flatText;
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
