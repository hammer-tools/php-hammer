<?php

$dummy = function ($a, $b = null) {
    $b = $b === null ? 123 : $b;
};

$dummy = function ($a = null) {
    $a = $a === null ? 123 : $a;
};

abstract class Dummy1
{
    function dummy2($a, $b = null)
    {
        $b = $b === null ? 123 : $b;
    }

    function dummy3($a, $b = null)
    {
        $b = $b === null ? 123 : $b;
        return $b;
    }
}

// Not applicable for quick-fix only:

$dummy = function (&$a = 123) {
};

$dummy = function (int &$a = 123) {
};

abstract class Dummy2
{
    abstract function dummy1($a, $b = 123);
}

// Not applicable:

$dummy = function ($a) {
};

$dummy = function ($a = null) {
};
