package com.grayben.riskExtractor.helpers;

import com.grayben.riskExtractor.htmlScorer.ScoredText;
import com.grayben.riskExtractor.htmlScorer.ScoredTextElement;
import com.grayben.riskExtractor.htmlScorer.partScorers.Scorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.tagScorers.MapScorer;
import org.apache.commons.lang3.tuple.Pair;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

import java.util.*;

/**
 * Created by beng on 23/04/2016.
 */
public class ScoredTextGenerator {

    public static Pair<ScoredText, String> randomPairedScoredTextAndHtml(Random random, Set<Scorer<Element>> elementScorers) {
        throw new UnsupportedOperationException("Not implemented");
    }

    public static ScoredText randomScoredText(Random random, Set<Scorer<Element>> elementScorers){
        Set<MapScorer<Tag>> tagScorers = ScoredTextReverseEngineerer.equivalentScorersFrom(elementScorers);
        List<MapScorer<Tag>> scorerList = new ArrayList<>();
        scorerList.addAll(tagScorers);

        ScoredText scoredText = new ScoredText();

        for(int i = 0; i < random.nextInt(); i++){
            Map<String, Integer> textScores = new HashMap<>();

            for(MapScorer<Tag> tagMapScorer: scorerList){
                Map<Tag, Integer> scoreMap = tagMapScorer.getScoresMap();

                String label = tagMapScorer.getScoreLabel();
                Integer score = Scorer.DEFAULT_SCORE;

                if(random.nextInt() % 2 > 0){
                    List<Integer> scoreList = new ArrayList<>();
                    scoreList.addAll(scoreMap.values());
                    score = scoreList.get(random.nextInt() % scoreList.size());
                }
                textScores.put(label, score);

            }
            String text = ((Integer)random.nextInt()).toString();

            scoredText.add(new ScoredTextElement(text, textScores);
        }
        return scoredText;
    }
}
