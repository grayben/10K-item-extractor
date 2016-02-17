package com.grayben.riskExtractor.htmlScorer.nodeVisitor.setup;

import com.grayben.riskExtractor.htmlScorer.partScorers.Scorer;
import org.jsoup.nodes.Element;

import java.util.*;

/**
 * Created by Ben Gray on 12/02/2016.
 */
class SetupHelpers {

    private SetupHelpers(){}

    static AnnotatedElement configureAnnotatedElement(){

        Set<Scorer<Element>> elementScorers = new ElementScorersSupplier().get();

        return new AnnotatedElement.TreeAssembler(
                new ElementListFunction().apply(elementScorers),
                elementScorers
        ).getRootAnnotation();
    }
}
