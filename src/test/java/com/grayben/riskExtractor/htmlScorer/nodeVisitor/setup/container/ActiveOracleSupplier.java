package com.grayben.riskExtractor.htmlScorer.nodeVisitor.setup.container;

import com.grayben.riskExtractor.htmlScorer.ScoredText;
import com.grayben.riskExtractor.htmlScorer.ScoredTextElement;
import com.grayben.riskExtractor.htmlScorer.nodeVisitor.setup.annotation.AnnotatedElement;
import com.grayben.riskExtractor.htmlScorer.partScorers.Scorer;
import com.grayben.tools.testOracle.oracle.active.ActiveOracle;
import org.apache.commons.lang3.tuple.Pair;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.NodeTraversor;
import org.jsoup.select.NodeVisitor;

import java.util.Set;
import java.util.function.Supplier;

/**
 * Created by Ben Gray on 16/02/2016.
 */
public class ActiveOracleSupplier
        implements Supplier<ActiveOracle<Pair<Set<Scorer<Element>>, AnnotatedElement>, ScoredText>> {

    @Override
    public ActiveOracle<Pair<Set<Scorer<Element>>, AnnotatedElement>, ScoredText> get() {

        ScoredText scoredText = new ScoredText();

        NodeVisitor nodeVisitor = new NodeVisitor() {

            @Override
            public void head(Node node, int i) {
                if (isAnnotatedElement(node)) {
                    AnnotatedElement annotatedElement = (AnnotatedElement) node;
                    scoredText.add(
                            new ScoredTextElement(annotatedElement.ownText(), annotatedElement.getScores())
                    );
                }
            }

            @Override
            public void tail(Node node, int i) {
                isAnnotatedElement(node);
            }

            private boolean isAnnotatedElement(Node node) {
                return node.getClass().equals(AnnotatedElement.class);
            }
        };

        NodeTraversor nt = new NodeTraversor(nodeVisitor);
        return pair -> {
            nt.traverse(pair.getRight());
            return scoredText;
        };
    }


}
