<?php

class Dummy
{
    const DUMMY = null;

    function dummy()
    {
        return
            <weak_warning descr="[PHP Hammer] Class reference format must be \"Dummy\".">self</weak_warning>::DUMMY ||
            Dummy::DUMMY;
    }
}

class DummyB extends Dummy
{
    function dummy()
    {
        return <weak_warning descr="[PHP Hammer] Class reference format must be \"DummyB\".">self</weak_warning>::DUMMY;
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
