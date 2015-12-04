package com.grayben.riskExtractor.headingMarker.nominator;

import com.grayben.riskExtractor.headingMarker.MarkedText;
import com.grayben.riskExtractor.headingMarker.elector.ElectedText;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by beng on 4/12/2015.
 */
public class MarkedTextOracle {

    private enum StringClassification {
        ELECTED_HEADING,
        NOMINATED_HEADING,
        SELECTED_CONTENT,
        EXCLUDED_CONTENT
    }

    static final String ELECTED_HEADING = "ELECTED HEADING";
    static final String NOMINATED_HEADING = "NOMINATED HEADING";
    static final String SELECTED_CONTENT = "selected content";
    static final String EXCLUDED_CONTENT = "excluded content";

    MarkedText testInput = null;

    List<String> testExpectedOutput = null;

    public MarkedTextOracle(){
        List<StringClassification> defaults = defaultClassifications();
        setupTestInput(defaults);
        setupTestOutput(defaults);
    }

    public MarkedTextOracle(List<StringClassification> classifiedList){
        setupTestInput(classifiedList);
        setupTestOutput(classifiedList);
    }

    private void
    setupTestOutput(List<StringClassification> classificationList){
        List<String> testExpectedOutput;
    }

    private void
    setupTestInput(List<StringClassification> classificationList){

        List<Integer> nomineeIndex = new ArrayList<>();
        List<Integer> electeeIndex = new ArrayList<>();
        List<Integer> includedTextIndex = new ArrayList<>();
        List<Integer> excludedTextIndex = new ArrayList<>();


        List<String> stringList = new ArrayList<>();

        ListIterator<StringClassification> it
                = classificationList.listIterator();

        while(it.hasNext()){
            int index = it.nextIndex();
            StringClassification element = it.next();

            if(element.equals(StringClassification.ELECTED_HEADING)) {
                electeeIndex.add(index);
                stringList.add(index + ": " + this.ELECTED_HEADING);
            }
            else if(element.equals(StringClassification.NOMINATED_HEADING)) {
                nomineeIndex.add(index);
                stringList.add(index + ": " + this.NOMINATED_HEADING);
            }
            else if(element.equals(StringClassification.SELECTED_CONTENT)) {
                includedTextIndex.add(index);
                stringList.add(index + ": " + this.SELECTED_CONTENT);
            }
            else {
                excludedTextIndex.add(index);
                stringList.add(index + ": " + this.EXCLUDED_CONTENT);
            }
        }

        ElectedText electedText = new ElectedText(
                stringList,
                nomineeIndex,
                electeeIndex
        );

        this.testInput = new MarkedText(electedText);
    }

    public boolean validateResult(List<String> result){
        List<String> expectedResult = new ArrayList<>();
        return false;
    }

    private List<StringClassification> defaultClassifications(){
        List<StringClassification> list = new ArrayList<>();
        list.add(StringClassification.EXCLUDED_CONTENT);
        list.add(StringClassification.EXCLUDED_CONTENT);
        list.add(StringClassification.EXCLUDED_CONTENT);
        list.add(StringClassification.NOMINATED_HEADING);
        list.add(StringClassification.NOMINATED_HEADING);
        list.add(StringClassification.EXCLUDED_CONTENT);
        list.add(StringClassification.ELECTED_HEADING);
        list.add(StringClassification.SELECTED_CONTENT);
        list.add(StringClassification.SELECTED_CONTENT);
        list.add(StringClassification.NOMINATED_HEADING);
        list.add(StringClassification.NOMINATED_HEADING);
        list.add(StringClassification.EXCLUDED_CONTENT);
        list.add(StringClassification.ELECTED_HEADING);
        list.add(StringClassification.SELECTED_CONTENT);
        list.add(StringClassification.SELECTED_CONTENT);
        list.add(StringClassification.NOMINATED_HEADING);
        list.add(StringClassification.NOMINATED_HEADING);
        list.add(StringClassification.EXCLUDED_CONTENT);
        list.add(StringClassification.ELECTED_HEADING);
        list.add(StringClassification.SELECTED_CONTENT);
        list.add(StringClassification.SELECTED_CONTENT);

        return list;
    }
}
