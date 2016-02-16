package com.grayben.riskExtractor.htmlScorer.nodeVisitor.container;

import com.grayben.riskExtractor.htmlScorer.ScoredText;
import com.grayben.tools.testOracle.SystemUnderTest;
import com.grayben.tools.testOracle.oracle.passive.PassiveOracle;
import com.grayben.tools.testOracle.testContainer.TestContainer;
import org.jsoup.nodes.Element;

import java.util.function.Function;

/**
 * Created by Ben Gray on 12/02/2016.
 */
public class TestContainerFunction implements Function<TestContainerFunction.Config, TestContainer<Element, ScoredText>> {

    enum Config {
        DEFAULT(SetupHelpers.NewConfig.DEFAULT);

        private final SetupHelpers.NewConfig testContainerSetupHelpersConfig;

        Config(SetupHelpers.NewConfig config) {
            this.testContainerSetupHelpersConfig = config;
        }

        public SetupHelpers.NewConfig getTestContainerSetupHelpersConfig() {
            return testContainerSetupHelpersConfig;
        }
    }

    @Override
    public TestContainer<Element, ScoredText> apply(Config config) {
        SystemUnderTest<Element, ScoredText> sut = null;
        PassiveOracle<Element, ScoredText> passiveOracle = null;
        return new TestContainer.Builder<Element, ScoredText>()
                .begin()
                .systemUnderTest(sut)
                .oracle(passiveOracle)
                .build();
    }

}
