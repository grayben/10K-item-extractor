package com.grayben.testing;

/**
 * Created by beng on 8/01/2016.
 */
public abstract class SUTInputAndOutputOracle<SUT, I, O>
        implements
        SystemUnderTestRetrievable<SUT>,
        InputAndExpectedOutputRetrievable<I, O>
{

    private InputAndExpectedOutputRetrievable<I, O> ioGenerator;
    private SystemUnderTestRetrievable<SUT> sutGenerator;

    abstract protected void instantiateIoGenerator();
    abstract protected void instantiateSutGenerator();

    @Override
    final public I getInput() {
        if (ioGenerator == null) {
            instantiateIoGenerator();
        }
        return ioGenerator.getInput();
    }

    @Override
    final public O getExpectedOutput() {
        if (ioGenerator == null) {
            instantiateIoGenerator();
        }
        return ioGenerator.getExpectedOutput();
    }

    @Override
    final public SUT getSUT() {
        if (sutGenerator == null) {
            instantiateSutGenerator();
        }
        return sutGenerator.getSUT();
    }
}
