package com.grayben.riskExtractor.helpers;

import com.grayben.riskExtractor.htmlScorer.ScoredText;
import com.grayben.riskExtractor.htmlScorer.ScoredTextElement;
import com.grayben.riskExtractor.htmlScorer.partScorers.Scorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.elementScorers.EmphasisElementScorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.elementScorers.SegmentationElementScorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.tagScorers.MapScorer;
import org.apache.commons.io.IOUtils;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.parser.Tag;

import java.io.InputStream;
import java.util.*;

/**
 * Created by beng on 20/04/2016.
 */
public final class ScoredTextReverseEngineerer {

    public static <T, U extends Scorer<T>> U appropriateScorerFrom(Iterable<U> scorerSet, String scorerLabel){
        for(U scorer : scorerSet){
            if (scorer.getScoreLabel().equals(scorerLabel)){
                return scorer;
            }
        }
        throw new IllegalArgumentException("The specified Iterable did not contain a matching element");
    }

    public static <K, V> K keySuchThatValueIs(Map<K, V> map, V targetValue){
        if( ! map.containsValue(targetValue)){
            throw new IllegalArgumentException(map.toString() + " did not contain " + targetValue.toString());
        }

        for(Map.Entry<K, V> entry: map.entrySet()){
            if (entry.getValue().equals(targetValue)) return entry.getKey();
        }

        throw new IllegalArgumentException("Unexpected exception");
    }

    public static String htmlFrom(Node tree){
        StringBuilder sb = new StringBuilder();
        tree.html(sb);
        return sb.toString();
    }

    public static <T> boolean mutuallyExclusiveNonZeroScores(Collection<? extends Scorer<T>> scorers, T item){
        int numNonZeroScores = 0;
        for(Scorer<T> scorer : scorers){
            if (scorer.score(item) > 0) numNonZeroScores++;
        }
        return numNonZeroScores <= 1;
    }

    public static MapScorer<Tag> equivalentScorerFrom(EmphasisElementScorer emphasisElementScorer){
        return emphasisElementScorer.getTagEmphasisScorer();
    }

    public static MapScorer<Tag> equivalentScorerFrom(SegmentationElementScorer segmentationElementScorer){
        Scorer<Tag> tagScorer = segmentationElementScorer.getTagScorer();
        return (MapScorer<Tag>) tagScorer;
    }

    public static Set<MapScorer<Tag>> equivalentScorersFrom(Set<? extends Scorer<? extends Element>> elementScorers){
        Set<MapScorer<Tag>> newSet = new HashSet<>();
        for(Scorer<? extends Element> elementScorer: elementScorers){
            if(SegmentationElementScorer.class.isAssignableFrom(elementScorer.getClass())){
                newSet.add(equivalentScorerFrom((SegmentationElementScorer) elementScorer));
            } else if(EmphasisElementScorer.class.isAssignableFrom(elementScorer.getClass())){
                newSet.add(equivalentScorerFrom((EmphasisElementScorer) elementScorer));
            } else {
                throw new IllegalArgumentException(elementScorer.getClass().toString() + " was not a recognised subclass");
            }
        }
        return newSet;
    }

    //TODO: adapt to e.g. tags. Not elements: can't wrap text in an element!
    public static String htmlFromTagMapScorers(ScoredText scoredText, Set<MapScorer<Tag>> tagScorers){
        StringBuilder stringBuilder = new StringBuilder();

        for(ScoredTextElement scoredTextElement : scoredText.getList()){

            String htmlForElement = scoredTextElement.getTextElement();


            Map<String, Integer> scores = scoredTextElement.getScores();

            List<MapScorer<Tag>> scorerList = new ArrayList<>();
            List<Tag> wrapWith = new ArrayList<>();
            for(Map.Entry<String, Integer> labelScorePair : scores.entrySet()) {

                String label = labelScorePair.getKey();
                Integer score = labelScorePair.getValue();

                MapScorer<Tag> scorer = appropriateScorerFrom(tagScorers, label);

                scorerList.add(scorer);
                wrapWith.add(keySuchThatValueIs(scorer.getScoresMap(), score));
            }

            for(Tag tag : wrapWith){
                assert mutuallyExclusiveNonZeroScores(scorerList, tag);
                htmlForElement = new Element(tag, "").wrap(htmlForElement).html();
            }

            stringBuilder.append(htmlForElement);
        }
        return stringBuilder.toString();
    }

    public static String htmlFromElementScorers(ScoredText scoredText, Set<Scorer<Element>> elementScorers){
        return htmlFromTagMapScorers(scoredText, equivalentScorersFrom(elementScorers));
    }

    public static InputStream inputStreamFrom(String html){
        return IOUtils.toInputStream(html);
    }

    public static InputStream inputStreamFrom(Node tree){
        String html = htmlFrom(tree);
        return inputStreamFrom(html);
    }

    public static InputStream inputSteamFromTagMapScorers(ScoredText scoredText, Set<MapScorer<Tag>> elementScorers){
        String html = htmlFromTagMapScorers(scoredText, elementScorers);
        return inputStreamFrom(html);
    }

    public static InputStream inputStreamFromElementScorers(ScoredText scoredText, Set<Scorer<Element>> elementScorers){
        return inputSteamFromTagMapScorers(scoredText, equivalentScorersFrom(elementScorers));
    }

}
