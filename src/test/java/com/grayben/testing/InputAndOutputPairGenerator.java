package com.grayben.testing;

import java.util.function.Function;

/**
 * Created by beng on 7/01/2016.
 */
public class InputAndOutputPairGenerator<S, I, O> {

    private S seed;
    private Function<S, I> inputGenerator;
    private Function<S, O> outputGenerator;

    public InputAndOutputPairGenerator(S seed){
        this.seed = seed;
    }

    public I generateInput(){
        return inputGenerator.apply(seed);
    }

    public O generateOutput(){
        return outputGenerator.apply(seed);
    }
}