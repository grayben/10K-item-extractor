package com.grayben.riskExtractor.headingMarker;

import com.grayben.riskExtractor.extractor.SectionExtractable;
import com.grayben.riskExtractor.headingMarker.elector.ElectedTextList;

public class MarkedText implements SectionExtractable {
	
	ElectedTextList text;

	@Override
	public String subselection(TextCandidate electee) {
		return null;
	}

}
