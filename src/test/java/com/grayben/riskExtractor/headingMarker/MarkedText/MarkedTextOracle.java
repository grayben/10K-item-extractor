package com.grayben.riskExtractor.headingMarker.markedText;

import com.grayben.riskExtractor.headingMarker.elector.ElectedText;
import org.apache.commons.collections4.list.SetUniqueList;

import java.util.*;

class MarkedTextOracle {

    private ElectedText testInput;
    private List<String> testExpectedOutput;

    protected ElectedText getTestInput(){
        return this.testInput;
    }

    protected List<String> getTestExpectedOutput(){
        return Collections.unmodifiableList(this.testExpectedOutput);
    }

    protected MarkedTextOracle(List<TextElementClass> classifiedList){
        this.testInput = generateTestInput(classifiedList);
        generateTestExpectedOutput(classifiedList, this.testInput);
    }

    protected boolean validateResult(List<String> result){
        List<String> expectedResult = getTestExpectedOutput();
        return result.equals(expectedResult);
    }

    protected void generateTestExpectedOutput(List<TextElementClass> param, ElectedText testInput) {

        class IndexHelper {
            Integer startIndex;
            Integer endIndex;
            Integer currentIndex;
            Boolean onSelectedContent;
            Map<Integer, Integer> targetRanges;
            List<TextElementClass> elementList;

            IndexHelper(List<TextElementClass> elementList){
                startIndex = null;
                endIndex = null;
                currentIndex = null;
                onSelectedContent = false;
                targetRanges = new HashMap<>();
                this.elementList = elementList;
            }

            Map<Integer, Integer> process(){
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

            }

            void encounterElectedHeading(){
                /* if we were already in a section,
                we need to break off and start a new section */
                if(onSelectedContent){
                    completeMapEntry();
                }
                beginMapEntry();
            }

            void encounterNominatedHeading(){
                //this must not be called if the heading is also elected
                assert false;


                if (onSelectedContent) {
                    completeMapEntry();
                }
                /* assign onSelectedContent := false
                    -> (startIndex == null & endIndex == null) */
                assert startIndex == null && endIndex == null;
                onSelectedContent = false;
            }

            void beginMapEntry(){
                //assign(startIndex) -> startIndex was null
                assert startIndex == null;
                startIndex = currentIndex;
                onSelectedContent = true;
            }

            void completeMapEntry(){
                //if(onSelectedContent) -> startIndex is assigned
                assert startIndex != null;

                //assign(endIndex) -> endIndex was null
                assert endIndex == null;
                // index - 1 because we don't want to include the heading
                endIndex = currentIndex - 1;

                targetRanges.put(startIndex, endIndex);
                startIndex = null;
                endIndex = null;
            }

        }

        IndexHelper indexHelper = new IndexHelper(param);

        Map<Integer, Integer> targetRanges = indexHelper.process();

        this.testExpectedOutput = constructExpectedOutput(targetRanges, testInput);
    }

    protected ElectedText generateTestInput(List<TextElementClass> param){

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

        return new ElectedText(
                textInput, nomineeIndex, electeeIndex
        );
    }

    private List<String>
    constructExpectedOutput(SetUniqueList<Integer> targetIndex, ElectedText testInput){
        ListIterator<Integer> it = targetIndex.listIterator();
        List<String> testExpectedOutput = new ArrayList<>();
        while(it.hasNext()){
            testExpectedOutput.add(testInput.getStringList().get(it.next()));
        }
        return testExpectedOutput;
    }
}
