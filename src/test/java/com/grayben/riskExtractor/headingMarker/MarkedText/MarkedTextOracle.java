package com.grayben.riskExtractor.headingMarker.markedText;

import com.grayben.riskExtractor.headingMarker.elector.ElectedText;

import java.util.*;

/**
 * Created by beng on 4/12/2015.
 */
class MarkedTextOracle {

    private enum StringClass {
        ELECTED_HEADING("ELECTED HEADING"),
        NOMINATED_HEADING("NOMINATED HEADING"),
        NON_HEADING_CONTENT("non-heading content");

        private final String archetype;

        StringClass(String archetype){
            this.archetype = archetype;
        }

        String archetype(){
            return this.archetype;
        }
    }

    private List<Integer> nomineeIndex;
    private List<Integer> electeeIndex;
    private List<String> textInput;

    private List<Integer> targetTextIndex;

    private ElectedText testInput;
    private List<String> testExpectedOutput;

    protected ElectedText getTestInput(){
        return this.testInput;
    }

    protected List<String> getTestExpectedOutput(){
        return this.testExpectedOutput;
    }

    protected MarkedTextOracle(){
        List<StringClass> defaults = defaultClassifications();
        setupData(defaults);
    }

    protected MarkedTextOracle(List<StringClass> classifiedList){
        setupData(classifiedList);
    }

    protected boolean validateResult(List<String> result){
        List<String> expectedResult = new ArrayList<>();
        return false;
    }


    private void constructIntermediateData(List<StringClass> params){
        List<String> textInput = new ArrayList<>();

        List<Integer> nomineeIndex = new ArrayList<>();
        List<Integer> electeeIndex = new ArrayList<>();

        List<Integer> targetTextIndex = new ArrayList<>();


        ListIterator<StringClass> it
                = params.listIterator();

        boolean onSelectedContent = false;

        while(it.hasNext()){
            int index = it.nextIndex();
            StringClass elementType = it.next();
            String stringToAdd = index + ": " + elementType.archetype();
            textInput.add(stringToAdd);

            if(elementType.equals(StringClass.ELECTED_HEADING)) {
                electeeIndex.add(index);

                targetTextIndex.add(index);

                onSelectedContent = true;
            }
            else if(elementType.equals(StringClass.NOMINATED_HEADING)) {
                nomineeIndex.add(index);

                onSelectedContent = false;
            }
            else if(elementType.equals(StringClass.NON_HEADING_CONTENT)) {

                if(onSelectedContent) {
                    targetTextIndex.add(index);
                }
            }
        }
        this.targetTextIndex = targetTextIndex;

        this.electeeIndex = electeeIndex;
        this.nomineeIndex = nomineeIndex;
        this.textInput = textInput;
    }

    private void setupData(List<StringClass> params){
        constructIntermediateData(params);
        constructTestInput();
        constructExpectedOutput();
    }

    private void constructTestInput(){
        ElectedText electedText
                = new ElectedText(
                this.textInput, this.nomineeIndex, this.electeeIndex
        );
        this.testInput = electedText;
    }

    private void
    constructExpectedOutput(){
        ListIterator<Integer> it = this.targetTextIndex.listIterator();
        List<String> testExpectedOutput = new ArrayList<>();
        while(it.hasNext()){
            testExpectedOutput.add(this.textInput.get(it.next()));
        }
        this.testExpectedOutput = testExpectedOutput;
    }

    private List<StringClass> defaultClassifications(){
        List<StringClass> list = new ArrayList<>();
        list.add(StringClass.NON_HEADING_CONTENT);
        list.add(StringClass.NON_HEADING_CONTENT);
        list.add(StringClass.NON_HEADING_CONTENT);
        list.add(StringClass.NOMINATED_HEADING);
        list.add(StringClass.NOMINATED_HEADING);
        list.add(StringClass.NON_HEADING_CONTENT);
        list.add(StringClass.ELECTED_HEADING);
        list.add(StringClass.NON_HEADING_CONTENT);
        list.add(StringClass.NON_HEADING_CONTENT);
        list.add(StringClass.NOMINATED_HEADING);
        list.add(StringClass.NOMINATED_HEADING);
        list.add(StringClass.NON_HEADING_CONTENT);
        list.add(StringClass.ELECTED_HEADING);
        list.add(StringClass.NON_HEADING_CONTENT);
        list.add(StringClass.NON_HEADING_CONTENT);
        list.add(StringClass.NOMINATED_HEADING);
        list.add(StringClass.NOMINATED_HEADING);
        list.add(StringClass.NON_HEADING_CONTENT);
        list.add(StringClass.ELECTED_HEADING);
        list.add(StringClass.NON_HEADING_CONTENT);
        list.add(StringClass.NON_HEADING_CONTENT);

        return list;
    }

    void printData(){
        System.out.println("### TEXT INPUT ##########");
        System.out.println(this.textInput);
        System.out.println("### NOMINEE INDEX #######");
        System.out.println(this.nomineeIndex);
        System.out.println("### ELECTEE INDEX #######");
        System.out.println(this.electeeIndex);
        System.out.println("### TARGET TEXT INDEX ###");
        System.out.println(this.targetTextIndex);
    }
}
