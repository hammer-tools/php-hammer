<?php

$dummy = (string)$dummy;
$dummy = (string)(new Dummy);
$dummy = (string)$dummy->call();
$dummy = (string)Dummy::call();
$dummy = (string)call();

// Not applicable:

class Dummy
{
    function __toString()
    {
        return parent::__toString();
    }
}
