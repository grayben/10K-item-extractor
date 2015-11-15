package com.grayben.parser;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.select.NodeTraversor;
import org.jsoup.select.NodeVisitor;

public class Parser {

	public static void main(String[] args) throws IOException {
		System.out.println("Starting parse");
		File input = null;
		Document doc = null;
		int numDocuments = 100;
		
		// 
					doc = Jsoup.connect("http://google.com/").get();
					Elements elements = doc.select("*");
					String toHtml = elements.text();
					System.out.print(toHtml);
					
					NodeVisitor flattener = new Flattener();
					NodeTraversor nt = new NodeTraversor(flattener);
					nt.traverse(doc);
		
		final long startTime = System.currentTimeMillis();
		for(int i = 1; i <= numDocuments; i++){
			input = new File("/Volumes/MBS Data/EDGAR-Form10K/filings/" + i + ".txt");
			doc = Jsoup.parse(input, "ASCII");
			elements = doc.select("*");
			String html = elements.text();
			System.out.print(html);
			
			
		}
		final long endTime = System.currentTimeMillis();
		final long dT = endTime - startTime;
		final long secondsElapsed = dT/1000;
		System.out.println("Finished parsing: " 
				+ numDocuments + " in " + secondsElapsed + " seconds.");
		
		
	}

}
