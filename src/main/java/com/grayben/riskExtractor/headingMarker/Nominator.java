package com.grayben.riskExtractor.headingMarker;

import com.grayben.riskExtractor.htmlScorer.ScoredText;
import com.grayben.riskExtractor.htmlScorer.ScoredTextElement;

import java.util.function.Function;

/**
 * Created by beng on 24/04/2016.
 */
public class Nominator {
    private final Function<ScoredTextElement, Boolean> isNominee;

    public Nominator(Function<ScoredTextElement, Boolean> isNominee) {
        this.isNominee = isNominee;
    }

    public NominatedText nominate(ScoredText scoredText){
        throw new UnsupportedOperationException("Not implemented");
    }
}
