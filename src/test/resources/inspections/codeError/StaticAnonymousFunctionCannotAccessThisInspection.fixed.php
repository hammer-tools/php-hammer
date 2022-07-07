<?php

class Dummy
{
    function dummy()
    {
        $dummy = function () {
            return $this;
        };

        $dummy = fn() => $this;
    }
}
