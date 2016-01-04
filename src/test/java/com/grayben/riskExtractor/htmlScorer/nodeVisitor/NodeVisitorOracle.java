package com.grayben.riskExtractor.htmlScorer.nodeVisitor;

import com.grayben.riskExtractor.htmlScorer.ScoredText;
import com.grayben.riskExtractor.htmlScorer.ScoringAndFlatteningNodeVisitor;
import com.grayben.riskExtractor.htmlScorer.partScorers.Scorer;
import org.jsoup.nodes.Element;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

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
    private Element rootElement;

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
    }

    private void generateInputAndExpectedOutput() {
    }

    ////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////

    private void assembleInSemiRandomTree (
            Element currentElement,
            Iterable<Element> elementsToAttach) {

        boolean afterNotChild = false;

        for (Element element :
                elementsToAttach) {
            afterNotChild = !afterNotChild;
            if (afterNotChild) {
                currentElement.after(element);
            } else {
                currentElement.appendChild(element);
            }
            currentElement = element;
        }
        this.rootElement = currentElement;
    }

    private void assembleInSemiRandomTree(Iterable<Element> elementsToAttach){
        assembleInSemiRandomTree(this.rootElement, elementsToAttach);
    }
}