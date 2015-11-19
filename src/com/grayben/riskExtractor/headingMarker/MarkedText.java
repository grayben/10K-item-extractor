package com.grayben.riskExtractor.headingMarker;

import java.util.List;

import com.grayben.riskExtractor.TextCandidate;
import com.grayben.riskExtractor.extractor.SectionExtractable;
import com.grayben.riskExtractor.headingMarker.elector.ElectedTextList;

public class MarkedText implements SectionExtractable {
	
	ElectedTextList text;

	@Override
	public List<String> subselection(TextCandidate electee) {
		return null;
	}

}
