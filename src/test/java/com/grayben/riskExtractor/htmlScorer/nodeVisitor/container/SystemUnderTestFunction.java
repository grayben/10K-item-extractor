package com.grayben.riskExtractor.htmlScorer.nodeVisitor.container;

import com.grayben.riskExtractor.htmlScorer.ScoredText;
import com.grayben.tools.testOracle.SystemUnderTest;
import org.jsoup.nodes.Element;

import java.util.function.Function;

/**
 * Created by Ben Gray on 16/02/2016.
 */
public class SystemUnderTestFunction
        implements Function<SystemUnderTestFunction.SUTConfig, SystemUnderTest<Element, ScoredText>> {

    enum SUTConfig {
        DEFAULT
    }

    @Override
    public SystemUnderTest<Element, ScoredText> apply(SUTConfig sutConfig) {
        return null;
    }
}
