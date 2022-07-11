<?php

class Dummy
{
    const DUMMY = null;

    private self $a;
    public self|int|null $b;

    function dummy(self $b): self
    {
        return
            self::DUMMY ||
            self::DUMMY;
    }

    function dummyB(self|int $a): self|int|null
    {
        if ($a instanceof self) {
        }

        return new self;
    }
}

// Not applicable:

$dummy = Dummy::DUMMY;

class DummyB extends Dummy
{
    function dummy(Dummy $b)
    {
        return
            self::DUMMY ||
            Dummy::DUMMY;
    }
}
