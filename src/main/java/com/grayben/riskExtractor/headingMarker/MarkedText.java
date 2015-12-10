package com.grayben.riskExtractor.headingMarker;

import com.grayben.riskExtractor.headingMarker.elector.ElectedText;

import java.util.*;

final public class MarkedText
        extends ElectedText {

    Map<Integer, Integer> stringIndexPairs = null;

    List<String> selectedSections = null;

    public MarkedText(ElectedText text) {
        super(text);
    }

    private Map<Integer, Integer> getStringIndexPairs(){

        if (this.stringIndexPairs == null){
            //TODO: assumes that lists are sorted!

            //TODO: doesn't handle when elected is last nominee!

            //I want to use

            //Id the elected indexes
            SortedSet<Integer> electees = this.getElectees();

            SortedSet<Integer> nominees = this.getNominees();





            this.stringIndexPairs = null;
        }

        return this.stringIndexPairs;
    }

    private List<String> getSelectedSections(){

        if(this.selectedSections == null){
            Iterator<Map.Entry<Integer, Integer>> pairIterator
                    = getStringIndexPairs().entrySet().iterator();

            List<String> selectedSections = new ArrayList<>();

            while(pairIterator.hasNext()){
                Map.Entry<Integer, Integer> entry = pairIterator.next();
                int startIndex = entry.getKey();
                //TODO: test that this is the correct range
                int endIndex = entry.getValue() - 1;
                StringBuilder sb = new StringBuilder();
                List<String> textSectionElements
                        = getStringList().subList(startIndex, endIndex);
                ListIterator<String> sectionElementIterator
                        = textSectionElements.listIterator();
                while(sectionElementIterator.hasNext()){
                    sb.append(sectionElementIterator.next());
                }
                String section = sb.toString();
                selectedSections.add(section);
            }
            this.selectedSections = selectedSections;
        }

        return Collections.unmodifiableList(this.selectedSections);


    }


    /**
     * Assumes that all the lists and indexes are equivalent!
     * @return
     */
    public List<String> subSelections() {

        //TODO: variable and method naming semantics

        return getSelectedSections();
    }

}
