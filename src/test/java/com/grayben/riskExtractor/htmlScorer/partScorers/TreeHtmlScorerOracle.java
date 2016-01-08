package com.grayben.riskExtractor.htmlScorer.partScorers;

import com.grayben.riskExtractor.htmlScorer.ScoredText;
import com.grayben.riskExtractor.htmlScorer.ScoringAndFlatteningNodeVisitor;
import com.grayben.riskExtractor.htmlScorer.TreeHtmlScorer;
import com.grayben.riskExtractor.htmlScorer.nodeVisitor.AnnotatedElement;
import com.grayben.riskExtractor.htmlScorer.nodeVisitor.AnnotatedElementTreeAssembler;
import com.grayben.testing.InputAndExpectedOutputRetrievable;
import com.grayben.testing.SeedBasedInputExpectedOutputGenerator;
import org.mockito.Mockito;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Created by beng on 6/01/2016.
 */
public class TreeHtmlScorerOracle implements InputAndExpectedOutputRetrievable<File, ScoredText> {

    //interface fields

    //a tree htmlScorer instantiated with an appropriate stub of ScoringAndFlatteningNodeVisitor
    TreeHtmlScorer sut;

    TreeHtmlScorer getSut(){
        return this.sut;
    }

    @Override
    public File getInput() {
        return this.generator.getInput();
    }

    @Override
    public ScoredText getExpectedOutput(){
        return this.generator.getExpectedOutput();
    }

    //internal fields

    enum Configuration {
        SIMPLE
    }

    private Configuration configuration;

    private static class NVStubber implements Function<ScoredText, ScoringAndFlatteningNodeVisitor> {

        @Override
        public ScoringAndFlatteningNodeVisitor apply(ScoredText scoredText) {
            ScoringAndFlatteningNodeVisitor nv = Mockito.mock(ScoringAndFlatteningNodeVisitor.class);
            Mockito.doReturn(scoredText).when(nv.getFlatText());
            return nv;
        }
    }

    private AnnotatedElementTreeAssembler treeAssembler;

    private SeedBasedInputExpectedOutputGenerator<AnnotatedElement, File, ScoredText> generator;

    //constructors

    TreeHtmlScorerOracle(Configuration configuration, AnnotatedElementTreeAssembler treeAssembler){
        validateInitParams(configuration, treeAssembler);
        processInitParams(configuration, treeAssembler);
        setup();
    }

    private void validateInitParams(Configuration configuration, AnnotatedElementTreeAssembler treeAssembler) {
        switch (configuration){
            case SIMPLE:
                break;
            default:
                throw new IllegalArgumentException(
                        "Configuration option was not recognised"
                );
        }
        if (treeAssembler == null) {
            throw new NullPointerException(
                    "AnnotatedElementTreeAssembler was null"
            );
        }
        AnnotatedElementTreeAssembler assembler = new AnnotatedElementTreeAssembler()
    }

    private void processInitParams(Configuration configuration, AnnotatedElementTreeAssembler treeAssembler) {
        this.configuration = configuration;
        this.treeAssembler = treeAssembler;
    }

    private void setup() {

        //this is the input to generating both input and output.
        //f(seed) -> (File input, ScoredText expectedOutput) should be simpler
        //than SUT.process(File input) -> ScoredText output, otherwise this oracle is
        //more complex than the SUT itself and therefore pointless.
        AnnotatedElement seed = setupSeed();

        setupGenerator(seed);

        setupSut();
    }

    //create AnnotatedElement annotationTree using the provided List<Scorer<Element>> and List<Element>
    private AnnotatedElement setupSeed() {
        AnnotatedElement annotationTree = null;
        switch (configuration){
            case SIMPLE:
                annotationTree = simpleSetupSeed();
                break;
        }
        return annotationTree;
    }

    private void setupGenerator(AnnotatedElement seed) {
        this.generator = new SeedBasedInputExpectedOutputGenerator<AnnotatedElement, File, ScoredText>(seed) {


            @Override
            protected File generateInput(AnnotatedElement seed) {
                //TODO: implement
                return null;
            }

            @Override
            protected ScoredText generateExpectedOutput(AnnotatedElement seed) {
                //TODO: implement
                return null;
            }

            @Override
            protected void validateInitParams(AnnotatedElement seed) {
                if (seed == null) {
                    throw new NullPointerException(
                            "The input parameter 'seed' was null"
                    );
                }
            }
        };
    }

    private List<Object> setupSutInitParams() {
        List<Object> sutInitParams = new ArrayList<>();
        NVStubber stubber = new NVStubber();
        ScoringAndFlatteningNodeVisitor nvStub = stubber.apply(getExpectedOutput());
        sutInitParams.add(nvStub);
        return sutInitParams;
    }

    //use the generated file scorer to create a configured SUT Spy
    private void setupSut(){
        List<Object> sutInitParams = setupSutInitParams();
        this.sut = new TreeHtmlScorer((ScoringAndFlatteningNodeVisitor) sutInitParams.remove(0));
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Config-specific methods //////////////////////////////////////////////////////////////////////////////

    private AnnotatedElement simpleSetupSeed() {
        //TODO: implement
        return null;
    }
}
