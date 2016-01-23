package com.grayben.riskExtractor.htmlScorer;

import com.grayben.riskExtractor.htmlScorer.nodeVisitor.AnnotatedElement;
import com.grayben.riskExtractor.htmlScorer.nodeVisitor.AnnotatedElementTreeAssembler;
import com.grayben.riskExtractor.htmlScorer.partScorers.Scorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.elementScorers.ElementScorerSetSupplier;
import com.grayben.tools.math.parametricEquation.AdaptedParametricEquation;
import com.grayben.tools.math.parametricEquation.ParametricEquation;
import com.grayben.tools.testOracle.ParametricTestOracle;
import org.apache.commons.lang3.tuple.Triple;
import org.jsoup.nodes.Element;

import java.io.File;
import java.util.HashSet;
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
            Function<Configuration, AnnotatedElement> adapter = setupConfigurationAdapter();
            ParametricEquation<AnnotatedElement, File, ScoredText> parametricEquation = parametricEquation();
            return new AdaptedParametricEquation<>(adapter, parametricEquation);
        }

        //TODO: consider moving to class AnnotatedElementTreeDisassembler or similar class
        private ParametricEquation<AnnotatedElement, File, ScoredText> parametricEquation() {
            return null;
        }

        private Function<Configuration, AnnotatedElement> setupConfigurationAdapter() {

            return instantiateAssembler().andThen(AnnotatedElementTreeAssembler::getRootAnnotation);
        }

        private Function<Configuration, AnnotatedElementTreeAssembler> instantiateAssembler() {
            return new Function<Configuration, AnnotatedElementTreeAssembler>() {
                @Override
                public AnnotatedElementTreeAssembler apply(Configuration configuration) {
                    List<Element> elements;
                    AnnotatedElementTreeAssembler.Configuration assemblerConfig;
                    Set<? extends Scorer<Element>> scorers;

                    Set<ElementScorerSetSupplier.Content> elementScorerSetContents;

                    switch (configuration){
                        case SIMPLE:

                            assemblerConfig = AnnotatedElementTreeAssembler.Configuration.MIXED_TREE;

                            elementScorerSetContents = new HashSet<>();
                            elementScorerSetContents.add(ElementScorerSetSupplier.Content.EMPHASIS_ELEMENT_SCORER);
                            elementScorerSetContents.add(ElementScorerSetSupplier.Content.SEGMENTATION_ELEMENT_SCORER);
                            break;
                        default:
                            throw new IllegalArgumentException(
                                    "The specified Configuration was not recognised."
                            );
                    }
                    scorers = new ElementScorerSetSupplier(elementScorerSetContents).get();
                    return new AnnotatedElementTreeAssembler()
                }
            };
        }

        private Function<Configuration, Triple<List<Element>, AnnotatedElementTreeAssembler.Configuration, Set<Scorer<Element>>>> generateAssemblerParamsFunction() {
            //// TODO: 21/01/2016 implement
            return null;
        }

        private Function<Triple<List<Element>, AnnotatedElementTreeAssembler.Configuration, Set<Scorer<Element>>>, AnnotatedElementTreeAssembler> constructAssemblerFunction() {
            return triple -> new AnnotatedElementTreeAssembler(triple.getLeft(), triple.getMiddle(), triple.getRight());
        }
    }
}
