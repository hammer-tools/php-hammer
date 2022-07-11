<?php

class Dummy
{
    const DUMMY = null;

    function dummy()
    {
        return
            <weak_warning descr="[PHP Hammer] Class reference format must be \"Dummy::class\".">self</weak_warning>::DUMMY ||
            Dummy::DUMMY;
    }
}

// Not applicable:

$dummy = Dummy::DUMMY;
