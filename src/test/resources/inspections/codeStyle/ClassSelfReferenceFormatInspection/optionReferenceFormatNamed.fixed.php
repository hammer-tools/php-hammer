<?php

class Dummy
{
    const DUMMY = null;

    function dummy()
    {
        return
            Dummy::DUMMY ||
            Dummy::DUMMY;
    }
}

class DummyB extends Dummy
{
    function dummy()
    {
        return DummyB::DUMMY;
    }
}

// Not applicable:

$dummy = Dummy::DUMMY;

class DummyC extends Dummy
{
    function dummy()
    {
        return Dummy::DUMMY;
    }
}
