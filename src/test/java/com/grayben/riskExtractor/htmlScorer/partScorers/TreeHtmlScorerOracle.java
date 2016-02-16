package com.grayben.riskExtractor.htmlScorer.partScorers;

/**
 * Created by beng on 6/01/2016.
 */
public class TreeHtmlScorerOracle {}
/*
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

            return instantiateAssembler().andThen(TreeAssembler::getRootAnnotation);
        }

        private Function<Configuration, TreeAssembler> instantiateAssembler() {
            Function<Configuration, List<? extends Object>> initParams;

            Function<List<? extends Object>, TreeAssembler> instantiate
                    = objects -> {
                ListIterator<? extends Object> it = objects.listIterator();
                List<Element> elementList;
                TreeAssembler.Configuration assemblerConfig;
                Set<Scorer<Element>> scorers;

                elementList = ((List<Element>) it.next());
                assemblerConfig = ((TreeAssembler.Configuration) it.next());
                scorers = ((Set<Scorer<Element>>) it.next());

                return new TreeAssembler(elementList, assemblerConfig, scorers);
            };
            throw new UnsupportedOperationException();
            //return instantiateAssemblerFunction().andThen();
        }

        //TODO: consider moving to TreeAssembler.Factory
        private Function<Configuration, Triple<List<Element>, TreeAssembler.Configuration, Set<Scorer<Element>>>> generateAssemblerParamsFunction() {
            //// TODO: 21/01/2016 implement
            return null;
        }

        //TODO: consider moving into TreeAssembler itself
        private Function<Triple<List<Element>, TreeAssembler.Configuration, Set<Scorer<Element>>>, TreeAssembler> constructAssemblerFunction() {
            return triple -> new TreeAssembler(triple.getLeft(), triple.getMiddle(), triple.getRight());
        }
    }
}
*/