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

        SetUniqueList<Integer> targetTextIndex
                = SetUniqueList.setUniqueList(new ArrayList<>());

        ListIterator<TextElementClass> it
                = param.listIterator();

        boolean onSelectedContent = false;

        while (it.hasNext()) {
            int index = it.nextIndex();
            TextElementClass elementType = it.next();

            if (elementType.equals(TextElementClass.ELECTED_HEADING)) {
                targetTextIndex.add(index);
                onSelectedContent = true;

            } else if (elementType.equals(TextElementClass.NOMINATED_HEADING))
                onSelectedContent = false;

            else if (elementType.equals(TextElementClass.NON_HEADING_CONTENT)) {
                if (onSelectedContent) {
                    targetTextIndex.add(index);
                }
            }
        }

        this.testExpectedOutput = constructExpectedOutput(targetTextIndex, testInput);
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
