package com.grayben.riskExtractor.htmlScorer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;
import org.jsoup.select.NodeTraversor;

import java.io.File;
import java.io.IOException;
import java.util.ListIterator;

public class TreeHtmlScorer implements HtmlScorer {
	
	NodeTraversor nt;
	ScoringAndFlatteningNodeVisitor nv;

	public TreeHtmlScorer() {
		super();
		//this.nv = new ScoringAndFlatteningNodeVisitor();
		this.nt = new NodeTraversor(this.nv);
	}
	
	private ScoredText traverse(Node node)
			throws NullPointerException {
		if(node != null){
			nt.traverse(node);
		} else {
			throw new NullPointerException();
		}
		return nv.getFlatText();
	}


	@Override
	public ScoredText scoreHtml(File htmlFile, String charsetName) {
		Document doc = parseHtmlFile(htmlFile, charsetName);
		Element body = getHtmlBody(doc);
		return traverse(body);
	}
	
	private Element getHtmlBody(Document doc) {
		Elements elements = doc.select("html > body");
		ListIterator<Element> iterator = elements.listIterator();
		int i = 1;
		while (iterator.hasNext()){
			System.out.println(i++);
			System.out.println(iterator.next().html());	
		}

		//TODO: implement
		
		return null;
	}

	@Override
	public ScoredText scoreHtml(String url) {
		//TODO: use JSoup to get a Connection.Response so can remove SEC head
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
		String crapHtml = "<SEC-DOCUMENT> all this crap\n"
				+ "more crap"
				+ "<html>"
				+ "<head>a header</head>"
				+ "<body>"
				+ "real HTML"
				+ "</body>"
				+ "</html>"
				+ "even more crap";
		
		System.out.println();
		System.out.println("CRAP HTML");
		System.out.println(crapHtml);
		System.out.println();
		
		Whitelist wl = Whitelist.relaxed();
		String cleanHtml = Jsoup.clean(crapHtml, wl);
		
		System.out.println("CLEANED HTML");
		System.out.println();
		System.out.print(cleanHtml);
		System.out.println();
		
		doc = Jsoup.parse(cleanHtml);
		System.out.println("PARSED HTML");
		System.out.println();
		System.out.print(doc.html());
		System.out.println();
		return doc;
	}
}
