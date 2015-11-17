package com.grayben.riskExtractor.htmlScorer;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.select.NodeTraversor;
import org.jsoup.select.NodeVisitor;

public class HtmlScorer {
	
	NodeTraversor nt;
	NodeVisitor nv;

	public static void main(String[] args) throws IOException {
		Document doc = Jsoup.connect("http://google.com/").get();
	}
	
	
}
