package com.grayben.riskExtractor.extractor;

import java.util.List;

public interface TextSubselectable {
	List<String> subselection(int fromIndex, int toIndex);
}
