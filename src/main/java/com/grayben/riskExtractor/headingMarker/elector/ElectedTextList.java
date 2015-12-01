package com.grayben.riskExtractor.headingMarker.elector;

import java.util.List;

import com.grayben.riskExtractor.headingMarker.TextCandidate;
import com.grayben.riskExtractor.headingMarker.TextCandidates;
import com.grayben.riskExtractor.headingMarker.nominator.NominatedTextList;
import com.grayben.riskExtractor.headingMarker.nominator.NomineesRetrievable;

public class ElectedTextList

        extends
        NominatedTextList

        implements
	    ElecteesRetrievable {

	List<String> textList;

    public List<String> getTextList() {
        return textList;
    }

    public void setTextList(List<String> textList) {
        this.textList = textList;
    }

    TextCandidates electees;

    @Override
    public TextCandidates getElectees() {
        // TODO Auto-generated method stub
        return this.electees;
    }

    public void setElectees(TextCandidates electees) {
        this.electees = electees;
    }

	public ElectedTextList(List<String> textList, TextCandidates nominees, TextCandidates electees){
		super(textList, nominees);
		this.electees = electees;
	}

}
