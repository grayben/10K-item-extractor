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

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class RiskExtractor {
	
	static long startTime;

	public static void main(String[] args) throws IOException {

		checkArgs(args);

		startingMain();
		
		boolean isTestParse = true;
		if(isTestParse){
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
		return new ScoringAndFlatteningNodeVisitor(elementScorers);
	}
	
	private static void testParse(String url) throws IOException {
		ScoringAndFlatteningNodeVisitor nv = setupNodeVisitor();
		HtmlScorer scorer = new TreeHtmlScorer(nv);
		ScoredText scoredText = scorer.scoreHtml(url);
		System.out.print(scoredText.toString());
	}
	
	private static void takeArgs(String[] args) throws IOException {
		checkArgs(args);
		String fileName = args[0];
		File htmlFile = new File(fileName);
		InputStream inputStream = new FileInputStream(htmlFile);
		String charsetName = args[1];
		HtmlScorer scorer = new TreeHtmlScorer(setupNodeVisitor());
		ScoredText scoredText = scorer.scoreHtml(inputStream, charsetName, "");
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

	private static String mainUsageMessage(){
		return "Usage\nprogram_name input_csv output_csv";
	}

	private static void checkArgs(String[] args) {
		int argsLen = 2;
		if (args.length != argsLen) {
			System.out.println(mainUsageMessage());
			throw new IllegalArgumentException(mainUsageMessage());
		}
	}

}
