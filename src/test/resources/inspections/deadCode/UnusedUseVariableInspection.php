<?php

$dummy = function() use (<weak_warning descr="[PHP Hammer] Unused variable declared in use().">$a</weak_warning>, $b, <weak_warning descr="[PHP Hammer] Unused variable declared in use().">$c</weak_warning>) {
    return $b;
};

$dummy = function() use (<weak_warning descr="[PHP Hammer] Unused variable declared in use().">&$a</weak_warning>, &$b, <weak_warning descr="[PHP Hammer] Unused variable declared in use().">&$c</weak_warning>) {
    return $b;
};

$dummy = function() use (<weak_warning descr="[PHP Hammer] Unused variable declared in use().">$any</weak_warning>) {
};
