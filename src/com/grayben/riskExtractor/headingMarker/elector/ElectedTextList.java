package com.grayben.riskExtractor.headingMarker.elector;

import java.util.List;

import com.grayben.riskExtractor.headingMarker.TextCandidate;
import com.grayben.riskExtractor.headingMarker.nominator.NomineesRetrievable;

public abstract class ElectedTextList
	implements NomineesRetrievable,
		ElecteesRetrievable {
	
	List<TextCandidate> nominees;
	List<TextCandidate> electees;

	@Override
	public List<TextCandidate> getNominees() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TextCandidate> getElectees() {
		// TODO Auto-generated method stub
		return null;
	}

}
