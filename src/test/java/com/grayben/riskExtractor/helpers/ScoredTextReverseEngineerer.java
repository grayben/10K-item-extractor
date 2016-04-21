package com.grayben.riskExtractor.helpers;

import com.grayben.riskExtractor.htmlScorer.ScoredText;
import com.grayben.riskExtractor.htmlScorer.partScorers.Scorer;
import org.apache.commons.io.IOUtils;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import java.io.InputStream;
import java.util.List;
import java.util.Set;

/**
 * Created by beng on 20/04/2016.
 */
public final class ScoredTextReverseEngineerer {

    public static String htmlFrom(Node tree){
        StringBuilder sb = new StringBuilder();
        tree.html(sb);
        return sb.toString();
    }

    public static String htmlFrom(ScoredText scoredText, Set<Scorer<Element>> elementScorers){
        List<String> textElementsSurroundedByAppropriateTags = null;
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
