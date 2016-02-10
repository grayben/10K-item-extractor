package com.grayben.riskExtractor.headingMarker.elector;

import com.grayben.riskExtractor.headingMarker.nominator.NominatedText;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * NominatedText which has also had nominees elected.
 */
public class ElectedText
        extends
        NominatedText
        implements
	    ElecteesRetrievable<String> {

    /**
     * The list of indices to the list of Strings corresponding to electeeIndices.
     */
    Set<Integer> electeeIndices;

    /**
     * Constructs ElectedText 'from scratch'.
     * <p>
     * @param textList the list of Strings corresponding to the text
     * @param nominees the indices into textList corresponding to nominated entries
     * @param electeeIndices the indices into textList corresponding to elected entries
     * @apiNote electeeIndices must be a subset of nominees
     */
    public ElectedText(List<String> textList, Set<Integer> nominees, Set<Integer> electeeIndices){
        super(textList, nominees);
        if (electeeIndices == null) {
            throw new NullPointerException("Attempted to pass illegal null argument");
        }
        if ( ! nominees.containsAll(electeeIndices))
            throw new IllegalArgumentException(
                    "The electeeIndices argument was not a subset " +
                            "of the nominees argument"
            );
        this.electeeIndices = electeeIndices;
    }

    /**
     * Construct ElectedText incrementally from an existing {@link NominatedText}
     * @param nominatedText the nominatedText upon which to construct
     * @param electeeIndices the list of indices into the text list corresponding to elected entries
     */
    public ElectedText(NominatedText nominatedText, Set<Integer> electeeIndices){
        this(
                nominatedText.getStringList(),
                nominatedText.getNomineeIndices(),
                electeeIndices
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
    public Set<Integer> getElecteeIndices() {

        Set<Integer> indices = Collections.emptySet();
        indices.addAll(this.electeeIndices);
        return indices;
    }
}
