<?php

$dummy = function ($a, $b, $c) {
    return compact('a', 'b', <warning descr="[PHP Hammer] Variables should be avoided in compact().">$c</warning>);
};

$dummy = function ($a, $b, string|null $c) {
    return compact('a', 'b', <warning descr="[PHP Hammer] Variables should be avoided in compact().">$c</warning>);
};

$dummy = function ($a, $b, string $c) {
    return compact('a', 'b', <warning descr="[PHP Hammer] Variables should be avoided in compact().">$c</warning>);
};

$dummy = function ($a, $b, mixed $c) {
    return compact('a', 'b', <warning descr="[PHP Hammer] Variables should be avoided in compact().">$c</warning>);
};

$dummy = function ($a, $b, $c) {
    return compact('a', ['b', <warning descr="[PHP Hammer] Variables should be avoided in compact().">$c</warning>]);
};

$dummy = function ($a, $b, string|null $c) {
    return compact('a', ['b', <warning descr="[PHP Hammer] Variables should be avoided in compact().">$c</warning>]);
};

$dummy = function ($a, $b, string $c) {
    return compact('a', ['b', <warning descr="[PHP Hammer] Variables should be avoided in compact().">$c</warning>]);
};

$dummy = function ($a, $b, mixed $c) {
    return compact('a', ['b', <warning descr="[PHP Hammer] Variables should be avoided in compact().">$c</warning>]);
};

$dummy = function ($a, $b, mixed $c) {
    return compact('a', ['b', 1 => <warning descr="[PHP Hammer] Variables should be avoided in compact().">$c</warning>]);
};

$dummy = function ($a, $b, array $c) {
    return compact('a', 'b', <warning descr="[PHP Hammer] Variables should be avoided in compact().">$c</warning>);
};

// Not applicable:

$dummy = function ($a, $b, $c) {
    return compact('a', 'b', 'c');
};

$dummy = function ($a, $b, $c) {
    return compact(['a'], ['b', 'c']);
};
