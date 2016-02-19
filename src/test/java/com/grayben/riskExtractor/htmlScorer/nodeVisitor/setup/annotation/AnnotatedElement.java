package com.grayben.riskExtractor.htmlScorer.nodeVisitor.setup.annotation;

import com.grayben.riskExtractor.htmlScorer.nodeVisitor.setup.ElementListSupplier;
import com.grayben.riskExtractor.htmlScorer.partScorers.Scorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.elementScorers.ElementScorerSetFunction;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

import java.util.*;
import java.util.function.Function;

/**
 * Created by beng on 5/01/2016.
 */
public class AnnotatedElement extends Element {

    private Map<String, Integer> scores;

    public Map<String, Integer> getScores() {
        return Collections.unmodifiableMap(scores);
    }

    public AnnotatedElement(Element element, Map<String, Integer> scores) {
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

    /**
     * Created by beng on 5/01/2016.
     */
    public static class TreeAssembler {

        //input fields
        private final List<Element> elementsToAttach;
        private final Set<? extends Scorer<Element>> elementScorers;

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

        public TreeAssembler(
                final List<Element> elementsToAttach,
                final Set<? extends Scorer<Element>> elementScorers
        ) {

            validateInitParams(elementsToAttach, elementScorers);
            this.elementsToAttach = elementsToAttach;
            this.elementScorers = elementScorers;
            this.random = new Random(System.currentTimeMillis());
            initialiseMaps();
            //setup the first parent/child AnnotatedElement pair
            plantSeedling();
            assembleInTree();
        }

        private void validateInitParams(
                List<Element> elements,
                Set<? extends Scorer<Element>> elementScorers
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
                currentIsolatedScores.put(scorer.getScoreLabel(), Scorer.DEFAULT_SCORE);
                parentCumulativeScores.put(scorer.getScoreLabel(), Scorer.DEFAULT_SCORE);
                childCumulativeScores.put(scorer.getScoreLabel(), Scorer.DEFAULT_SCORE);
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

    /**
     * Created by Ben Gray on 17/02/2016.
     */
    public static class AnnotatedElementFunction implements Function<Set<ElementScorerSetFunction.Content>, AnnotatedElement> {

        private final Function<Set<ElementScorerSetFunction.Content>, Set<Scorer<Element>>> scorersFunction;

        public AnnotatedElementFunction() {
            this.scorersFunction = new ElementScorerSetFunction();
        }

        @Override
        public AnnotatedElement apply(Set<ElementScorerSetFunction.Content> contents) {

            return new TreeAssembler(
                    new ElementListSupplier(scorersFunction.apply(contents)).get(),
                    scorersFunction.apply(contents)
            ).getRootAnnotation();
        }
    }
}
