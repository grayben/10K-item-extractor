package com.grayben.riskExtractor.htmlScorer.partScorers;

import com.grayben.riskExtractor.htmlScorer.ScoredText;
import com.grayben.riskExtractor.htmlScorer.TreeHtmlScorer;
import org.jsoup.nodes.Node;

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
    Function<File, ScoredText> oracleScorer;

    //this is the input to generating both input and output.
    //f(seed) -> (File input, ScoredText expectedOutput) should be simpler
    //than SUT.process(File input) -> ScoredText output, otherwise this oracle is
    //more complex than the SUT itself and therefore pointless.
    Node seed;

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
        setupScorer();
        setupSut();
        setupInput();
        setupExpectedOutput();
    }

    private void setupScorer(){
        switch (configuration){
            case SIMPLE:
                simpleSetupScorer();
                break;
        }
    }

    private void setupSut(){
        switch (configuration){
            case SIMPLE:
                simpleSetupSut();
                break;
        }
    }

    private void setupInput(){
        switch (configuration){
            case SIMPLE:
                simpleSetupInput();
                break;
        }
    }

    private void setupExpectedOutput(){
        switch (configuration){
            case SIMPLE:
                simpleSetupExpectedOutput();
                break;
        }
    }

    private void simpleSetupScorer() {
        //TODO: implement
    }

    private void simpleSetupSut() {
        //TODO: implement
    }

    private void simpleSetupInput() {
        //TODO: implement
    }

    private void simpleSetupExpectedOutput() {
        //TODO: implement
    }
}
