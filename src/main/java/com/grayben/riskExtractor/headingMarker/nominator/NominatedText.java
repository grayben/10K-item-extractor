package com.grayben.riskExtractor.headingMarker.nominator;

import java.util.List;

import com.grayben.riskExtractor.headingMarker.UnmodifiableText;

public class NominatedText
		extends UnmodifiableText
		implements NomineesRetrievable {

    private int[] nominees;

    public NominatedText(List<String> stringList, int[] nominees) {
        super(stringList);
        this.nominees = nominees;
    }

    @Override
    public List<Integer> getNominees() {
        return nominees;
    }
}
