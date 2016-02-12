package com.grayben.riskExtractor.htmlScorer.nodeVisitor;

import com.grayben.riskExtractor.htmlScorer.ScoredText;
import com.grayben.tools.testOracle.testContainer.TestContainer;
import org.jsoup.nodes.Element;

import java.util.function.Function;

/**
 * Created by Ben Gray on 12/02/2016.
 */
public class TestContainerFunction implements Function<TestContainerFunction.Config, TestContainer<Element, ScoredText>> {

    public enum Config {
        DEFAULT
    }

    @Override
    public TestContainer<Element, ScoredText> apply(Config config) {
        return null;
    }

    class TestContainerSetupHelpers {
        
    }
}
