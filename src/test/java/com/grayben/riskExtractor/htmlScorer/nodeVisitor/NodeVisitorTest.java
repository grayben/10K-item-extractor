package com.grayben.riskExtractor.htmlScorer.nodeVisitor;

import org.jsoup.select.NodeVisitor;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.fail;

/**
 * Created by beng on 16/12/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public abstract class NodeVisitorTest {

    private NodeVisitor nodeVisitorSUT;

    protected NodeVisitor getNodeVisitorSUT() {
        return nodeVisitorSUT;
    }

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

    @Ignore
    @Test
    public void
    test_HeadThrowsNullPointerException_WhenNodeIsNull
            () throws Exception {
        fail("Test not implemented");
    }

    @Ignore
    @Test
    public void
    test_HeadThrowsIllegalArgumentException_WhenDepthBelowZero
            () throws Exception {
        fail("Test not implemented");
    }

    @Ignore
    @Test
    public void
    test_TailThrowsNullPointerException_WhenNodeIsNull
            () throws Exception {
        fail("Test not implemented");
    }

    @Ignore
    @Test
    public void
    test_TailThrowsIllegalArgumentException_WhenDepthBelowZero
            () throws Exception {
        fail("Test not implemented");
    }
}
