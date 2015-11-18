package com.grayben.riskExtractor.headingElector;

import com.grayben.riskExtractor.ListOfTextCandidates;
import com.grayben.riskExtractor.Text;
import com.grayben.riskExtractor.headingNominator.NomineesRetrievable;

public abstract class ElectedTextList
	extends Text
	implements NomineesRetrievable,
		ElecteesRetrievable {
	
	ListOfTextCandidates nominees;
	ListOfTextCandidates electees;

	@Override
	public ListOfTextCandidates getNominees() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListOfTextCandidates getElectees() {
		// TODO Auto-generated method stub
		return null;
	}

}
