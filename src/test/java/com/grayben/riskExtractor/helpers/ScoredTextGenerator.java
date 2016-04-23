package com.grayben.riskExtractor.helpers;

import com.grayben.riskExtractor.htmlScorer.ScoredText;
import com.grayben.riskExtractor.htmlScorer.ScoredTextElement;
import com.grayben.riskExtractor.htmlScorer.partScorers.Scorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.tagScorers.MapScorer;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

import java.util.*;

/**
 * Created by beng on 23/04/2016.
 */
public class ScoredTextGenerator {
    public static ScoredText randomScoredText(Random random, Set<Scorer<Element>> elementScorers){
        Set<MapScorer<Tag>> tagScorers = equivalentScorersFrom(elementScorers);
        List<MapScorer<Tag>> scorerList = new ArrayList<>();
        scorerList.addAll(tagScorers);

        int numScorers = scorerList.size();

        ScoredText scoredText = new ScoredText();
        for(int i = 0; i < random.nextInt(); i++){
            List<Tag> tagsToApply = new ArrayList<>();

            int maxNumScorersToUse = random.nextInt() % (numScorers + 1);
            List<MapScorer<Tag>> scorersCopy = new ArrayList<>(scorerList);

            for(int j = 0; j < maxNumScorersToUse; j++){
                if(random.nextInt() % 2 > 0){
                    int scorersCopySize = scorersCopy.size();
                    MapScorer<Tag> selectedScorer = scorersCopy.remove(random.nextInt() % scorersCopySize);
                    List<Tag> tagList = new ArrayList<>();
                    tagList.addAll(selectedScorer.getScoresMap().keySet());
                    tagsToApply.add(tagList.get(random.nextInt() % tagList.size()));
                } else {
                    tagsToApply.add(Tag.valueOf("foobarbaz"));
                }

            }
            Iterator<Tag> tagIterator = tagsToApply.iterator();
            String text = ((Integer)random.nextInt()).toString();
            Element element = new Element(tagIterator.next(), "").text(text);
            while(tagIterator.hasNext()){
                Element parent = new Element(tagIterator.next(), "");
                parent.appendChild(element);
                element = parent;
            }

            ScoredTextElement scoredTextElement;
            Map<String, Integer> elementScores = new HashMap<>();
            for(Scorer<Element> scorer: elementScorers){
                elementScores.put(scorer.getScoreLabel(), scorer.score(element));
            }

            scoredTextElement = new ScoredTextElement(text, elementScores);
            scoredText.add(scoredTextElement);
        }
        return scoredText;
    }
}
