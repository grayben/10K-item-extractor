package com.grayben.riskExtractor.htmlScorer.nodeVisitor.container;

import com.grayben.riskExtractor.htmlScorer.nodeVisitor.AnnotatedElement;
import com.grayben.riskExtractor.htmlScorer.nodeVisitor.AnnotatedElementTreeAssembler;
import com.grayben.riskExtractor.htmlScorer.nodeVisitor.UtilsThatShouldBeRefactored;
import com.grayben.riskExtractor.htmlScorer.partScorers.Scorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.elementScorers.ElementScorerSetSupplier;
import org.jsoup.nodes.Element;

import java.util.*;

/**
 * Created by Ben Gray on 12/02/2016.
 */
class TestContainerSetupHelpers {

    private TestContainerSetupHelpers(){}

    public enum NewConfig {
        DEFAULT(AnnotatedElementTreeAssembler.Configuration.MIXED_TREE);

        private final AnnotatedElementTreeAssembler.Configuration treeAssemblerConfiguration;

        NewConfig(AnnotatedElementTreeAssembler.Configuration configuration) {
            this.treeAssemblerConfiguration = configuration;
        }

        public AnnotatedElementTreeAssembler.Configuration getTreeAssemblerConfiguration() {
            return treeAssemblerConfiguration;
        }
    }

    static Set<Scorer<Element>> configureElementScorerSet(NewConfig config) {
        Set<ElementScorerSetSupplier.Content> contents = new HashSet<>();

        switch (config) {
            case DEFAULT:
                contents.add(ElementScorerSetSupplier.Content.SEGMENTATION_ELEMENT_SCORER);
                contents.add(ElementScorerSetSupplier.Content.EMPHASIS_ELEMENT_SCORER);
                return new ElementScorerSetSupplier(contents).get();
            default:
                throw new IllegalArgumentException("The input option was not recognised");
        }
    }

    static List<Element> configureElementList(NewConfig config, Set<Scorer<Element>> scorers){
        List<Element> targetElements;
        switch (config.getTreeAssemblerConfiguration()) {
            case MIXED_TREE:
                targetElements = new ArrayList<>();
                targetElements.addAll(UtilsThatShouldBeRefactored.getEmphasisedTargetElementsAndScores(scorers).keySet());
                targetElements.addAll(UtilsThatShouldBeRefactored.getSegmentedTargetElementsAndScores(scorers).keySet());
                targetElements.addAll(UtilsThatShouldBeRefactored.generateAndScoreRandomElements(scorers, 100).keySet());
                for (Element element : targetElements) {
                    element.text(UtilsThatShouldBeRefactored.randomString());
                }
                break;
            default:
                throw new IllegalArgumentException("The input option was not recognised");
        }
        Collections.shuffle(targetElements, new Random());
        return targetElements;
    }

    static AnnotatedElement configureAnnotatedElement(NewConfig config){

        Set<Scorer<Element>> elementScorers = configureElementScorerSet(config);

        return new AnnotatedElementTreeAssembler(
                configureElementList(config, elementScorers),
                config.getTreeAssemblerConfiguration(),
                elementScorers
        ).getRootAnnotation();
    }
}
