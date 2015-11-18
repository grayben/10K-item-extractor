package com.grayben.riskExtractor.extractor;

import com.grayben.riskExtractor.Text;

public interface TextSubselectable {
	Text subselection(int fromIndex, int toIndex);
}
