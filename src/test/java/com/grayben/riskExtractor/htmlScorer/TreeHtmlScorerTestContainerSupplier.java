package com.grayben.riskExtractor.htmlScorer;

import com.grayben.riskExtractor.htmlScorer.partScorers.Scorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.elementScorers.ElementScorerSetFunction;
import com.grayben.tools.testOracle.SystemUnderTest;
import com.grayben.tools.testOracle.oracle.active.ActiveOracle;
import com.grayben.tools.testOracle.testContainer.TestContainer;
import com.sun.source.tree.Tree;
import org.jsoup.nodes.Element;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Created by Ben Gray on 19/02/2016.
 */
public class TreeHtmlScorerTestContainerSupplier implements Supplier<TestContainer<File, ScoredText>> {
    @Override
    public TestContainer<File, ScoredText> get() {
        return new TestContainer.Builder<File, ScoredText>()
                .begin()
                .systemUnderTest(systemUnderTest())
                .oracle(activeOracle())
                .build();
    }

    private ActiveOracle<? super File, ? extends ScoredText> activeOracle() {
        return new ActiveOracle<File, ScoredText>() {
            @Override
            public ScoredText apply(File input) {
                Set<ElementScorerSetFunction.Content> contents = new HashSet<>();
                contents.add(ElementScorerSetFunction.Content.EMPHASIS_ELEMENT_SCORER);
                contents.add(ElementScorerSetFunction.Content.SEGMENTATION_ELEMENT_SCORER);
                Set<Scorer<Element>> elementScorerSet = new ElementScorerSetFunction().apply(contents);
                //TODO: share nv setup with both test suites
                ScoringAndFlatteningNodeVisitor nv = new ScoringAndFlatteningNodeVisitor(elementScorerSet);
                TreeHtmlScorer htmlScorer = new TreeHtmlScorer(nv);
                htmlScorer.scoreHtml(input, ???)
            }
        };
    }

    private SystemUnderTest<? super File, ? extends ScoredText> systemUnderTest() {
        TreeHtmlScorer treeHtmlScorer = new TreeHtmlScorer()
        return null;
    }
}
