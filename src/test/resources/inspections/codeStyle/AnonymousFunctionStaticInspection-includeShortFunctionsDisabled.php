<?php

$dummy = fn() => true;

$dummy = fn() => fn() => true;

class DummyA
{
    function dummy()
    {
        $dummy = fn() => true;
    }
}

// Keep inspecting another cases:

$dummy = fn() => true;
