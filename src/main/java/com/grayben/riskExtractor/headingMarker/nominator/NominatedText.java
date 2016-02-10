package com.grayben.riskExtractor.headingMarker.nominator;

import com.grayben.riskExtractor.headingMarker.UnmodifiableText;
import org.apache.commons.collections4.list.SetUniqueList;

import java.util.ArrayList;
import java.util.List;

public class NominatedText
		extends UnmodifiableText
		implements NomineesRetrievable<String> {

    private SetUniqueList<Integer> nominees;

    public NominatedText(List<String> stringList, SetUniqueList<Integer> nominees) {
        super(stringList);
        if (nominees == null) {
            throw new NullPointerException("Attempted to pass null argument");
        }
        this.nominees = nominees;
    }

    public NominatedText(
            UnmodifiableText unmodifiableText,
            SetUniqueList<Integer> nominees){
        super(unmodifiableText);
        if (nominees == null) {
            throw new NullPointerException("Attempted to pass null argument");
        }
        this.nominees = nominees;
    }

    public NominatedText(NominatedText nominatedText){
        this(nominatedText, nominatedText.getNomineeIndices());
    }

    @Override
    public SetUniqueList<Integer> getNomineeIndices() {
        SetUniqueList newList = SetUniqueList.setUniqueList(
                new ArrayList<>()
        );
        newList.addAll(this.nominees);
        return newList;
    }
}
