package com.grayben.riskExtractor.headingMarker.elector;

import com.grayben.riskExtractor.headingMarker.nominator.NominatedText;
import org.apache.commons.collections4.list.SetUniqueList;

import java.util.ArrayList;
import java.util.List;

/**
 * NominatedText which has also had nominees elected.
 */
public class ElectedText
        extends
        NominatedText
        implements
	    ElecteesRetrievable {

    /**
     * The list of indices to the list of Strings corresponding to electees.
     */
    SetUniqueList<Integer> electees;

    /**
     * Constructs ElectedText 'from scratch'.
     * <p>
     * @param textList the list of Strings corresponding to the text
     * @param nominees the indices into textList corresponding to nominated entries
     * @param electees the indices into textList corresponding to elected entries
     * @apiNote electees must be a subset of nominees
     */
    public ElectedText(List<String> textList, SetUniqueList<Integer> nominees, SetUniqueList<Integer> electees){
        super(textList, nominees);
        if (electees == null) {
            throw new NullPointerException("Attempted to pass illegal null argument");
        }
        if ( ! nominees.containsAll(electees))
            throw new IllegalArgumentException(
                    "The electees argument was not a subset " +
                            "of the nominees argument"
            );
        this.electees = electees;
    }

    /**
     * Construct ElectedText incrementally from an existing {@link NominatedText}
     * @param nominatedText the nominatedText upon which to construct
     * @param electees the list of indices into the text list corresponding to elected entries
     */
    public ElectedText(NominatedText nominatedText, SetUniqueList<Integer> electees){
        this(
                nominatedText.getStringList(),
                nominatedText.getNomineeIndices(),
                electees
        );
    }

    /**
     * Constructs as a copy of the input.
     * @param electedText the ElectedText to copy
     */
    public ElectedText(ElectedText electedText){
        this(electedText, electedText.getElecteeIndices());
    }

    @Override
    public SetUniqueList<Integer> getElecteeIndices() {

        SetUniqueList<Integer> newSetUniqueList
                = SetUniqueList.setUniqueList(new ArrayList<>());

        newSetUniqueList.addAll(this.electees);

        return newSetUniqueList;
    }

}
