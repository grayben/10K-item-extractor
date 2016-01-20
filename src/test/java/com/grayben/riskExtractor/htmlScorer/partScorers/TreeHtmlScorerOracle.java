package com.grayben.riskExtractor.htmlScorer.partScorers;

import com.grayben.riskExtractor.htmlScorer.ScoredText;
import com.grayben.riskExtractor.htmlScorer.nodeVisitor.AnnotatedElement;
import com.grayben.riskExtractor.htmlScorer.nodeVisitor.AnnotatedElementTreeAssembler;
import com.grayben.tools.math.parametricEquation.AdaptedParametricEquation;
import com.grayben.tools.math.parametricEquation.ParametricEquation;
import com.grayben.tools.testOracle.ParametricTestOracle;
import org.apache.commons.lang3.tuple.Triple;
import org.jsoup.nodes.Element;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

/**
 * Created by beng on 6/01/2016.
 */
public class TreeHtmlScorerOracle
        extends ParametricTestOracle<TreeHtmlScorerOracle.Configuration, File, ScoredText> {

    private TreeHtmlScorerOracle(ParametricEquation<Configuration, File, ScoredText> parametricEquation) {
        super(parametricEquation);
    }

    enum Configuration {
        SIMPLE
    }

    public static class Factory {

        public static TreeHtmlScorerOracle create(TreeHtmlScorerOracle.Configuration configuration) {
            Factory factory = new Factory(configuration);
            return factory.oracle();
        }

        private final TreeHtmlScorerOracle.Configuration configuration;

        private Factory(Configuration configuration){
            this.configuration = configuration;
        }

        private TreeHtmlScorerOracle oracle(){
            AdaptedParametricEquation<Configuration, AnnotatedElement, File, ScoredText> adaptedParametricEquation
                    = setupAdaptedParametricEquation();
            return new TreeHtmlScorerOracle(adaptedParametricEquation);
        }

        private AdaptedParametricEquation<Configuration,AnnotatedElement,File,ScoredText>
        setupAdaptedParametricEquation() {
            Function<Configuration, AnnotatedElement> adapter
                    = setupConfigurationAdapter();
            ParametricEquation<AnnotatedElement, File, ScoredText> annotationUnpackingFunction
                    = setupAnnotationUnpackingFunction();
            return new AdaptedParametricEquation<>(adapter, annotationUnpackingFunction);
        }

        //TODO: consider moving to class AnnotatedElementTreeDisassembler or similar class
        private ParametricEquation<AnnotatedElement, File, ScoredText> setupAnnotationUnpackingFunction() {
            return null;
        }

        private Function<Configuration, AnnotatedElement> setupConfigurationAdapter() {

            Function
                    <Configuration, Triple<
                    List<Element>,
                    AnnotatedElementTreeAssembler.Configuration,
                    Set<Scorer<Element>>>
                    > generateAssemblerParamsFunction = generateAssemblerParamsFunction();

            Function<
                    Triple<
                            List<Element>,
                            AnnotatedElementTreeAssembler.Configuration,
                            Set<Scorer<Element>>
                            >
                    , AnnotatedElementTreeAssembler>
                    constructAssemblerFunction = constructAssemblerFunction();

            //TODO: consider moving into AnnotatedElementTreeAssembler itself
            Function<AnnotatedElementTreeAssembler, AnnotatedElement> assembleTreeFunction
                    = AnnotatedElementTreeAssembler::getRootAnnotation;

            return generateAssemblerParamsFunction.andThen(constructAssemblerFunction).andThen(assembleTreeFunction);
        }

        //TODO: consider moving to AnnotatedElementTreeAssembler.Factory
        private Function<Configuration, Triple<List<Element>, AnnotatedElementTreeAssembler.Configuration, Set<Scorer<Element>>>> generateAssemblerParamsFunction() {
            return null;
        }

        //TODO: consider moving into AnnotatedElementTreeAssembler itself
        private Function<Triple<List<Element>, AnnotatedElementTreeAssembler.Configuration, Set<Scorer<Element>>>, AnnotatedElementTreeAssembler> constructAssemblerFunction() {
            return triple -> new AnnotatedElementTreeAssembler(triple.getLeft(), triple.getMiddle(), triple.getRight());
        }
    }
}
