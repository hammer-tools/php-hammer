<?php

$dummy = function (int|null $a = null) {
};

$dummy = function (int|string|null $a = null) {
};

/**
 * @method dummyA(int|null $a = null)
 * @method dummyB(int | string|null $a = null)
 */
class Dummy {
}

$dummy = function ((A&B)|null $a = null) {
};

$dummy = function ((A&B)|C|null $a = null) {
};

$dummy = function (C|(A&B)|null $a = null) {
};

$dummy = function ((A&B)|C|(D&E)|null $a = null) {
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
