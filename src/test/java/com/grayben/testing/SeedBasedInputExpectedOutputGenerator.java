package com.grayben.testing;

/**
 * Created by beng on 7/01/2016.
 */
public abstract class SeedBasedInputExpectedOutputGenerator<S, I, O>
        implements InputAndExpectedOutputRetrievable<I, O> {

    I input;
    O expectedOutput;

    SeedBasedInputExpectedOutputGenerator(S seed){
        validateInitParams(seed);
        this.input = generateInput(seed);
        this.expectedOutput = generateExpectedOutput(seed);
    }

    abstract I generateInput(S seed);

    abstract O generateExpectedOutput(S seed);

    abstract void validateInitParams(S seed);

    @Override
    public I getInput() {
        return this.input;
    }

    @Override
    public O getExpectedOutput() {
        return this.expectedOutput;
    }
}
