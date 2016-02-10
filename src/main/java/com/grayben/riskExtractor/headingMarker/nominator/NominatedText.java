package com.grayben.riskExtractor.headingMarker.nominator;

import com.grayben.riskExtractor.headingMarker.UnmodifiableText;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class NominatedText
		extends UnmodifiableText
		implements NomineesRetrievable<String> {

    private Set<Integer> nomineeIndices;

    public NominatedText(List<String> stringList, Set<Integer> nomineeIndices) {
        super(stringList);
        if (nomineeIndices == null) {
            throw new NullPointerException("Attempted to pass null argument");
        }
        this.nomineeIndices = nomineeIndices;
    }

    public NominatedText(
            UnmodifiableText unmodifiableText,
            Set<Integer> nomineeIndices){
        super(unmodifiableText);
        if (nomineeIndices == null) {
            throw new NullPointerException("Attempted to pass null argument");
        }
        this.nomineeIndices = nomineeIndices;
    }

    public NominatedText(NominatedText nominatedText){
        this(nominatedText, nominatedText.getNomineeIndices());
    }

    @Override
    public Set<Integer> getNomineeIndices() {
        Set<Integer> indices = Collections.emptySet();
        indices.addAll(this.nomineeIndices);
        return indices;
    }
}
