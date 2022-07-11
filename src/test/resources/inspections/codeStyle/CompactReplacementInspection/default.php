<?php

$dummy = function () {
    $x = 1;
    $y = 2;
    $z = 3;

    $a = <weak_warning descr="[PHP Hammer] Array can be replaced by compact().">['x' => $x]</weak_warning>;
    $b = [];
    $c = ['y'];

    ['x'=>$x] = [];

    return <weak_warning descr="[PHP Hammer] Array can be replaced by compact().">[
        'y' => $y,
        ... <weak_warning descr="[PHP Hammer] Array can be replaced by compact().">['z' => $z]</weak_warning>,
        'x' => $x,
        ... compact('a', "b"),
    ]</weak_warning>;
};
