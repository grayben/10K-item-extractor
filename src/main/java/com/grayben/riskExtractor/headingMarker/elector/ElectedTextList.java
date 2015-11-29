package com.grayben.riskExtractor.headingMarker.elector;

import java.util.List;

import com.grayben.riskExtractor.headingMarker.TextCandidate;
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

	List<TextCandidate> nominees;

    @Override
    public List<TextCandidate> getNominees() {
        // TODO Auto-generated method stub
        return this.nominees;
    }

    public void setNominees(List<TextCandidate> nominees) {
        this.nominees = nominees;
    }


    List<TextCandidate> electees;

    @Override
    public List<TextCandidate> getElectees() {
        // TODO Auto-generated method stub
        return this.electees;
    }

    public void setElectees(List<TextCandidate> electees) {
        this.electees = electees;
    }

	public ElectedTextList(List<String> textList, List<TextCandidate> nominees, List<TextCandidate> electees){
		super();
		this.textList = textList;
		this.nominees = nominees;
		this.electees = electees;
	}

}
