package com.grayben.riskExtractor.headingMarker.elector;

import java.util.List;

import com.grayben.riskExtractor.headingMarker.TextCandidate;
import com.grayben.riskExtractor.headingMarker.TextCandidates;
import com.grayben.riskExtractor.headingMarker.nominator.NomineesRetrievable;

public class ElectedTextList implements
	NomineesRetrievable,
	ElecteesRetrievable {

	List<String> textList;

    public List<String> getTextList() {
        return textList;
    }

    public void setTextList(List<String> textList) {
        this.textList = textList;
    }

	TextCandidates nominees;

    @Override
    public TextCandidates getNominees() {
        // TODO Auto-generated method stub
        return this.nominees;
    }

    public void setNominees(TextCandidates nominees) {
        this.nominees = nominees;
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
		super();
		this.textList = textList;
		this.nominees = nominees;
		this.electees = electees;
	}

}
