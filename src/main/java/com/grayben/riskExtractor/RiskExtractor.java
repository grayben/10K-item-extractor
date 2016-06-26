package com.grayben.riskExtractor;

import com.grayben.riskExtractor.headingMarker.Nominator;
import com.grayben.riskExtractor.htmlScorer.*;
import com.grayben.riskExtractor.htmlScorer.partScorers.Scorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.TagAndAttribute;
import com.grayben.riskExtractor.htmlScorer.partScorers.elementScorers.EmphasisElementScorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.elementScorers.SegmentationElementScorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.tagScorers.TagAndAttributeScorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.tagScorers.TagEmphasisScorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.tagScorers.TagSegmentationScorer;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

import java.io.*;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class RiskExtractor {

	static final String elementScorerSetRelativePath = "scorer.ser";
	
	static long startTime;



	public static void main(String[] args) throws IOException {

		checkArgs(args);

		startingMain();
		
		takeArgs(args);
		
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

	private static void testParse(String url) throws IOException {
		ScoringAndFlatteningNodeVisitor nv = setupNodeVisitor();
		HtmlScorer scorer = new TreeHtmlScorer(nv);
		ScoredText scoredText = scorer.scoreHtml(url);
		System.out.print(scoredText.toString());
	}

	private static void takeArgs(String[] args) throws IOException {
		checkArgs(args);
		String inileName = args[0];
		String charsetName = args[1];
		String outfileName = args[2];
		File infile = new File(inileName);
		InputStream inputStream = new FileInputStream(infile);

		HtmlScorer scorer = setupTreeHtmlScorer();
		ScoredText scoredText = scorer.scoreHtml(inputStream, charsetName, "");

		Nominator nominator = setupNominator();

		System.out.print(scoredText.toString());
		File outFile = new File(outfileName);
		PrintWriter writer;
		try {
			writer = new PrintWriter(outFile);
			writer.print(scoredText.toString());
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static Set<String> scoreLabels(){
		Set<String> scoreLabels = new HashSet<>();
		scoreLabels.add(SegmentationElementScorer.SCORE_LABEL);
		scoreLabels.add(EmphasisElementScorer.SCORE_LABEL);
		return scoreLabels;
	}

	private static Function<Map<String, Integer>, Integer> sumAllScoresWithLabelIn(Collection<String> relevantScoreLabels){
		return scoresMap -> {
			int scoreSum = 0;
			for (String label :
					relevantScoreLabels) {
				scoreSum += scoresMap.get(label);
			}
			return scoreSum;
		};
	}

	private static Nominator setupNominator() {
		Function<ScoredText, List<Integer>> computeNomineeIndices = scoredText -> {
            List<Integer> nominees;
            nominees = filterCandidatesViaTextRegexPredicate(HEADING_NOMINEE_REGEX.asPredicate()).apply(scoredText, null);
            nominees = filterNomineeHeadingsViaHtmlScores(sumAllScoresWithLabelIn(scoreLabels())).apply(scoredText, nominees);
            return null;
        };
		return new Nominator(computeNomineeIndices);
	}

	private static int highestAggregateScore(List<ScoredTextElement> scoredTextElements, Iterable<Integer> candidateIndices, Function<Map<String, Integer>, Integer> scoreMapAggregator){
		int largestSum = Integer.MIN_VALUE;
		for (int i = 0; i < scoredTextElements.size(); i++){
			ScoredTextElement element = scoredTextElements.get(i);
			int elementScoreAggregate = scoreMapAggregator.apply(element.getScores());
			if (elementScoreAggregate > largestSum){
				largestSum = elementScoreAggregate;
			}
		}
		return largestSum;
	}

	public static BiFunction<ScoredText, List<Integer>, List<Integer>> filterNomineeHeadingsViaHtmlScores(Function<Map<String, Integer>, Integer> scoreMapAggregator){
		return (scoredText, candidateIndices) -> {
			List<Integer> nomineeTextIndices = new ArrayList<>();
			List<ScoredTextElement> scoredTextElementList = scoredText.getList();
			int thresholdAggregateScore = highestAggregateScore(scoredTextElementList, candidateIndices, scoreMapAggregator);
			for (int i = 0; i < scoredTextElementList.size(); i++){
				if (candidateIndices.contains(i)){
					ScoredTextElement element = scoredTextElementList.get(i);
					int aggregateHtmlScore = scoreMapAggregator.apply(element.getScores());
					if (aggregateHtmlScore >= thresholdAggregateScore){
						nomineeTextIndices.add(i);
					}
				}
			}
			return nomineeTextIndices;
		};
	}

	public static Pattern HEADING_NOMINEE_REGEX = Pattern.compile("item [0-9]+.*?$", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
	public static Pattern HEADING_ELECTEE_REGEX = Pattern.compile("item 1A.*?Risk.*?$", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);

	public static BiFunction<ScoredText, List<Integer>, List<Integer>> filterCandidatesViaTextRegexPredicate(Predicate<String> regex) {
		return (scoredText, candidateIndices) -> {
			List<Integer> nomineeTextIndices = new ArrayList<>();
			List<ScoredTextElement> scoredTextElements = scoredText.getList();;
			for (int i = 0; i < scoredTextElements.size(); i++){
				if(candidateIndices == null || candidateIndices.contains(i)) {
					ScoredTextElement element = scoredTextElements.get(i);
					if (regex.test(element.getTextElement())) {
						nomineeTextIndices.add(i);
					}
				}
			}
			return nomineeTextIndices;
		};
	}

	private static String mainUsageMessage(){
		return "Usage\nprogram_name input_csv charset_name output_csv";
	}

	private static void checkArgs(String[] args) {
		int argsLen = 3;
		if (args.length != argsLen) {
			System.out.println(mainUsageMessage());
			throw new IllegalArgumentException(mainUsageMessage());
		}
	}

	public static TreeHtmlScorer setupTreeHtmlScorer(){
		return new TreeHtmlScorer(setupNodeVisitor());
	}

	public static ScoringAndFlatteningNodeVisitor setupNodeVisitor(){
		Set<Scorer<Element>> elementScorers = new HashSet<>();
		elementScorers.add(
				new SegmentationElementScorer(
						new TagSegmentationScorer(setupTagSegmentationScoreMap())
				)
		);
		elementScorers.add(
				new EmphasisElementScorer(
						new TagEmphasisScorer(setupTagEmphasisScoreMap()),
						new TagAndAttributeScorer(setupTagAndAttributeScoreMap())
				)
		);
		return new ScoringAndFlatteningNodeVisitor(elementScorers);
	}

	/**
     * @return the default score map for this class
     */
    public static final Map<Tag, Integer> setupTagEmphasisScoreMap() {

        //Map<Tag, Integer> tagScores = this.getTagScores();
        Map<Tag, Integer> tagScoreMap = new HashMap<>();
        tagScoreMap.put(Tag.valueOf("b"), 1);
        tagScoreMap.put(Tag.valueOf("strong"), 1);
        tagScoreMap.put(Tag.valueOf("h1"), 1);
        tagScoreMap.put(Tag.valueOf("h2"), 1);
        tagScoreMap.put(Tag.valueOf("h3"), 1);
        tagScoreMap.put(Tag.valueOf("h4"), 1);
        tagScoreMap.put(Tag.valueOf("h5"), 1);
        tagScoreMap.put(Tag.valueOf("u"), 1);

        return tagScoreMap;

    }

	/**
     * @return the default score map for this class
     */
    public static Map<TagAndAttribute, Integer> setupTagAndAttributeScoreMap() {

        String[] tagNames = {"font", "div", "p"};
        Set<Tag> tags = new HashSet<>();
        /**
         * {@link tags} := ("font", "div", "p")
         */
        for (String tagName: tagNames) {
            tags.add(Tag.valueOf(tagName));
        }

        Set<Attribute> attributes = new HashSet<>();
        /**
         * {@link attributes} := ("style" => "bold", "style" => "underline")
         */
        attributes.add(new Attribute("style", "bold"));
        attributes.add(new Attribute("style", "underline"));

        Set<TagAndAttribute> tagAndAttributes = new HashSet<>();
        /**
         * {@link tagAndAttributes} := cartesianProduct({@link tags}, {@link attributes})
         */
        for (Tag tag: tags)
            for (Attribute attribute : attributes)
                tagAndAttributes.add(
                        new TagAndAttribute(
                                tag,
                                attribute
                        )
                );

        Map<TagAndAttribute, Integer> scoresMap = new HashMap<>();
        for (TagAndAttribute tagAndAttribute : tagAndAttributes) {
            scoresMap.put(tagAndAttribute, 1);
        }

        return scoresMap;

    }

	/**
     * @return the default score map for this class
     */
    public static final Map<Tag, Integer> setupTagSegmentationScoreMap() {

        Map<Tag, Integer> tagScoreMap = new HashMap<>();
        tagScoreMap.put(Tag.valueOf("br"), 1);
        tagScoreMap.put(Tag.valueOf("dd"), 1);
        tagScoreMap.put(Tag.valueOf("dl"), 1);
        tagScoreMap.put(Tag.valueOf("dt"), 1);
        tagScoreMap.put(Tag.valueOf("form"), 1);
        tagScoreMap.put(Tag.valueOf("hr"), 1);
        tagScoreMap.put(Tag.valueOf("li"), 1);
        tagScoreMap.put(Tag.valueOf("p"), 1);
        tagScoreMap.put(Tag.valueOf("pre"), 1);
        tagScoreMap.put(Tag.valueOf("table"), 1);
        tagScoreMap.put(Tag.valueOf("tbody"), 1);
        tagScoreMap.put(Tag.valueOf("td"), 1);
        tagScoreMap.put(Tag.valueOf("th"), 1);
        tagScoreMap.put(Tag.valueOf("title"), 1);
        tagScoreMap.put(Tag.valueOf("tr"), 1);
        tagScoreMap.put(Tag.valueOf("ul"), 1);
        tagScoreMap.put(Tag.valueOf("div"), 1);

        return tagScoreMap;

    }
}
