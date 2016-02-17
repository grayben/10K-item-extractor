package com.grayben.riskExtractor.htmlScorer.nodeVisitor.setup;

import com.grayben.riskExtractor.htmlScorer.partScorers.Scorer;
import org.jsoup.nodes.Element;

import java.util.*;
import java.util.function.Function;

/**
 * Created by Ben Gray on 17/02/2016.
 */
public class ElementListFunction implements Function<Set<Scorer<Element>>, List<Element>> {

    @Override
    public List<Element> apply(Set<Scorer<Element>> scorers) {

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
}
