<?php

class Dummy
{
    const DUMMY = null;

    function dummy()
    {
        return
            self::DUMMY ||
            self::DUMMY;
    }
}

// Not applicable:

$dummy = Dummy::DUMMY;

class DummyB extends Dummy
{
    function dummy()
    {
        return
            self::DUMMY ||
            Dummy::DUMMY;
    }
}
