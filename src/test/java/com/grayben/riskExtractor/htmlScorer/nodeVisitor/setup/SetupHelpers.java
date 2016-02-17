package com.grayben.riskExtractor.htmlScorer.nodeVisitor.setup;

import com.grayben.riskExtractor.htmlScorer.partScorers.Scorer;
import org.jsoup.nodes.Element;

import java.util.*;

/**
 * Created by Ben Gray on 12/02/2016.
 */
class SetupHelpers {

    private SetupHelpers(){}

    static List<Element> configureElementList(Set<Scorer<Element>> scorers){
        List<Element> targetElements = new ArrayList<>();
        targetElements.addAll(UtilsThatShouldBeRefactored.getEmphasisedTargetElementsAndScores(scorers).keySet());
        targetElements.addAll(UtilsThatShouldBeRefactored.getSegmentedTargetElementsAndScores(scorers).keySet());
        targetElements.addAll(UtilsThatShouldBeRefactored.generateAndScoreRandomElements(scorers, 100).keySet());
        for (Element element : targetElements) {
            element.text(UtilsThatShouldBeRefactored.randomString());
        }
        Collections.shuffle(targetElements, new Random());
        return targetElements;
    }

    static AnnotatedElement configureAnnotatedElement(){

        Set<Scorer<Element>> elementScorers = new ElementScorersSupplier().get();

        return new AnnotatedElement.TreeAssembler(
                configureElementList(elementScorers),
                elementScorers
        ).getRootAnnotation();
    }
}
