package com.grayben.riskExtractor.headingMarker;

import com.grayben.riskExtractor.headingMarker.elector.ElectedText;

import java.util.List;

public class MarkedText {
	
	ElectedText text;

    public MarkedText(ElectedText text) {
        super();
        if(text == null){
            throw new NullPointerException("The argument passed was null");
        }
        this.text = text;
    }


    /**
     * Assumes that all the lists and indexes are equivalent!
     * @return
     */
    public List<String> subSelections() {
        return null;
        /*
        *//* First, extract the list of indexes *//*
        ArrayList<Integer> indexes; // =something;


        List<String> subSelections = new ArrayList<>();
        for (TextCandidate electee : text.getElectees()) {

            *//* Identify the current start and finishing indexes *//*
            int startIndex = 0; // = something
            int endIndex = 0; //  = something


            ListIterator<String> it = text.getText().listIterator(startIndex);
            while(it.hasNext()){
                if(it.nextIndex() == endIndex){
                    // then stop appending strings
                }

            }
        }
        return null;*/
    }

}
