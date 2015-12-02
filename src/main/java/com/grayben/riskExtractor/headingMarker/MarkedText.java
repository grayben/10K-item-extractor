package com.grayben.riskExtractor.headingMarker;

import com.grayben.riskExtractor.headingMarker.elector.ElectedText;

import java.util.*;

final public class MarkedText
        extends ElectedText {

    Map<Integer, Integer> stringIndexPairs = null;

    Collection<String> selectedSections = null;

    public MarkedText(ElectedText text) {
        super(text);
    }

    private Map<Integer, Integer> getStringIndexPairs(){

        if (this.stringIndexPairs == null){
            //TODO: assumes that lists are sorted!

            //TODO: doesn't handle when elected is last nominee!

            //I want to use

            //Id the elected indexes
            List<Integer> electees = this.getElectees();

            List<Integer> nominees = this.getNominees();

            Map<Integer, Integer> stringIndexPairs = new HashMap<>();
            ListIterator<Integer> electedIterator = electees.listIterator();
            int i = 0;
            while(electedIterator.hasNext()){
                //get the start index
                Integer electedStringIndex = electedIterator.next();

                //should be the index into the String list representing the next candidate
                int followingCandidateStringIndex
                        = nominees.get(nominees.indexOf(electedStringIndex));

                stringIndexPairs.put(
                        electedStringIndex,
                        followingCandidateStringIndex
                );
            }

            this.stringIndexPairs = stringIndexPairs;

            //the iterator should have returned elected.size() entries
            assert( i == electees.size() );
        }

        return this.stringIndexPairs;
    }

    private Collection<String> getSelectedSections(){

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

        return Collections.unmodifiableCollection(this.selectedSections);


    }


    /**
     * Assumes that all the lists and indexes are equivalent!
     * @return
     */
    public Collection<String> subSelections() {

        //TODO: variable and method naming semantics

        return getSelectedSections();
    }

}
