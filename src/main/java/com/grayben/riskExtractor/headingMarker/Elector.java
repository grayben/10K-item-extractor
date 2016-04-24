package com.grayben.riskExtractor.headingMarker;

import com.grayben.riskExtractor.htmlScorer.ScoredText;
import com.grayben.riskExtractor.htmlScorer.ScoredTextElement;

import java.util.function.Function;

/**
 * Created by beng on 24/04/2016.
 */
public class Elector {
    private final Function<ScoredTextElement, Boolean> isElectee;

    public Elector(Function<ScoredTextElement, Boolean> isElectee) {
        this.isElectee = isElectee;
    }

    public ElectedText elect(NominatedText nominatedText){
        throw new UnsupportedOperationException("Not implemented");
    }

    public ElectedText electedText(Nominator nominator, ScoredText scoredText){
        NominatedText nominatedText = nominator.nominate(scoredText);
        return elect(nominatedText);
    }
}
