<?php

$dummy = function (int|null $a = null) {
};

$dummy = function (int|string|null $a = null) {
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
