package com.grayben.riskExtractor.headingMarker.nominator;

import com.grayben.riskExtractor.headingMarker.UnmodifiableText;

import java.util.Collections;
import java.util.List;
import java.util.SortedSet;

public class NominatedText
		extends UnmodifiableText
		implements NomineesRetrievable {

    //TODO: make this a set: should not have repetitions
    private SortedSet<Integer> nominees;

    public NominatedText(List<String> stringList, SortedSet<Integer> nominees) {
        super(stringList);
        if (nominees == null) {
            throw new NullPointerException("Attempted to pass null argument");
        }
        this.nominees = nominees;
    }

    public NominatedText(
            UnmodifiableText unmodifiableText,
            SortedSet<Integer> nominees){
        super(unmodifiableText);
        if (nominees == null) {
            throw new NullPointerException("Attempted to pass null argument");
        }
        this.nominees = nominees;
    }

    public NominatedText(NominatedText nominatedText){
        this(nominatedText, nominatedText.getNominees());
    }

    @Override
    public SortedSet<Integer> getNominees() {
        return Collections.unmodifiableSortedSet(this.nominees);
    }
}
