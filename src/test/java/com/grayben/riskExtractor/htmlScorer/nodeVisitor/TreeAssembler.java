package com.grayben.riskExtractor.htmlScorer.nodeVisitor;

import com.grayben.riskExtractor.htmlScorer.partScorers.Scorer;
import org.jsoup.nodes.Element;

import java.util.*;

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
    private Random random;
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
        this.random = new Random(System.currentTimeMillis());
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
            case MIXED_TREE:
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
        parentAnnotation.text(currentElement.ownText());

        //child
        currentElement = elementsToAttach.remove(0);
        currentIsolatedScores = isolatedScore(currentElement);
        childCumulativeScores = addScores(parentCumulativeScores, currentIsolatedScores);

        childAnnotation = new AnnotatedElement(currentElement, childCumulativeScores);
        childAnnotation.text(currentElement.ownText());
        parentAnnotation.appendChild(childAnnotation);

        //set root of hierarchy to be built
        rootAnnotation = parentAnnotation;
    }

    private AnnotatedElement assembleInTree() {

        while (elementsToAttach.isEmpty() == false) {
            currentElement = elementsToAttach.remove(0);
            currentIsolatedScores = isolatedScore(currentElement);

            //25% chance of moving down the tree
            if (random.nextInt() % 4 == 0) {
                parentCumulativeScores = childCumulativeScores;
                childCumulativeScores = null;
                parentAnnotation = childAnnotation;
                childAnnotation = null;
            }
            childCumulativeScores = addScores(parentCumulativeScores, currentIsolatedScores);
            childAnnotation = new AnnotatedElement(currentElement, childCumulativeScores);
            childAnnotation.text(currentElement.ownText());

            //50/50 chance: whether to append or prepend sibling
            if(random.nextInt() % 2 == 0)
                parentAnnotation.prependChild(childAnnotation);
            else
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
