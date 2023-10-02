<?php

$dummy = function (<weak_warning descr="🔨 PHP Hammer: parameter type is implicitly null.">int $a = null</weak_warning>) {
};

$dummy = function (int|string $a = null) { // not applicable
};

/**
 * @method dummyA(<weak_warning descr="🔨 PHP Hammer: parameter type is implicitly null.">int $a = null</weak_warning>)
 * @method dummyB(int | string $a = null) not applicable
 */
class Dummy {
}

$dummy = function (A&B $a = <error descr="Argument passed must be of the type \B&\A, null given">null</error>) {
};

$dummy = function ((A&B) $a = <error descr="Argument passed must be of the type \B&\A, null given">null</error>) {
};

$dummy = function ((A&B)|C $a = null) {
};

$dummy = function (C|(A&B) $a = null) {
};

$dummy = function ((A&B)|C|(D&E) $a = null) {
};
