package com.grayben.riskExtractor.helpers;

import com.grayben.riskExtractor.htmlScorer.partScorers.Scorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.TagAndAttribute;
import com.grayben.riskExtractor.htmlScorer.partScorers.elementScorers.EmphasisElementScorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.elementScorers.SegmentationElementScorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.tagScorers.TagSegmentationScorer;
import org.apache.commons.lang3.RandomStringUtils;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

import java.util.*;
import java.util.function.Supplier;

/**
 * Created by Ben Gray on 17/02/2016.
 */
public class ElementListSupplier implements Supplier<List<Element>> {

    private final Set<Scorer<Element>> scorers;

    public ElementListSupplier(Set<Scorer<Element>> scorers) {
        this.scorers = scorers;
    }

    @Override
    public List<Element> get() {

        List<Element> targetElements = new ArrayList<>();
        targetElements.addAll(getEmphasisedTargetElementsAndScores(scorers).keySet());
        targetElements.addAll(getSegmentedTargetElementsAndScores(scorers).keySet());
        targetElements.addAll(generateAndScoreRandomElements(scorers, 100).keySet());

        for (Element element : targetElements) {
            element.text(randomString());
        }

        Collections.shuffle(targetElements, new Random());
        return targetElements;
    }

    public static Map<Element, Integer>
    getSegmentedTargetElementsAndScores(Iterable<Scorer<Element>> elementScorers){
        String scoreLabel = SegmentationElementScorer.SCORE_LABEL;

        Map<Element, Integer> targetMap = null;

        Iterator<Scorer<Element>> it = elementScorers.iterator();

        while (targetMap == null && it.hasNext()) {
            Scorer<Element> nextScorer = it.next();
            if (nextScorer.getScoreLabel().equals(scoreLabel)) {
                targetMap = new HashMap<>();
                Map<Tag, Integer> tagScoresMap =
                        ((TagSegmentationScorer)
                                (
                                        (SegmentationElementScorer) nextScorer
                                ).getTagScorer()
                        ).getScoresMap();
                for (Map.Entry<Tag, Integer> entry :
                        tagScoresMap.entrySet()) {
                    targetMap.put(
                            new Element(
                                    entry.getKey(), "some string"
                            ),
                            entry.getValue()
                    );
                }
            }
        }

        if (targetMap == null)
            throw new IllegalArgumentException("Couldn't find any segmented elements");

        return targetMap;
    }

    public static Map<Element, Integer>
    getEmphasisedTargetElementsAndScores(Iterable<Scorer<Element>> elementScorers){
        String scoreLabel = EmphasisElementScorer.SCORE_LABEL;

        Map<Element, Integer> targetMap = null;

        Iterator<Scorer<Element>> it = elementScorers.iterator();

        while (targetMap == null && it.hasNext()) {
            Scorer<Element> nextScorer = it.next();
            if (nextScorer.getScoreLabel().equals(scoreLabel)) {
                targetMap = new HashMap<>();

                //tag only emphasis
                Map<Tag, Integer> tagScoresMap
                        = ((EmphasisElementScorer) nextScorer)
                        .getTagEmphasisScorer()
                        .getScoresMap();
                for (Map.Entry<Tag, Integer> entry :
                        tagScoresMap.entrySet()) {
                    targetMap.put(
                            new Element(
                                    entry.getKey(),
                                    "some string"
                            ),
                            entry.getValue()
                    );
                }
                tagScoresMap = null;

                //tag and attribute emphasis
                Map<TagAndAttribute, Integer> tagAndAttributeScoresMap
                        =((EmphasisElementScorer)nextScorer)
                        .getTagAndAttributeScorer()
                        .getScoresMap();
                for(Map.Entry<TagAndAttribute, Integer> entry : tagAndAttributeScoresMap.entrySet()){
                    Attributes attributes = new Attributes();
                    attributes.put(entry.getKey().getAttribute());
                    targetMap.put(
                            new Element(entry.getKey().getTag(), "some string", attributes),
                            entry.getValue()
                    );
                }
            }
        }
        if (targetMap == null)
            throw new IllegalArgumentException("Couldn't find any emphasised elements");
        return targetMap;
    }

    public static String randomString(){
        return RandomStringUtils.random(8);
    }

    public static Map<Element, Map<String, Integer>> generateAndScoreRandomElements(Set<Scorer<Element>> scorers, int numberToGenerate) {
        Map<Element, Map<String, Integer>> result = new HashMap<>();
        while(numberToGenerate-- > 0){
            Element element = new Element(Tag.valueOf(randomString()), randomString());
            Map<String, Integer> scores = new HashMap<>();
            for(Scorer<Element> scorer : scorers){
                scores.put(scorer.getScoreLabel(), scorer.score(element));
            }
            result.put(element, scores);
        }
        return result;
    }
}
