package com.grayben.riskExtractor.headingMarker.nominator;

import java.util.List;

import com.grayben.riskExtractor.headingMarker.UnmodifiableText;

public class NominatedText
		extends UnmodifiableText
		implements NomineesRetrievable {

    private List<Integer> nominees;

    public NominatedText(List<String> stringList, List<Integer> nominees) {
        super(stringList);
        this.nominees = nominees;
    }

    public NominatedText(
            UnmodifiableText unmodifiableText,
            List<Integer> nominees){
        super(unmodifiableText);
        this.nominees = nominees;
    }

    public NominatedText(NominatedText nominatedText){
        this(nominatedText, nominatedText.getNominees());
    }

    @Override
    public List<Integer> getNominees() {
        return nominees;
    }
}
