package com.grayben.riskExtractor.headingMarker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by beng on 1/12/2015.
 */
public class UnmodifiableText {

    private ArrayList<String> stringList;

    public UnmodifiableText(List<String> stringList){
        if(stringList == null)
            throw new NullPointerException("Attempted " +
                    "to construct with null pointer");
        this.stringList = new ArrayList<>(stringList);
    }

    public UnmodifiableText(UnmodifiableText unmodifiableText){
        this(unmodifiableText.getStringList());

    }

    public List<String> getStringList(){
        return Collections.unmodifiableList(this.stringList);
    }
}
