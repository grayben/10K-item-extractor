package com.grayben.riskExtractor.helpers;

import com.grayben.riskExtractor.htmlScorer.ScoredText;
import com.grayben.riskExtractor.htmlScorer.ScoredTextElement;
import com.grayben.riskExtractor.htmlScorer.partScorers.Scorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.tagScorers.MapScorer;
import org.apache.commons.io.IOUtils;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    public static <K, Integer> List<K> keysSuchThatValuesSumTo(Map<K, Integer> map, Integer toSumTo){
        throw new UnsupportedOperationException("Not implemented");
    }

    public static String htmlFrom(Node tree){
        StringBuilder sb = new StringBuilder();
        tree.html(sb);
        return sb.toString();
    }

    //TODO: adapt to e.g. tags. Not elements: can't wrap text in an element!
    public static String htmlFrom(ScoredText scoredText, Set<MapScorer<Element>> elementScorers){
        List<String> textElementsSurroundedByAppropriateTags = new ArrayList<>();
        for(ScoredTextElement scoredTextElement : scoredText.getList()){
            String textElement = scoredTextElement.getTextElement();
            Map<String, Integer> scores = scoredTextElement.getScores();
            for(Map.Entry<String, Integer> labelScorePair : scores.entrySet()){
                String label = labelScorePair.getKey();
                Integer score = labelScorePair.getValue();
                MapScorer<Element> scorer = appropriateScorerFrom(elementScorers, label);
                List<Element> wrapWith = keysSuchThatValuesSumTo(scorer.getScoresMap(), score);
            }
        }
        String taggedTextSurroundedByFrame = null;
        return taggedTextSurroundedByFrame;
    }

    public static InputStream inputStreamFrom(String html){
        return IOUtils.toInputStream(html);
    }

    public static InputStream inputStreamFrom(Node tree){
        String html = htmlFrom(tree);
        return inputStreamFrom(html);
    }

    public static InputStream inputSteamFrom(ScoredText scoredText, Set<Scorer<Element>> elementScorers){
        String html = htmlFrom(scoredText, elementScorers);
        return inputStreamFrom(html);
    }

}
