package com.grayben.riskExtractor.headingMarker.markedText;

import com.grayben.riskExtractor.headingMarker.elector.ElectedText;

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

        List<Integer> targetTextIndex = new ArrayList<>();

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
        List<Integer> nomineeIndex = new ArrayList<>();
        List<Integer> electeeIndex = new ArrayList<>();

        ListIterator<TextElementClass> it
                = param.listIterator();

        while(it.hasNext()){
            int index = it.nextIndex();
            TextElementClass elementType = it.next();
            String stringToAdd = index + ": " + elementType.archetype();
            textInput.add(stringToAdd);

            if(elementType.equals(TextElementClass.ELECTED_HEADING)) {
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
    constructExpectedOutput(List<Integer> targetIndex, ElectedText testInput){
        ListIterator<Integer> it = targetIndex.listIterator();
        List<String> testExpectedOutput = new ArrayList<>();
        while(it.hasNext()){
            testExpectedOutput.add(testInput.getStringList().get(it.next()));
        }
        return testExpectedOutput;
    }
}
