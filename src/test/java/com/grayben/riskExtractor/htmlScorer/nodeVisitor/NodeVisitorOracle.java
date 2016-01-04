package com.grayben.riskExtractor.htmlScorer.nodeVisitor;

import com.grayben.riskExtractor.htmlScorer.ScoredText;
import com.grayben.riskExtractor.htmlScorer.ScoringAndFlatteningNodeVisitor;
import com.grayben.riskExtractor.htmlScorer.partScorers.Scorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.elementScorers.SegmentationElementScorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.tagScorers.TagSegmentationScorer;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * For a nominated test configuration: generates the test input, SUT and expected output.
 * <p/>
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class NodeVisitorOracle {

    private Set<Scorer<Element>> sutParams;

    enum Configuration {
        SEQUENTIAL
    }

    private Configuration config;

    ScoringAndFlatteningNodeVisitor getSUT(Set<Scorer<Element>> elementScorers){
        return new ScoringAndFlatteningNodeVisitor(elementScorers);
    }

    Element getInput(){
        return this.rootElement;
    }

    ScoredText getExpectedOutput() {
        return expectedOutput;
    }

    private Map<Element, Integer> scoresMap;
    private AnnotatedElement rootElement;

    private ScoredText expectedOutput;

    NodeVisitorOracle(Configuration config) {
        validateInitParams(config);
        this.config = config;
        generateArtifacts();
    }

    private void validateInitParams(Configuration config){
        switch(config) {
            case SEQUENTIAL:
                break;
            default:
                throw new IllegalArgumentException("The option was not recognised");
        }
    }

    private void generateArtifacts() {
        generateSutParams();
        generateInputAndExpectedOutput();
    }

    private void generateSutParams() {
        Set<Scorer<Element>> elementScorers = new HashSet<>();
        Scorer<Element> segmentationElementScorer =  new SegmentationElementScorer(
                new TagSegmentationScorer(
                        TagSegmentationScorer.defaultMap()
                )
        );
    }

    private void generateInputAndExpectedOutput() {
    }

    ////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////

    private class AnnotatedElement extends Element {

        Map<String, Integer> scores;

        public AnnotatedElement(Element element, Map<String, Integer> scores){
            this(element.tag(), element.baseUri(), element.attributes(), scores);
        }

        public AnnotatedElement(Tag tag, String baseUri, Attributes attributes, Map<String, Integer> scores) {
            super(tag, baseUri, attributes);
            this.scores = scores;
        }

        public AnnotatedElement(Tag tag, String baseUri, Map<String, Integer> scores) {
            super(tag, baseUri);
            this.scores = scores;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////

    private Map<String, Integer> score(Element element) {
        Map<String, Integer> scores = new HashMap<>();

        for (Scorer<Element> scorer : sutParams) {
            scores.put(scorer.getScoreLabel(), scorer.score(element));
        }
        return scores;
    }

    private void replaceScores(Map<String, Integer> destination, Map<String, Integer> source){
        for(Map.Entry<String, Integer> scoreEntry : source.entrySet()){
            destination.put(scoreEntry.getKey(), scoreEntry.getValue());
        }
    }

    private void incrementScores(Map<String, Integer> destination, Map<String, Integer> source){
        for(Map.Entry<String, Integer> scoreEntry : source.entrySet()){
            String key = scoreEntry.getKey();
            Integer oldValue;
            if (destination.containsKey(key)) oldValue = destination.get(key);

            //TODO: refactor so that value can be retrieved from a registry mapping scorer to scoreLabel s
            else oldValue = 0;
            Integer incoming = scoreEntry.getValue();
            Integer newValue = oldValue + incoming;
            destination.put(key, newValue);
        }
    }

    private void assembleInTree(
            AnnotatedElement currentElement,
            Iterable<Element> elementsToAttach) {
        Map<String, Integer> parentScores = new HashMap<>();
        Map<String, Integer> currentScores = new HashMap<>();

        boolean afterNotChild = false;

        for (Element element : elementsToAttach) {
            afterNotChild = !afterNotChild;

            if (afterNotChild) {
                //since we're moving to a sibling, need to know the parent's scores
                currentScores = parentScores;
            } else {
                parentScores = currentScores;
                currentScores = parentScores;
            }
            incrementScores(currentScores, score(element));
            currentElement = new AnnotatedElement(element, currentScores);
        }
        this.rootElement = currentElement;
    }

    private void assembleInTree(Iterable<Element> elementsToAttach){
        assembleInTree(this.rootElement, elementsToAttach);
    }
}