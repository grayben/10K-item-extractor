package com.grayben.testing;

/**
 * Created by beng on 7/01/2016.
 */
public interface InputAndExpectedOutputRetrievable<I, O> {

    I getInput();

    O getExpectedOutput();
}