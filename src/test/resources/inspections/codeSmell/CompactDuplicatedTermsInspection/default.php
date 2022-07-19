<?php

$dummy = function ($a, $b) {
    return compact('a', <weak_warning descr="[PHP Hammer] Duplicated term in compact().">'a'</weak_warning>, 'b');
};

$dummy = function ($a, $b) {
    return compact('a', [<weak_warning descr="[PHP Hammer] Duplicated term in compact().">'a'</weak_warning>, 'b']);
};

$dummy = function ($a, $b) {
    return compact(['a', <weak_warning descr="[PHP Hammer] Duplicated term in compact().">'a'</weak_warning>], 'b');
};

$dummy = function ($a, $b) {
    return compact(['a'], <weak_warning descr="[PHP Hammer] Duplicated term in compact().">'a'</weak_warning>, 'b');
};

$dummy = function ($a, $b) {
    return compact('a', [<weak_warning descr="[PHP Hammer] Duplicated term in compact().">'a'</weak_warning>], 'b');
};

$dummy = function ($a, $b) {
    return compact('a', ['b', <weak_warning descr="[PHP Hammer] Duplicated term in compact().">'a'</weak_warning>]);
};

// Not applicable:

$dummy = function ($a, $b) {
    return compact('a', 'b');
};

$dummy = function ($a, $b) {
    return compact(['a'], ['b']);
};

$dummy = function ($a, $b) {
    return compact(['a', 'b']);
};
