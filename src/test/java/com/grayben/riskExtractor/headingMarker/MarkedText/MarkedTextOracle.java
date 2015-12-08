package com.grayben.riskExtractor.headingMarker.markedText;

import com.grayben.riskExtractor.headingMarker.elector.ElectedText;
import com.grayben.riskExtractor.headingMarker.elector.electedText.ElectedTextOracle;

import java.util.*;

/**
 * Created by beng on 4/12/2015.
 */
class MarkedTextOracle extends ElectedTextOracle {

    protected List<Integer> getTargetTextIndex() {
        return targetTextIndex;
    }

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
        //TODO: work out pattern for constructor inheritence
        super();
        List<TextElementClass> defaults = defaultClassifications();
        setupData(defaults);
        generateTestInput();
        constructExpectedOutput();
    }

    protected MarkedTextOracle(List<TextElementClass> classifiedList){
        super(classifiedList);
        setupData(classifiedList);
    }

    @Override
    protected boolean validateResult(List<String> result){
        List<String> expectedResult = new ArrayList<>();
        return false;
    }

    @Override
    protected void setupData(List<TextElementClass> params){

        List<Integer> targetTextIndex = new ArrayList<>();

        ListIterator<TextElementClass> it
                = params.listIterator();

        boolean onSelectedContent = false;

        while(it.hasNext()){
            int index = it.nextIndex();
            TextElementClass elementType = it.next();

            if(elementType.equals(TextElementClass.ELECTED_HEADING)) {
                targetTextIndex.add(index);
                onSelectedContent = true;
            }

            else if(elementType.equals(TextElementClass.NOMINATED_HEADING))
                onSelectedContent = false;

            else if(elementType.equals(TextElementClass.NON_HEADING_CONTENT)) {
                if(onSelectedContent) {
                    targetTextIndex.add(index);
                }
            }
        }
        this.targetTextIndex = targetTextIndex;
    }

    @Override
    private void generateTestInput(){
        ElectedText electedText
                = new ElectedText(
                this.getTextInput(), this.getNomineeIndex(), this.getElecteeIndex()
        );
        this.testInput = electedText;
    }

    @Override
    private void
    constructExpectedOutput(){
        ListIterator<Integer> it = this.getTargetTextIndex().listIterator();
        List<String> testExpectedOutput = new ArrayList<>();
        while(it.hasNext()){
            testExpectedOutput.add(this.getTextInput().get(it.next()));
        }
        this.testExpectedOutput = testExpectedOutput;
    }

    @Override
    void printData(){
        System.out.println("### TEXT INPUT ##########");
        System.out.println(this.getTextInput());
        System.out.println("### NOMINEE INDEX #######");
        System.out.println(this.getNomineeIndex());
        System.out.println("### ELECTEE INDEX #######");
        System.out.println(this.getElecteeIndex());
        System.out.println("### TARGET TEXT INDEX ###");
        System.out.println(this.getTargetTextIndex());
    }
}
