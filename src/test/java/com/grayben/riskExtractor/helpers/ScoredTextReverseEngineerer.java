package com.grayben.riskExtractor.helpers;

import com.grayben.riskExtractor.htmlScorer.ScoredText;
import com.grayben.riskExtractor.htmlScorer.ScoredTextElement;
import com.grayben.riskExtractor.htmlScorer.partScorers.Scorer;
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

    public static <K, Integer> List<K> keysSuchThatValuesSumTo(Map<K, Integer> map, Integer toSumTo){
        throw new UnsupportedOperationException("Not implemented");
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

    //TODO: adapt to e.g. tags. Not elements: can't wrap text in an element!
    public static String htmlFrom(ScoredText scoredText, Set<MapScorer<Tag>> tagScorers){
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
                wrapWith.addAll(keysSuchThatValuesSumTo(scorer.getScoresMap(), score));
            }

            for(Tag tag : wrapWith){
                assert mutuallyExclusiveNonZeroScores(scorerList, tag);
                htmlForElement = new Element(tag, "").wrap(htmlForElement).html();
            }

            stringBuilder.append(htmlForElement);
        }
        return stringBuilder.toString();
    }

    public static InputStream inputStreamFrom(String html){
        return IOUtils.toInputStream(html);
    }

    public static InputStream inputStreamFrom(Node tree){
        String html = htmlFrom(tree);
        return inputStreamFrom(html);
    }

    public static InputStream inputSteamFrom(ScoredText scoredText, Set<MapScorer<Tag>> elementScorers){
        String html = htmlFrom(scoredText, elementScorers);
        return inputStreamFrom(html);
    }

}
