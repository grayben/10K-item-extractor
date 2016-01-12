package com.grayben.riskExtractor.htmlScorer.nodeVisitor;

import org.jsoup.nodes.Comment;
import org.jsoup.nodes.Node;
import org.jsoup.select.NodeVisitor;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by beng on 16/12/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public abstract class NodeVisitorTest {

    private NodeVisitor nodeVisitorSUT;

    protected void setNodeVisitorSUT(NodeVisitor nodeVisitorSUT) {
        this.nodeVisitorSUT = nodeVisitorSUT;
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void
    test_HeadThrowsNullPointerException_WhenNodeIsNull
            () throws Exception {
        thrown.expect(NullPointerException.class);

        Node nullNode = null;

        nodeVisitorSUT.head(nullNode, 1);
    }

    @Test
    public void
    test_HeadThrowsIllegalArgumentException_WhenDepthBelowZero
            () throws Exception {
        thrown.expect(IllegalArgumentException.class);

        Node node = new Comment("a comment here", "a base URI there");

        int depthBelowZero = -1;

        nodeVisitorSUT.head(node, depthBelowZero);
    }

    @Test
    public void
    test_TailThrowsNullPointerException_WhenNodeIsNull
            () throws Exception {
        thrown.expect(NullPointerException.class);

        Node nullNode = null;

        nodeVisitorSUT.tail(nullNode, 1);
    }

    @Test
    public void
    test_TailThrowsIllegalArgumentException_WhenDepthBelowZero
            () throws Exception {
        thrown.expect(IllegalArgumentException.class);

        Node node = new Comment("a comment here", "a base URI there");

        int depthBelowZero = -1;

        nodeVisitorSUT.tail(node, depthBelowZero);
    }
}
