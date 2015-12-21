package com.grayben.riskExtractor.htmlScorer.partScorers.elementScorers;

import com.grayben.riskExtractor.htmlScorer.partScorers.TagAndAttribute;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.mockito.Mockito;

import java.util.*;

/**
 * Created by beng on 21/12/2015.
 */
public final class TestHelper {

    private static Random random = new Random();

    private static String randomString(){
        return Integer.toString(random.nextInt());
    }

    private TestHelper(){}

    public static Element stubElement(Tag tag, Attributes attributes){
        Element element = Mockito.mock(Element.class);
        Mockito.when(element.tag())
                .thenReturn(tag);
        Mockito.when(element.attributes())
                .thenReturn(attributes);

        return element;
    }

    public static Tag stubTag(String tagName){
        Tag tag = Mockito.mock(Tag.class);
        Mockito.when(tag.isEmpty()).thenReturn(false);
        Mockito.when(tag.getName()).thenReturn("font");

        return tag;
    }

    public static Tag stubRandomTag(Set<Tag> notEqualTo){
        boolean unique = false;
        Tag tagStub = null;
        while ( ! unique ){
            String name = Integer.toString(random.nextInt());
            tagStub = stubTag(name);
            if (notEqualTo.contains(tagStub) == false)
                unique = true;
        }
        return tagStub;
    }

    public static Attribute stubAttribute(String key, String value){
        Attribute attribute = Mockito.mock(Attribute.class);
        Mockito.when(attribute.getKey()).thenReturn(key);
        Mockito.when(attribute.getValue()).thenReturn(value);

        return attribute;
    }

    public static Attribute stubRandomAttribute(Set<Attribute> notEqualToMocks){
        Attribute attributeStub = null;
        boolean unique = false;
        while ( ! unique ){
            attributeStub = stubAttribute(randomString(), randomString());
            if(notEqualToMocks.contains(attributeStub) == false)
                unique = true;
        }
        assert attributeStub != null;
        return attributeStub;
    }

    public static List<Attribute> stubAttributes(Map<String, String> mapping){
        List<Attribute> attributes = new ArrayList<>();
        for (Map.Entry<String, String> entry: mapping.entrySet()) {
            attributes.add(stubAttribute(entry.getKey(), entry.getValue()));
        }

        assert attributes.size() == mapping.size();
        return attributes;
    }

    public static List<Attribute> stubRandomAttributes
            (int amountToStub, Set<Attribute> notEqualToAnyOfTheseMocks){
        List<Attribute> attributes = new ArrayList<>();
        for (int i = 0; i < amountToStub; i++){
            attributes.add(stubRandomAttribute(notEqualToAnyOfTheseMocks))
        }
        return attributes;
    }

    public static Attributes convertListToAttributes(List<Attribute> attributeList){
        Attributes attributes = new Attributes();
        for (Attribute attribute :
                attributeList) {
            attributes.put(attribute);
        }
        return attributes;
    }

    public static Map<String, String> randomMap(int number){
        Map<String, String> map = new HashMap<>();
        Iterator<Integer> it = random.ints(number * 2).iterator();
        while(it.hasNext()){
            map.put(
                    it.next().toString(),
                    it.next().toString()
            );
        }
        assert map.size() == number;

        return map;
    }

    public static List<Element> mockRandomElements(int amountToMock, Set<Element> notEqualToAnyOf){
        List<Element> mocks = new ArrayList<>();

    }

    public static Element
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

    public static Map<Element, Integer>
    mockElementsAndScoresConformingToTagAndAttributeScoreMap
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

    public static Element mockElementConformingToTag(Tag tag){
        Element elementMock = Mockito.mock(Element.class);

        //the element returns the scored Tag
        Mockito.when(elementMock.tag()).thenReturn(tag);

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

    public static Map<Element, Integer>
    mockElementsAndScoresConformingToTagScoreMap
            (Map<Tag, Integer> scoreMap) {

        Map<Element, Integer> elementsAndScores = new HashMap<>();

        for (Map.Entry<Tag, Integer> entry :
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
