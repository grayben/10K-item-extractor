package com.grayben.riskExtractor.extractor;

import java.util.List;

import com.grayben.riskExtractor.headingMarker.TextCandidate;

public interface SectionExtractable {
	List<String> subselection(TextCandidate electee);
}
