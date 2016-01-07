package com.grayben.riskExtractor.htmlScorer.partScorers;

import com.grayben.riskExtractor.htmlScorer.ScoredText;
import com.grayben.riskExtractor.htmlScorer.TreeHtmlScorer;
import com.grayben.riskExtractor.htmlScorer.nodeVisitor.AnnotatedElement;

import java.io.File;
import java.util.function.Function;

/**
 * Created by beng on 6/01/2016.
 */
public class TreeHtmlScorerOracle {

    //TODO: decide on design, declare needed instance variables, decide upon flow of control

    //interface fields

    //a tree htmlScorer instantiated with an appropriate Mocked Spy of ScoringAndFlatteningNodeVisitor
    TreeHtmlScorer sut;
    File input;
    ScoredText expectedOutput;

    TreeHtmlScorer getSut(){
        //TODO: implement
        return null;
    }

    File getInput() {
        //TODO: implement
        return null;
    }

    ScoredText getExpectedOutput(){
        //TODO: implement
        return null;
    }

    //internal fields

    //this will be used by the SUT Spy to stub f(File input) -> ScoredText output
    Function<File, ScoredText> fileProcessorFunction;

    //this will be used by both the

    //this is the input to generating both input and output.
    //f(seed) -> (File input, ScoredText expectedOutput) should be simpler
    //than SUT.process(File input) -> ScoredText output, otherwise this oracle is
    //more complex than the SUT itself and therefore pointless.
    AnnotatedElement annotationTree;

    enum Configuration {
        SIMPLE
    }

    private Configuration configuration;

    //constructors

    TreeHtmlScorerOracle(Configuration configuration){
        validateInitParams(configuration);
        processInitParams(configuration);
        setup();
    }

    private void validateInitParams(Configuration configuration) {
        switch (configuration){
            case SIMPLE:
                break;
            default:
                throw new IllegalArgumentException(
                        "Configuration option was not recognised"
                );
        }
    }

    private void processInitParams(Configuration configuration) {
        this.configuration = configuration;
    }

    private void setup() {
        setupFileProcessorFunction();
        setupSut();
        setupSeed();
        setupInput();
        setupExpectedOutput();
    }

    //create a
    private void setupFileProcessorFunction(){
        switch (configuration){
            case SIMPLE:
                simpleSetupFileProcessorFunction();
                break;
        }
    }

    //use the generated file scorer to create a configured SUT Spy
    private void setupSut(){
        switch (configuration){
            case SIMPLE:
                simpleSetupSut();
                break;
        }
    }

    //create AnnotatedElement annotationTree using the provided List<Scorer<Element>> and List<Element>
    private void setupSeed() {
        switch (configuration){
            case SIMPLE:
                simpleSetupSeed();
                break;
        }
    }

    //convert AnnotatedElement annotationTree into File input
    private void setupInput(){
        switch (configuration){
            case SIMPLE:
                simpleSetupInput();
                break;
        }
    }

    //convert AnnotatedElement annotationTree into ScoredText output
    private void setupExpectedOutput(){
        switch (configuration){
            case SIMPLE:
                simpleSetupExpectedOutput();
                break;
        }
    }

    private void simpleSetupFileProcessorFunction() {
        //TODO: implement
    }

    private void simpleSetupSut() {
        //TODO: implement
    }

    private void simpleSetupSeed() {
        //TODO: implement
    }

    private void simpleSetupInput() {
        //TODO: implement
    }

    private void simpleSetupExpectedOutput() {
        //TODO: implement
    }
}
