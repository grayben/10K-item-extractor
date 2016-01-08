package com.grayben.testing;

/**
 * Created by beng on 8/01/2016.
 */
public class SUTInputAndOutputOracle<SUT, I, O>
        implements
        SystemUnderTestRetrievable<SUT>,
        InputAndExpectedOutputRetrievable<I, O>
{

    InputAndExpectedOutputRetrievable<I, O> ioGenerator;
    SystemUnderTestRetrievable<SUT> sutGenerator;

    @Override
    final public I getInput() {
        return ioGenerator.getInput();
    }

    @Override
    final public O getExpectedOutput() {
        return ioGenerator.getExpectedOutput();
    }

    @Override
    final public SUT getSUT() {
        return sutGenerator.getSUT();
    }
}
