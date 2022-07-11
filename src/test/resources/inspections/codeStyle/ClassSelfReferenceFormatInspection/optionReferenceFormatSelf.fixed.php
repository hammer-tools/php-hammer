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
