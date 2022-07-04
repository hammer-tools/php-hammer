<?php

$dummy = function() use (<warning descr="[PHP Hammer] Unused variable declared in use().">$a</warning>, $b, <warning descr="[PHP Hammer] Unused variable declared in use().">$c</warning>) {
    return $b;
};

$dummy = function() use (<warning descr="[PHP Hammer] Unused variable declared in use().">&$a</warning>, &$b, <warning descr="[PHP Hammer] Unused variable declared in use().">&$c</warning>) {
    return $b;
};

$dummy = function() use (<warning descr="[PHP Hammer] Unused variable declared in use().">$any</warning>) {
};
