package com.grayben.riskExtractor;

import java.io.File;
import java.util.List;

import com.grayben.riskExtractor.htmlScorer.HtmlScorer;
import com.grayben.riskExtractor.htmlScorer.ScoredTextElement;
import com.grayben.riskExtractor.htmlScorer.TreeHtmlScorer;

public class RiskExtractor {

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		checkArgs(args);
		String fileName = args[0];
		File htmlFile = new File(fileName);
		String charsetName = args[1];
		HtmlScorer scorer = new TreeHtmlScorer();
		List<ScoredTextElement> scoredText = scorer.scoreHtml(htmlFile, charsetName);
		System.out.print(scoredText.toString());
		System.out.println();
		System.out.println("Program finished.");
		long endTime = System.currentTimeMillis();
		long secondsElapsed = (endTime - startTime)/1000;
		System.out.println("Time elapsed: " + secondsElapsed);

	}
	
	private static void checkArgs(String[] args){
		int argsLen = 2;
		if(args.length != argsLen){
			System.err.println("ERROR: must pass " + argsLen + " arguments.");
			System.exit(1);
		}
	}

}
