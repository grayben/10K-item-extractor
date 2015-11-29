package com.grayben.riskExtractor.htmlScorer;

import org.jsoup.nodes.Attribute;
import org.jsoup.parser.Tag;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

/**
 * Created by beng on 28/11/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class TagAndAttributeTest {

    TagAndAttribute tagAndAttributeSUT;

    @Mock
    Tag tagMock;

    @Mock
    Attribute attributeMock;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        tagAndAttributeSUT = new TagAndAttribute(tagMock, attributeMock);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void test_Initialise_ThrowsIllegalArgumentException_WhenTagArgumentIsNull(){
        thrown.expect(IllegalArgumentException.class);
        tagAndAttributeSUT = new TagAndAttribute(null, attributeMock);
    }

    @Test
    public void test_Initialise_ThrowsIllegalArgumentException_WhenAttributeArgumentIsNull(){
        thrown.expect(IllegalArgumentException.class);
        tagAndAttributeSUT = new TagAndAttribute(tagMock, null);
    }

    @Test
    public void test_GetTag_IsNotNull_Always(){
        Tag tagReturned = tagAndAttributeSUT.getTag();
        assertNotNull(tagReturned);
    }

    @Test
    public void test_GetAttribute_IsNotNull_Always(){
        Attribute attributeReturned = tagAndAttributeSUT.getAttribute();
        assertNotNull(attributeReturned);
    }
}