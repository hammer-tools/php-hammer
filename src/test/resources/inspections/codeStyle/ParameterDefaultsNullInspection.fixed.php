<?php

$dummy = function ($a, $b = null) {
    $b ??= 123;
};

$dummy = function ($a = null) {
    $a ??= 123;
};

abstract class Dummy1
{
    function dummy2($a, $b = null)
    {
        $b ??= 123;
    }

    function dummy3($a, $b = null)
    {
        $b ??= 123;
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
