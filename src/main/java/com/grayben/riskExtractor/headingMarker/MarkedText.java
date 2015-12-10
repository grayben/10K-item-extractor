package com.grayben.riskExtractor.headingMarker;

import com.grayben.riskExtractor.headingMarker.elector.ElectedText;
import org.apache.commons.collections4.list.SetUniqueList;

import java.util.*;

final public class MarkedText
        extends ElectedText {

    Map<Integer, Integer> stringIndexPairs = null;

    List<String> selectedSections = null;

    public MarkedText(ElectedText text) {
        super(text);
    }


    private Map<Integer, Integer> getStringIndexPairs(){

        //assumes nominees contains all electees
        assert(getNominees().containsAll(getElectees()));

        if (this.stringIndexPairs == null){

            //I want to use

            //Id the elected indexes
            SetUniqueList<Integer> electees = this.getElectees();

            SetUniqueList<Integer> nominees = this.getNominees();

            Iterator<Integer> electeeIterator = electees.iterator();



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
