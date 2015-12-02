package com.grayben.riskExtractor.headingMarker.nominator;

import java.util.Collections;
import java.util.List;

import com.grayben.riskExtractor.headingMarker.UnmodifiableText;

public class NominatedText
		extends UnmodifiableText
		implements NomineesRetrievable {

    private List<Integer> nominees;

    public NominatedText(List<String> stringList, List<Integer> nominees) {
        super(stringList);
        if (nominees == null) {
            throw new NullPointerException("Attempted to pass null argument");
        }
        this.nominees = nominees;
    }

    public NominatedText(
            UnmodifiableText unmodifiableText,
            List<Integer> nominees){
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
    public List<Integer> getNominees() {
        return Collections.unmodifiableList(this.nominees);
    }
}
