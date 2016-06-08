package com.grayben.riskExtractor.helpers;

import com.grayben.riskExtractor.htmlScorer.ScoredText;
import com.grayben.riskExtractor.htmlScorer.ScoredTextElement;
import com.grayben.riskExtractor.htmlScorer.partScorers.Scorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.tagScorers.MapScorer;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

import java.util.*;

/**
 * Created by beng on 23/04/2016.
 */
public class ScoredTextGenerator {

    public static Pair<ScoredText, String> randomPairedScoredTextAndHtml(Random random, Set<Scorer<Element>> elementScorers) {
        Set<MapScorer<Tag>> tagScorers = ScoredTextReverseEngineerer.equivalentScorersFrom(elementScorers);
        List<MapScorer<Tag>> scorerList = new ArrayList<>();
        scorerList.addAll(tagScorers);

        int numScorers = scorerList.size();

        ScoredText scoredText = new ScoredText();
        StringBuilder htmlBuilder = new StringBuilder();
        for(int i = 0; i < random.nextInt(); i++){
            List<Tag> tagsToApply = new ArrayList<>();

            int maxNumScorersToUse = random.nextInt() % (numScorers + 1);
            List<MapScorer<Tag>> scorersCopy = new ArrayList<>(scorerList);

            for(int j = 0; j < maxNumScorersToUse; j++){
                if(random.nextInt() % 6 == 0){
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

            htmlBuilder.append(element.html());

            ScoredTextElement scoredTextElement;
            Map<String, Integer> elementScores = new HashMap<>();
            for(Scorer<Element> scorer: elementScorers){
                elementScores.put(scorer.getScoreLabel(), scorer.score(element));
            }

            scoredTextElement = new ScoredTextElement(text, elementScores);
            scoredText.add(scoredTextElement);
        }
        return new ImmutablePair<>(scoredText, htmlBuilder.toString());
    }

    public static int randomInt(Random random, int min, int max){
        return min + (int)(random.nextFloat() * ((max - min) + 1));
    }

    public static ScoredText randomScoredTextWithDefaultScorers(){
        Set<ElementScorerSetFunction.Content> contents = new HashSet<>();
        contents.add(ElementScorerSetFunction.Content.EMPHASIS_ELEMENT_SCORER);
        contents.add(ElementScorerSetFunction.Content.SEGMENTATION_ELEMENT_SCORER);
        Set<Scorer<Element>> elementScorerSet = new ElementScorerSetFunction().apply(contents);
        return ScoredTextGenerator.randomScoredText(new Random(42L), elementScorerSet);
    }

    public static ScoredText randomScoredText(Random random, Set<Scorer<Element>> elementScorers){
        Set<MapScorer<Tag>> tagScorers = ScoredTextReverseEngineerer.equivalentScorersFrom(elementScorers);
        List<MapScorer<Tag>> scorerList = new ArrayList<>();
        scorerList.addAll(tagScorers);

        ScoredText scoredText = new ScoredText();
        for(int i = 0; i < randomInt(random, 1, 100); i++){
            Map<String, Integer> textScores = new HashMap<>();

            for(MapScorer<Tag> tagMapScorer: scorerList){
                Map<Tag, Integer> scoreMap = tagMapScorer.getScoresMap();

                String label = tagMapScorer.getScoreLabel();
                Integer score = Scorer.DEFAULT_SCORE;

                if(randomInt(random, 1, 100) % 2 > 0){
                    List<Integer> scoreList = new ArrayList<>();
                    scoreList.addAll(scoreMap.values());
                    score = scoreList.get(randomInt(random, 1, Integer.MAX_VALUE - 1) % scoreList.size());
                }
                textScores.put(label, score);

            }
            String text = ((Integer)randomInt(random, 1, Integer.MAX_VALUE - 1)).toString();

            scoredText.add(new ScoredTextElement(text, textScores));
        }
        return scoredText;
    }
}
