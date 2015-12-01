package com.grayben.riskExtractor.headingMarker.elector;

import java.util.List;

import com.grayben.riskExtractor.headingMarker.TextCandidates;
import com.grayben.riskExtractor.headingMarker.nominator.NominatedText;

public class ElectedText

        extends
        NominatedText

        implements
	    ElecteesRetrievable {

	List<String> textList;

    public ElectedText(List<String> textList, List<Integer> nominees, TextCandidates electees){
        super(textList, nominees);
        this.electees = electees;
    }

    public List<String> getText() {
        return textList;
    }

    public void setText(List<String> textList) {
        this.textList = textList;
    }

    @Override
    public List<Integer> getElectees() {
        // TODO Auto-generated method stub
        return this.electees;
    }

}
