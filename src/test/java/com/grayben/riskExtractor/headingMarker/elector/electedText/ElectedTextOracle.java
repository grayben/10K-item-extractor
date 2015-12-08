package com.grayben.riskExtractor.headingMarker.elector.electedText;

import com.grayben.riskExtractor.headingMarker.markedText.TextElementClass;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by beng on 8/12/2015.
 */
public class ElectedTextOracle {
    private List<Integer> nomineeIndex;
    private List<Integer> electeeIndex;
    private List<String> textInput;

    public ElectedTextOracle(List<TextElementClass> param) {
        setupData(param);
        generateTestInput();
        generateTestExpectedOutput();
    }

    protected List<Integer> getNomineeIndex() {
        return nomineeIndex;
    }

    protected List<Integer> getElecteeIndex() {
        return electeeIndex;
    }

    protected List<String> getTextInput() {
        return textInput;
    }

    private void setNomineeIndex(List<Integer> nomineeIndex) {
        this.nomineeIndex = nomineeIndex;
    }

    private void setElecteeIndex(List<Integer> electeeIndex) {
        this.electeeIndex = electeeIndex;
    }

    private void setTextInput(List<String> textInput) {
        this.textInput = textInput;
    }

    protected void setupData(List<TextElementClass> param){
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
            else if(elementType.equals(TextElementClass.NON_HEADING_CONTENT)) {
            }
        }

        this.setElecteeIndex(electeeIndex);
        this.setNomineeIndex(nomineeIndex);
        this.setTextInput(textInput);
    }

    private List<TextElementClass> defaultClassifications(){
        List<TextElementClass> list = new ArrayList<>();
        list.add(TextElementClass.NON_HEADING_CONTENT);
        list.add(TextElementClass.NON_HEADING_CONTENT);
        list.add(TextElementClass.NON_HEADING_CONTENT);
        list.add(TextElementClass.NOMINATED_HEADING);
        list.add(TextElementClass.NOMINATED_HEADING);
        list.add(TextElementClass.NON_HEADING_CONTENT);
        list.add(TextElementClass.ELECTED_HEADING);
        list.add(TextElementClass.NON_HEADING_CONTENT);
        list.add(TextElementClass.NON_HEADING_CONTENT);
        list.add(TextElementClass.NOMINATED_HEADING);
        list.add(TextElementClass.NOMINATED_HEADING);
        list.add(TextElementClass.NON_HEADING_CONTENT);
        list.add(TextElementClass.ELECTED_HEADING);
        list.add(TextElementClass.NON_HEADING_CONTENT);
        list.add(TextElementClass.NON_HEADING_CONTENT);
        list.add(TextElementClass.NOMINATED_HEADING);
        list.add(TextElementClass.NOMINATED_HEADING);
        list.add(TextElementClass.NON_HEADING_CONTENT);
        list.add(TextElementClass.ELECTED_HEADING);
        list.add(TextElementClass.NON_HEADING_CONTENT);
        list.add(TextElementClass.NON_HEADING_CONTENT);

        return list;
    }
}
