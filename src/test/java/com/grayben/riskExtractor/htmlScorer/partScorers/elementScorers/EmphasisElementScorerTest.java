package com.grayben.riskExtractor.htmlScorer.partScorers.elementScorers;

import com.grayben.riskExtractor.htmlScorer.partScorers.ScorerTest;
import com.grayben.riskExtractor.htmlScorer.partScorers.TagAndAttribute;
import com.grayben.riskExtractor.htmlScorer.partScorers.tagScorers.TagAndAttributeScorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.tagScorers.TagEmphasisScorer;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;

import static junit.framework.Assert.assertNotNull;

/**
 * Created by beng on 28/11/2015.
 */

@RunWith(MockitoJUnitRunner.class)
public class EmphasisElementScorerTest extends ScorerTest<Element> {

    EmphasisElementScorer emphasisElementScorerSUT;

    @Mock
    public Element elementToBeScoredMock;

    @Before
    public void setUp() throws Exception {
        this.emphasisElementScorerSUT = new EmphasisElementScorer();
        super.setScorerSUT(this.emphasisElementScorerSUT);
        super.setArgumentToBeScoredMock(elementToBeScoredMock);
        super.setUp();
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    private void stubElementToBeScoredMock(Tag tag, Attributes attributes){
        Mockito.when(elementToBeScoredMock.tag())
                .thenReturn(tag);
        Mockito.when(elementToBeScoredMock.attributes())
                .thenReturn(attributes);
    }

    private Tag stubTag(String tagName){
        Tag tag = Mockito.mock(Tag.class);
        Mockito.when(tag.isEmpty()).thenReturn(false);
        Mockito.when(tag.getName()).thenReturn("font");

        return tag;
    }

    private Attribute stubAttribute(String key, String value){
        Attribute attribute = Mockito.mock(Attribute.class);
        Mockito.when(attribute.getKey()).thenReturn(key);
        Mockito.when(attribute.getValue()).thenReturn(value);

        return attribute;
    }

    private List<Attribute> stubAttributes(Map<String, String> mapping){
        List<Attribute> attributes = new ArrayList<>();
        for (Map.Entry<String, String> entry: mapping.entrySet()) {
            attributes.add(stubAttribute(entry.getKey(), entry.getValue()));
        }

        assert attributes.size() == mapping.size();
        return attributes;
    }

    private Attributes convertListToAttributes(List<Attribute> attributeList){
        Attributes attributes = new Attributes();
        for (Attribute attribute :
                attributeList) {
            attributes.put(attribute);
        }
        return attributes;
    }

    private Map<String, String> randomMap(int number){
        Map<String, String> map = new HashMap<>();
        Random rand = new Random();
        Iterator<Integer> it = rand.ints(number * 2).iterator();
        while(it.hasNext()){
            map.put(
                    it.next().toString(),
                    it.next().toString()
            );
        }
        assert map.size() == number;

        return map;
    }

    @Override
    @Test
    public void
    test_ScoreReturnsInteger_WhenArgumentIsNotEmpty() throws Exception {
        Tag tag = stubTag("font");
        Attributes attributes
                = convertListToAttributes(
                stubAttributes(
                        randomMap(5)
                )
        );

        stubElementToBeScoredMock(tag, attributes);

        Integer returned = emphasisElementScorerSUT.score(elementToBeScoredMock);

        assertNotNull(returned);
    }

    @Test
    public void
    test_ScoreThrowsNullPointerException_WhenTagIsNull() throws Exception {
        thrown.expect(NullPointerException.class);

        Attributes attributes
                = convertListToAttributes(
                stubAttributes(
                        randomMap(5)
                )
        );

        stubElementToBeScoredMock(null, attributes);
    }

    @Test
    public void
    test_ScoreThrowsNullPointerException_WhenAttributesIsNull() throws Exception {
        thrown.expect(NullPointerException.class);

        stubElementToBeScoredMock(stubTag("font"), null);
    }

    @Test
    public void
    test_ScoreThrowsIllegalArgumentException_WhenTagIsEmpty() throws Exception {
        Tag tagStub = stubTag("");
        Mockito.when(tagStub.isEmpty()).thenReturn(true);

        Attributes attributes
                = convertListToAttributes(
                stubAttributes(
                        randomMap(5)
                )
        );

        stubElementToBeScoredMock(tagStub, attributes);

        thrown.expect(IllegalArgumentException.class);

        emphasisElementScorerSUT.score(elementToBeScoredMock);
    }

    @Override
    @Test
    public void
    test_ScoreGivesExpectedResult_WhenSimpleInput() throws Exception {

        // GENERATE OUTPUT ////////////////////////////////////////
        Map<Element, Integer> expectedOutput = new HashMap<>();

        Map<TagAndAttribute, Integer> tagAndAttributeScoreMap
                = TagAndAttributeScorer.defaultMap();

        //add Map.Entry<Element, Integer> entries to expectedOutput
        //based on the tagAndAttributeScoreMap
        Map<Element, Integer> scoredMapBasedOnTagAndAttributes
                = mockElementsAndScoredConformingToTagAndAttributeScoreMap(
                tagAndAttributeScoreMap
        );


        Map<Tag, Integer> tagEmphasisScoreMap
                = TagEmphasisScorer.defaultMap();

        //add Map.Entry<Element, Integer> entries to expectedOutput
        //based on the tagEmphasisScoreMap
        Map<Element, Integer> scoresMapBasedOnTags
                = mockElementsAndScoresConformingToTagScoreMap(
                tagEmphasisScoreMap
        );

        //combine the two maps
        expectedOutput.putAll(scoresMapBasedOnTags);
        expectedOutput.putAll(scoredMapBasedOnTagAndAttributes);
        assert expectedOutput.size() ==
                tagAndAttributeScoreMap.size() + tagEmphasisScoreMap.size();
        ///////////////////////////////////////////////////////////

        //TODO: define input
    }

    private Element
    mockElementConformingToTagAndAttribute(TagAndAttribute tagAndAttribute){
        Element elementMock = Mockito.mock(Element.class);

        //the element returns the scored TagAndAttribute.getTag()
        Mockito.when(elementMock.tag()).thenReturn(tagAndAttribute.getTag());

        //generate the target attribute from the scored key
        Attribute targetAttributeMock = stubAttribute(
                tagAndAttribute.getAttribute().getKey(),
                tagAndAttribute.getAttribute().getValue()
        );

        //Stub the Attributes randomly - these ones don't matter
        Attributes attributeMocks = convertListToAttributes(
                stubAttributes(randomMap(5))
        );

        //but then add the target attribute
        attributeMocks.put(targetAttributeMock);

        //the element returns the attributes
        // containing TagAndAttribute.getAttribute();
        Mockito.when(elementMock.attributes()).thenReturn(attributeMocks);

        return elementMock;
    }

    private Map<Element, Integer>
            mockElementsAndScoredConformingToTagAndAttributeScoreMap
            (Map<TagAndAttribute, Integer> scoreMap){
        Map<Element, Integer> elementsAndScores = new HashMap<>();

        for (Map.Entry<TagAndAttribute, Integer> entry : scoreMap.entrySet()){
            Element elementMock = mockElementConformingToTagAndAttribute(
                    entry.getKey()
            );

            //expect the element mock to be scored
            //according to the tagAndAttribute in the map
            elementsAndScores.put(elementMock, entry.getValue());
        }

        assert elementsAndScores.size() == scoreMap.size();

        return elementsAndScores;
    }

    private Element mockElementConformingToTag(Tag tag){
        Element elementMock = Mockito.mock(Element.class);

        //the element returns the scored Tag
        Mockito.when(elementMock.tag()).thenReturn(entry.getKey());

        //add some dummy attributes to the element
        //(they shouldn't matter, since only the tag is important)
        Attributes attributes = convertListToAttributes(
                stubAttributes(
                        randomMap(2)
                )
        );
        Mockito.when(elementMock.attributes()).thenReturn(attributes);

        return elementMock;
    }

    private Map<Element, Integer>
            mockElementsAndScoresConformingToTagScoreMap
            (Map<Tag, Integer> scoreMap){

        Map<Element, Integer> elementsAndScores = new HashMap<>();

        for (Map.Entry<Tag, Integer> entry:
                scoreMap.entrySet()) {

            Element elementMock = mockElementConformingToTag(entry.getKey());

            // expect the element mock to be scored
            // according to the tag in the map
            elementsAndScores.put(elementMock, entry.getValue());
        }

        assert elementsAndScores.size() == scoreMap.size();

        return elementsAndScores;
    }




}