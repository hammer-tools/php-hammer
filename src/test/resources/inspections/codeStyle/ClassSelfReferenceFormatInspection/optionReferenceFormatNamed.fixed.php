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

// Not applicable:

$dummy = Dummy::DUMMY;
