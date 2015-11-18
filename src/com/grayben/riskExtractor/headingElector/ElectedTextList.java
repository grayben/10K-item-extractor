package com.grayben.riskExtractor.headingElector;

import java.util.List;

import com.grayben.riskExtractor.ListOfTextCandidates;
import com.grayben.riskExtractor.Text;
import com.grayben.riskExtractor.headingNominator.NomineeRetrievable;

public abstract class ElectedTextList
	extends Text
	implements NomineeRetrievable,
		ElecteeRetrievable {
	
	ListOfTextCandidates nominees;
	ListOfTextCandidates electees;

	@Override
	public List<Integer> getNomineeIndexes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Integer> getElecteeIndexes() {
		// TODO Auto-generated method stub
		return null;
	}

}
