package com.grayben.riskExtractor.headingMarker.elector;

import java.util.List;

import com.grayben.riskExtractor.headingMarker.nominator.NominatedText;

public class ElectedText

        extends
        NominatedText

        implements
	    ElecteesRetrievable {


    List<Integer> electees;

    public ElectedText(List<String> textList, List<Integer> nominees, List<Integer> electees){
        super(textList, nominees);
        if (electees == null) {
            throw new NullPointerException("Attempted to pass illegal null argument");
        }
        this.electees = electees;
    }

    public ElectedText(NominatedText nominatedText, List<Integer> electees){
        super(nominatedText);
        if (electees == null) {
            throw new NullPointerException("Attempted to pass illegal null argument");
        }
        this.electees = electees;
    }

    public ElectedText(ElectedText electedText){
        this(electedText, electedText.getElectees());
    }

    @Override
    public List<Integer> getElectees() {
        // TODO Auto-generated method stub
        return this.electees;
    }

}
