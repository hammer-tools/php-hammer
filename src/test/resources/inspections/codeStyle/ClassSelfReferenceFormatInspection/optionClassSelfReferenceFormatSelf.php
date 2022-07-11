<?php

class Dummy
{
    const DUMMY = null;

    function dummy()
    {
        return
            self::DUMMY ||
            <weak_warning descr="[PHP Hammer] Class reference format must be \"self\".">Dummy</weak_warning>::DUMMY;
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
