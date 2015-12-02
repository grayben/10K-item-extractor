package com.grayben.riskExtractor.headingMarker;

import java.util.Collections;
import java.util.List;

/**
 * Created by beng on 1/12/2015.
 */
public class UnmodifiableText {

    private List<String> stringList;

    public UnmodifiableText(List<String> stringList){
        if(stringList == null)
            throw new NullPointerException("Attempted " +
                    "to construct with null pointer");
        this.stringList = stringList;
    }

    public UnmodifiableText(UnmodifiableText unmodifiableText){
        this(unmodifiableText.getStringList());

    }

    public List<String> getStringList(){
        return Collections.unmodifiableList(this.stringList);
    }
}
