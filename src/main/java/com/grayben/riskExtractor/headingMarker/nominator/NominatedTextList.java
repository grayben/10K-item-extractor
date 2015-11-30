package com.grayben.riskExtractor.headingMarker.nominator;

import java.util.List;

import com.grayben.riskExtractor.headingMarker.TextCandidate;
import com.grayben.riskExtractor.headingMarker.TextCandidates;

public class NominatedTextList implements NomineesRetrievable {

	List<String> textList;

	public List<String> getTextList() {
		return textList;
	}

	public void setTextList(List<String> textList) {
		this.textList = textList;
	}

	TextCandidates nominees;

	public void setNominees(TextCandidates nominees) {
		this.nominees = nominees;
	}

	@Override
	public TextCandidates getNominees() {
		return this.nominees;
	}

    public NominatedTextList(List<String> textList, TextCandidates nominees) {
        this.textList = textList;
        this.nominees = nominees;
    }
}
