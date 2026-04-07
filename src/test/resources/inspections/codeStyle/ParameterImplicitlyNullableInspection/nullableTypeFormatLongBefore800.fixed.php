<?php

$dummy = function (?int $a = null) {
};

$dummy = function (int|string $a = null) { // not applicable
};

/**
 * @method dummyA(?int $a = null)
 * @method dummyB(int | string $a = null) not applicable
 * @method dummyC(?int $a = null)
 * @method dummyD(?array $a = null)
 * @method dummyE(int|null $a = null)
 * @method dummyF(array|null $a = null)
 */
class Dummy {
}

$dummy = function (A&B $a = null) {
};

$dummy = function ((A&B) $a = null) {
};

$dummy = function ((A&B)|C $a = null) {
};

$dummy = function (C|(A&B) $a = null) {
};

$dummy = function ((A&B)|C|(D&E) $a = null) {
};
