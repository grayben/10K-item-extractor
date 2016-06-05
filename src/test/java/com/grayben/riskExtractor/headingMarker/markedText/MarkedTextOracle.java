package com.grayben.riskExtractor.headingMarker.markedText;

import com.grayben.riskExtractor.headingMarker.Elector;
import org.apache.commons.collections4.list.SetUniqueList;

import java.util.*;

public class MarkedTextOracle {

    private Elector.ElectedText testInput;
    private Set<String> testExpectedOutput;

    protected Elector.ElectedText getTestInput(){
        return this.testInput;
    }

    protected Set<String> getTestExpectedOutput(){
        return Collections.unmodifiableSet(this.testExpectedOutput);
    }

    protected MarkedTextOracle(List<TextElementClass> classifiedList){
        this.testInput = generateTestInput(classifiedList);
        generateTestExpectedOutput(classifiedList, this.testInput);
    }

    protected boolean validateResult(Set<String> result){
        Set<String> expectedResult = getTestExpectedOutput();
        return result.equals(expectedResult);
    }

    private void generateTestExpectedOutput(List<TextElementClass> param, Elector.ElectedText testInput) {

        class IndexHelper {
            private Integer startIndex;
            private Integer endIndex;
            private Integer currentIndex;
            private Boolean onSelectedContent;
            private Map<Integer, Integer> targetIndexRanges;
            private List<TextElementClass> elementList;

            IndexHelper(List<TextElementClass> elementList){
                startIndex = null;
                endIndex = null;
                currentIndex = null;
                onSelectedContent = false;
                targetIndexRanges = new HashMap<>();
                this.elementList = elementList;
            }

            private Map<Integer, Integer> process(){
                ListIterator<TextElementClass> iterator
                        = elementList.listIterator();

                while (iterator.hasNext()) {
                    currentIndex = iterator.nextIndex();

                    TextElementClass elementType = iterator.next();

                    if (elementType.equals(TextElementClass.ELECTED_HEADING)) {
                        encounterElectedHeading();

                    } else if (elementType.equals(TextElementClass.NOMINATED_HEADING)) {
                        encounterNominatedHeading();
                    }
                }

                //now, check to see if there was a non-terminated target section
                if(startIndex != null){
                    //the endIndex must be null, else the section should have been closed
                    assert endIndex == null;

                    currentIndex = elementList.size();

                    completeMapEntry();
                }

                return targetIndexRanges;

            }

            private void encounterElectedHeading(){
                /* if we were already in a section,
                we need to break off and start a new section */
                if(onSelectedContent){
                    completeMapEntry();
                }
                beginMapEntry();
            }

            private void encounterNominatedHeading(){


                if (onSelectedContent) {
                    completeMapEntry();
                }
                /* assign onSelectedContent := false
                    -> (startIndex == null & endIndex == null) */
                assert startIndex == null && endIndex == null;
                onSelectedContent = false;
            }

            private void beginMapEntry(){
                //assign(startIndex) -> startIndex was null
                assert startIndex == null;
                startIndex = currentIndex;
                onSelectedContent = true;
            }

            private void completeMapEntry(){
                //if(onSelectedContent) -> startIndex is assigned
                assert startIndex != null;

                //assign(endIndex) -> endIndex was null
                assert endIndex == null;
                // index - 1 because we don't want to include the heading
                endIndex = currentIndex - 1;

                targetIndexRanges.put(startIndex, endIndex);
                startIndex = null;
                endIndex = null;
            }

        }

        IndexHelper indexHelper = new IndexHelper(param);

        Map<Integer, Integer> targetIndexRanges = indexHelper.process();

        this.testExpectedOutput = constructExpectedOutput(targetIndexRanges, testInput);
    }

    private Elector.ElectedText generateTestInput(List<TextElementClass> param){

        List<String> textInput = new ArrayList<>();
        SetUniqueList<Integer> nomineeIndex
                = SetUniqueList.setUniqueList(new ArrayList<>());
        SetUniqueList<Integer> electeeIndex
                = SetUniqueList.setUniqueList(new ArrayList<>());

        ListIterator<TextElementClass> it
                = param.listIterator();

        while(it.hasNext()){
            int index;
            index = it.nextIndex();
            TextElementClass elementType = it.next();
            String stringToAdd;
            stringToAdd = index + ": " + elementType.archetype();
            textInput.add(stringToAdd);

            if(elementType.equals(TextElementClass.ELECTED_HEADING)) {
                nomineeIndex.add(index);
                electeeIndex.add(index);
            }
            else if(elementType.equals(TextElementClass.NOMINATED_HEADING)) {
                nomineeIndex.add(index);
            }
        }

        return new Elector.ElectedText(
                textInput, nomineeIndex, electeeIndex
        );
    }

    private Set<String>
    constructExpectedOutput(Map<Integer, Integer> targetIndexRanges, Elector.ElectedText testInput){
        Iterator<Map.Entry<Integer, Integer>> it = targetIndexRanges.entrySet().iterator();
        Set<String> testExpectedOutput = new HashSet<>();
        while(it.hasNext()){
            Map.Entry<Integer, Integer> entry = it.next();
            int startIndex = entry.getKey();
            int endIndex = entry.getValue();

            //endIndex + 1 because subList is exclusive of endIndex index argument
            List<String> subList = testInput.getStringList().subList(startIndex, endIndex + 1);

            StringBuilder sb = new StringBuilder();
            for (String string : subList
                 ) {
                sb.append(string + " ");
            }
            testExpectedOutput.add(sb.toString().replaceAll("\\s+", " ").trim());
        }
        return testExpectedOutput;
    }
}
