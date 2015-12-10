package com.grayben.riskExtractor.headingMarker.elector;

import com.grayben.riskExtractor.headingMarker.nominator.NominatedText;

import java.util.Collections;
import java.util.List;
import java.util.SortedSet;

public class ElectedText

        extends
        NominatedText

        implements
	    ElecteesRetrievable {

    //TODO: make this a set: should not have repetitions
    SortedSet<Integer> electees;

    public ElectedText(List<String> textList, SortedSet<Integer> nominees, SortedSet<Integer> electees){
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

    public ElectedText(NominatedText nominatedText, SortedSet<Integer> electees){
        this(
                nominatedText.getStringList(),
                nominatedText.getNominees(),
                electees
        );
    }

    public ElectedText(ElectedText electedText){
        this(electedText, electedText.getElectees());
    }

    @Override
    public SortedSet<Integer> getElectees() {
        return Collections.unmodifiableSortedSet(this.electees);
    }

}
