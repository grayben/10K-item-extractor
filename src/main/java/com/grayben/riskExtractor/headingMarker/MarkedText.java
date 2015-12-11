package com.grayben.riskExtractor.headingMarker;

import com.grayben.riskExtractor.headingMarker.elector.ElectedText;
import org.apache.commons.collections4.list.SetUniqueList;

import java.util.*;

final public class MarkedText
        extends ElectedText {

    Map<Integer, Integer> stringIndexPairs = null;

    List<String> selectedSections = null;

    public MarkedText(ElectedText text, Map<Integer, Integer> mapToUse) {
        super(text);
        if( ! mapToUse.isEmpty()){
            throw new IllegalArgumentException(
                    "The map to use must be empty"
            );
        } else {
            this.stringIndexPairs = mapToUse;
        }
        generateStringIndexPairs();
    }

    private void generateStringIndexPairs(){

        //assume that the map is already instantiated
        assert this.stringIndexPairs != null;

        //assumes this is only called once to fill in the map
        assert this.stringIndexPairs.isEmpty();

        //assumes nominees contains all electees
        assert(getNominees().containsAll(getElectees()));

        Map<Integer, Integer> map = this.stringIndexPairs;

        SetUniqueList<Integer> electees = this.getElectees();

        SetUniqueList<Integer> nominees = this.getNominees();

        for (Integer electee : electees) {
            Integer startIndex;
            Integer endIndex;
            startIndex = electee;
            int indexOfStartIndexInNominees
                    = nominees.indexOf(startIndex);
            //if the elected heading is the final nominee
            if(indexOfStartIndexInNominees == nominees.size() - 1){
                endIndex = null;
            } else {
                //the index of the next nominee
                endIndex = nominees.get(indexOfStartIndexInNominees + 1);
                //minus 1: exclude the nominee
                endIndex = endIndex - 1;
            }
            map.put(startIndex, endIndex);
        }

        this.stringIndexPairs = map;

    }



    private Map<Integer, Integer> getStringIndexPairs(){

        //stringIndexPairs must be setup before this method is called
        assert this.stringIndexPairs != null;

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
                int endIndex;
                //TODO: test that this is the correct range

                //if null: there is no terminating nominee; extract to end!
                if(entry.getValue() == null){
                    endIndex = getStringList().size() - 1;
                } else {
                    endIndex = entry.getValue();
                }
                List<String> textSectionElements
                        // endIndex + 1 because subList is exclusive of endIndex
                        = getStringList().subList(startIndex, endIndex + 1);
                selectedSections.addAll(textSectionElements);
            }
            this.selectedSections = selectedSections;
        }

        return Collections.unmodifiableList(this.selectedSections);


    }


    public List<String> subSelections() {

        //TODO: variable and method naming semantics

        return getSelectedSections();
    }

}
