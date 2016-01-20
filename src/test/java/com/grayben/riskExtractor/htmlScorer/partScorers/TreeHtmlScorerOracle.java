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
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.function.BiFunction;
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

            return instantiateAssembler().andThen(AnnotatedElementTreeAssembler::getRootAnnotation);
        }

        private Function<Configuration, AnnotatedElementTreeAssembler> instantiateAssembler() {
            Function<Configuration, List<? extends Object>> initParams;

            Function<List<? extends Object>, AnnotatedElementTreeAssembler> instantiate
                    = objects -> {
                ListIterator<? extends Object> it = objects.listIterator();
                List<Element> elementList;
                AnnotatedElementTreeAssembler.Configuration assemblerConfig;
                Set<Scorer<Element>> scorers;

                elementList = ((List<Element>) it.next());
                assemblerConfig = ((AnnotatedElementTreeAssembler.Configuration) it.next());
                scorers = ((Set<Scorer<Element>>) it.next());

                return new AnnotatedElementTreeAssembler(elementList, assemblerConfig, scorers);
            };
            //return instantiateAssemblerFunction().andThen();
        }

        //TODO: consider moving to AnnotatedElementTreeAssembler.Factory
        private Function<Configuration, Triple<List<Element>, AnnotatedElementTreeAssembler.Configuration, Set<Scorer<Element>>>> generateAssemblerParamsFunction() {
            //// TODO: 21/01/2016 implement
            return null;
        }

        //TODO: consider moving into AnnotatedElementTreeAssembler itself
        private Function<Triple<List<Element>, AnnotatedElementTreeAssembler.Configuration, Set<Scorer<Element>>>, AnnotatedElementTreeAssembler> constructAssemblerFunction() {
            return triple -> new AnnotatedElementTreeAssembler(triple.getLeft(), triple.getMiddle(), triple.getRight());
        }
    }
}
