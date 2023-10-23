<?php

$dummy = function (<weak_warning descr="🔨 PHP Hammer: parameter type is implicitly null.">int $a = null</weak_warning>) {
};

$dummy = function (<weak_warning descr="🔨 PHP Hammer: parameter type is implicitly null.">int|string $a = null</weak_warning>) {
};

/**
 * @method dummyA(<weak_warning descr="🔨 PHP Hammer: parameter type is implicitly null.">int $a = null</weak_warning>)
 * @method dummyB(<weak_warning descr="🔨 PHP Hammer: parameter type is implicitly null.">int | string $a = null</weak_warning>)
 */
class Dummy {
}

$dummy = function (<weak_warning descr="🔨 PHP Hammer: parameter type is implicitly null.">A&B $a = <error descr="Argument passed must be of the type \B&\A, null given">null</error></weak_warning>) {
};

$dummy = function (<weak_warning descr="🔨 PHP Hammer: parameter type is implicitly null.">(A&B)|C $a = null</weak_warning>) {
};

$dummy = function (<weak_warning descr="🔨 PHP Hammer: parameter type is implicitly null.">C|(A&B) $a = null</weak_warning>) {
};

$dummy = function (<weak_warning descr="🔨 PHP Hammer: parameter type is implicitly null.">(A&B)|C|(D&E) $a = null</weak_warning>) {
};

// Not applicable:

$dummy = function (int $a) {
};

$dummy = function (?int $a = null) {
};

$dummy = function (int|null $a = null) {
};

$dummy = function (int|string|null $a = null) {
};

$dummy = function ($a = null) {
};

$dummy = function (mixed $a) {
};

$dummy = function (mixed $a = null) {
};
