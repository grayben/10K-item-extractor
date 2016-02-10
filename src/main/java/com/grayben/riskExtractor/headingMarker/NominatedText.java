package com.grayben.riskExtractor.headingMarker;

import org.apache.commons.collections4.list.SetUniqueList;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link UnmodifiableText} for which zero or more entries have been deemed 'nominees.'
 * <p>
 * Created by Ben Gray, 2015.
 */
public class NominatedText
		extends UnmodifiableText
		implements NomineesRetrievable<String> {

    /**
     * The indices into {@link #stringList} identifying nominees.
     */
    private SetUniqueList<Integer> nomineeIndices;

    /**
     * Construct 'from scratch'.
     * @param stringList the list of strings containing the text entries
     * @param nomineeIndices the indices identifying nominees
     */
    public NominatedText(List<String> stringList, SetUniqueList<Integer> nomineeIndices) {
        super(stringList);
        if (nomineeIndices == null) {
            throw new NullPointerException("Attempted to pass null argument");
        }
        this.nomineeIndices = nomineeIndices;
    }

    /**
     * Construct incrementally.
     * @param unmodifiableText the text upon which to build
     * @param nomineeIndices the indices identifying nominees
     */
    public NominatedText(
            UnmodifiableText unmodifiableText,
            SetUniqueList<Integer> nomineeIndices){
        super(unmodifiableText);
        if (nomineeIndices == null) {
            throw new NullPointerException("Attempted to pass null argument");
        }
        this.nomineeIndices = nomineeIndices;
    }

    /**
     * Construct a copy.
     * @param nominatedText the object to copy
     */
    public NominatedText(NominatedText nominatedText){
        this(nominatedText, nominatedText.getNomineeIndices());
    }

    @Override
    public SetUniqueList<Integer> getNomineeIndices() {
        SetUniqueList<Integer> newList = SetUniqueList.setUniqueList(
                new ArrayList<>()
        );
        newList.addAll(this.nomineeIndices);
        return newList;
    }
}
