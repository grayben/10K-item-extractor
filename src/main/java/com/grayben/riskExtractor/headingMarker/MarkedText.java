package com.grayben.riskExtractor.headingMarker;

import com.grayben.riskExtractor.headingMarker.elector.ElectedTextList;

import java.util.List;

public class MarkedText {
	
	ElectedTextList text;

    public MarkedText(ElectedTextList text) {
        super();
        if(text == null){
            throw new IllegalArgumentException("The argument passed was null");
        }
        this.text = text;
    }

    public List<String> subSelections() {
        return null;
    }

}
