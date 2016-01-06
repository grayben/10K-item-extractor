package com.grayben.riskExtractor;

import com.grayben.riskExtractor.htmlScorer.HtmlScorer;
import com.grayben.riskExtractor.htmlScorer.ScoredText;
import com.grayben.riskExtractor.htmlScorer.ScoringAndFlatteningNodeVisitor;
import com.grayben.riskExtractor.htmlScorer.TreeHtmlScorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.Scorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.elementScorers.EmphasisElementScorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.elementScorers.SegmentationElementScorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.tagScorers.TagAndAttributeScorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.tagScorers.TagEmphasisScorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.tagScorers.TagSegmentationScorer;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

public class RiskExtractor {
	
	static long startTime;

	public static void main(String[] args) {
		startingMain();
		
		boolean testParse = true;
		if(testParse == true){
			testParse("http://google.com");
		} else takeArgs(args);
		
		completingMain();
	}
	
	private static void startingMain(){
		startTimer();
		System.out.println("Starting main");
	}
	
	private static void completingMain(){
		System.out.println();
		System.out.println("Program finished.");
		printTimeElapsed();
	}
	
	private static void startTimer(){
		startTime = System.currentTimeMillis();
	}
	
	private static void printTimeElapsed(){
		long currentTime = System.currentTimeMillis();
		float secondsElapsed = (float)(currentTime - startTime)/1000;
		System.out.println("Time elapsed: " + secondsElapsed + " seconds");
	}

	private static ScoringAndFlatteningNodeVisitor setupNodeVisitor(){
		Set<Scorer<Element>> elementScorers = new HashSet<>();
		elementScorers.add(
				new SegmentationElementScorer(
						new TagSegmentationScorer(TagSegmentationScorer.defaultMap())
				)
		);
		elementScorers.add(
				new EmphasisElementScorer(
						new TagEmphasisScorer(TagEmphasisScorer.defaultMap()),
						new TagAndAttributeScorer(TagAndAttributeScorer.defaultMap())
				)
		);
		ScoringAndFlatteningNodeVisitor nv = new ScoringAndFlatteningNodeVisitor(elementScorers);
		return nv;
	}
	
	private static void testParse(String url){
		ScoringAndFlatteningNodeVisitor nv = setupNodeVisitor();
		HtmlScorer scorer = new TreeHtmlScorer(nv);
		ScoredText scoredText = scorer.scoreHtml(url);
		System.out.print(scoredText.toString());
	}
	
	private static void takeArgs(String[] args){
		checkArgs(args);
		String fileName = args[0];
		File htmlFile = new File(fileName);
		String charsetName = args[1];
		HtmlScorer scorer = new TreeHtmlScorer(setupNodeVisitor());
		ScoredText scoredText = scorer.scoreHtml(htmlFile, charsetName);
		System.out.print(scoredText.toString());
		File outFile = new File("/Volumes/MBS Data/EDGAR-Form10K/output.txt");
		PrintWriter writer;
		try {
			writer = new PrintWriter(outFile);
			writer.print(scoredText.toString());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	private static void checkArgs(String[] args){
		int argsLen = 2;
		if(args.length != argsLen){
			System.err.println("ERROR: must pass " + argsLen + " arguments.");
			System.exit(1);
		}
	}

}
