<?php

$dummy = function (<weak_warning descr="[PHP Hammer] Parameter type is implicitly null.">int $a = null</weak_warning>) {
};

$dummy = function (<weak_warning descr="[PHP Hammer] Parameter type is implicitly null.">int|string $a = null</weak_warning>) {
};

/**
 * @method dummyA(<weak_warning descr="[PHP Hammer] Parameter type is implicitly null.">int $a = null</weak_warning>)
 * @method dummyB(<weak_warning descr="[PHP Hammer] Parameter type is implicitly null.">int | string $a = null</weak_warning>)
 */
class Dummy {
}

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
