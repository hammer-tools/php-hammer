<?php

class Dummy
{
    const DUMMY = null;

    private Dummy $a;
    public Dummy|int|null $b;

    function dummy(Dummy $b): Dummy
    {
        return
            Dummy::DUMMY ||
            Dummy::DUMMY;
    }

    function dummyB(Dummy|int $a): Dummy|int|null
    {
        if ($a instanceof Dummy) {
        }

        return new Dummy;
    }
}

class DummyB extends Dummy
{
    function dummy(Dummy $b): DummyB
    {
        return DummyB::DUMMY;
    }
}

// Not applicable:

$dummy = Dummy::DUMMY;

class DummyC extends Dummy
{
    function dummy(Dummy $b)
    {
        return Dummy::DUMMY;
    }
}
