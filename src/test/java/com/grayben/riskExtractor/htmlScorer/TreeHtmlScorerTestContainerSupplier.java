package com.grayben.riskExtractor.htmlScorer;

import com.grayben.tools.testOracle.testContainer.TestContainer;

import java.io.File;
import java.util.function.Supplier;

/**
 * Created by Ben Gray on 19/02/2016.
 */
public class TreeHtmlScorerTestContainerSupplier implements Supplier<TestContainer<File, ScoredText>> {
    @Override
    public TestContainer<File, ScoredText> get() {
        return null;
    }
}
