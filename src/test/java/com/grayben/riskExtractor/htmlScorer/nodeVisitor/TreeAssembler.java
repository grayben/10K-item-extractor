package com.grayben.riskExtractor.htmlScorer.nodeVisitor;

import com.grayben.riskExtractor.htmlScorer.partScorers.Scorer;
import org.jsoup.nodes.Element;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by beng on 5/01/2016.
 */
class TreeAssembler {

    //input fields
    private List<Element> elementsToAttach;

    private NodeVisitorOracle.Configuration configuration;
    private Set<Scorer<Element>> elementScorers;

    //output fields
    private AnnotatedElement rootAnnotation;

    public AnnotatedElement getRootAnnotation() {
        return rootAnnotation;
    }

    //internal working fields
    private Element currentElement;
    private HashMap<String, Integer> currentIsolatedScores;

    private AnnotatedElement parentAnnotation;
    private HashMap<String, Integer> parentCumulativeScores;

    private AnnotatedElement childAnnotation;
    private HashMap<String, Integer> childCumulativeScores;


    TreeAssembler(
            List<Element> elementsToAttach,
            NodeVisitorOracle.Configuration configuration,
            Set<Scorer<Element>> elementScorers
    ) {

        validateInitParams(elementsToAttach, configuration, elementScorers);
        this.elementsToAttach = elementsToAttach;
        this.configuration = configuration;
        this.elementScorers = elementScorers;
        initialiseMaps();
        //setup the first parent/child AnnotatedElement pair
        plantSeedling();
        assembleInTree();
    }

    private void validateInitParams(
            List<Element> elements,
            NodeVisitorOracle.Configuration configuration,
            Set<Scorer<Element>> elementScorers
    ) {
        if (elements == null) {
            throw new NullPointerException(
                    "The input elements list was null"
            );
        }
        if (elements.size() < 2) {
            throw new IllegalArgumentException(
                    "The input element list did not have at least 2 elements"
            );
        }
        if (configuration == null) {
            throw new NullPointerException(
                    "The input configuration option was null"
            );
        }
        switch (configuration) {
            case SEQUENTIAL:
                break;
            default:
                throw new IllegalArgumentException(
                        "The input configuration option was not recognised"
                );
        }
        if (elementScorers == null) {
            throw new NullPointerException(
                    "The input element scorers set was null"
            );
        }
        if (elementScorers.isEmpty()) {
            throw new IllegalArgumentException(
                    "The input element scorers set was empty"
            );
        }
    }

    private void initialiseMaps() {
        //make sure all the fields are instantiated, contain keys and default values
        currentIsolatedScores = new HashMap<>();
        parentCumulativeScores = new HashMap<>();
        childCumulativeScores = new HashMap<>();

        for (Scorer<Element> scorer : this.elementScorers) {
            currentIsolatedScores.put(scorer.getScoreLabel(), scorer.DEFAULT_SCORE);
            parentCumulativeScores.put(scorer.getScoreLabel(), scorer.DEFAULT_SCORE);
            childCumulativeScores.put(scorer.getScoreLabel(), scorer.DEFAULT_SCORE);
        }
    }

    private void plantSeedling() {
        //parent

        currentElement = elementsToAttach.remove(0);
        currentIsolatedScores = isolatedScore(currentElement);
        parentCumulativeScores = copyScores(currentIsolatedScores);

        parentAnnotation = new AnnotatedElement(currentElement, parentCumulativeScores);

        //child
        currentElement = elementsToAttach.remove(0);
        currentIsolatedScores = isolatedScore(currentElement);
        childCumulativeScores = addScores(parentCumulativeScores, currentIsolatedScores);

        childAnnotation = new AnnotatedElement(currentElement, childCumulativeScores);
        parentAnnotation.appendChild(childAnnotation);

        //set root of hierarchy to be built
        rootAnnotation = parentAnnotation;
    }

    private AnnotatedElement assembleInTree() {

        boolean childNotSibling = false;
        while (elementsToAttach.isEmpty() == false) {
            currentElement = elementsToAttach.remove(0);
            currentIsolatedScores = isolatedScore(currentElement);

            childNotSibling = !childNotSibling;
            if (childNotSibling) {
                parentCumulativeScores = childCumulativeScores;
                childCumulativeScores = null;
                parentAnnotation = childAnnotation;
                childAnnotation = null;
            }
            childCumulativeScores = addScores(parentCumulativeScores, currentIsolatedScores);
            childAnnotation = new AnnotatedElement(currentElement, childCumulativeScores);

            parentAnnotation.appendChild(childAnnotation);
        }
        return rootAnnotation;
    }

    private HashMap<String, Integer> copyScores(HashMap<String, Integer> source) {
        HashMap<String, Integer> newMap = (HashMap<String, Integer>) source.clone();
        return newMap;
    }

    private HashMap<String, Integer> isolatedScore(Element element) {
        HashMap<String, Integer> scores = new HashMap<>();

        for (Scorer<Element> scorer : elementScorers) {
            scores.put(scorer.getScoreLabel(), scorer.score(element));
        }
        return scores;
    }

    private HashMap<String, Integer> addScores(Map<String, Integer> map1, Map<String, Integer> map2) {

        assert map1.keySet().equals(map2.keySet());

        HashMap<String, Integer> mapSum = new HashMap<>();

        for (Map.Entry<String, Integer> entry1 : map1.entrySet()) {
            String key = entry1.getKey();
            Integer score1 = entry1.getValue();
            Integer score2 = map2.get(key);
            Integer scoreSum = score1 + score2;

            mapSum.put(key, scoreSum);
        }

        return mapSum;
    }

}
