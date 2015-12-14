package com.grayben.riskExtractor.headingMarker.elector;

import com.grayben.riskExtractor.headingMarker.nominator.NominatedText;
import org.apache.commons.collections4.list.SetUniqueList;

import java.util.ArrayList;
import java.util.List;

public class ElectedText

        extends
        NominatedText

        implements
	    ElecteesRetrievable {
    
    SetUniqueList<Integer> electees;

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

    public ElectedText(NominatedText nominatedText, SetUniqueList<Integer> electees){
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
    public SetUniqueList<Integer> getElectees() {

        SetUniqueList<Integer> newSetUniqueList
                = SetUniqueList.setUniqueList(new ArrayList<>());

        newSetUniqueList.addAll(this.electees);

        return newSetUniqueList;
    }

}
