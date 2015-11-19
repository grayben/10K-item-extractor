package com.grayben.riskExtractor.extractor;

import java.util.List;

import com.grayben.riskExtractor.TextCandidate;

public interface SectionExtractable {
	List<String> subselection(TextCandidate electee);
}
