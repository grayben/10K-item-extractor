package com.grayben.riskExtractor.helpers;

/**
 * Created by beng on 8/12/2015.
 */
public enum TextElementClass {
    ELECTED_HEADING("ELECTED HEADING"),
    NOMINATED_HEADING("NOMINATED HEADING"),
    NON_HEADING_CONTENT("non-heading content");

    private final String archetype;

    TextElementClass(String archetype){
        this.archetype = archetype;
    }

    public String archetype(){
        return this.archetype;
    }
}
