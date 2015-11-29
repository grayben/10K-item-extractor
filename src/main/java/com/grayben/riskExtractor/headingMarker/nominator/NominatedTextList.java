package com.grayben.riskExtractor.headingMarker.nominator;

import java.util.List;

import com.grayben.riskExtractor.headingMarker.TextCandidate;

public class NominatedTextList implements NomineesRetrievable {

	List<String> textList;

	public List<String> getTextList() {
		return textList;
	}

	public void setTextList(List<String> textList) {
		this.textList = textList;
	}

	List<TextCandidate> nominees;

	public void setNominees(List<TextCandidate> nominees) {
		this.nominees = nominees;
	}

	@Override
	public List<TextCandidate> getNominees() {
		return this.nominees;
	}

    public NominatedTextList(List<String> textList, List<TextCandidate> nominees) {
        this.textList = textList;
        this.nominees = nominees;
    }
}
