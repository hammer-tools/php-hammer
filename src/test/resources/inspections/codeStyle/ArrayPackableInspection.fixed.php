<?php

$arrayPacked = [
    123,
];

$arrayPacked = [
    123,
    456,
];

$arrayPacked = [
    123,
    456,
    789,
];

$arrayPacked = [
    123,
    ... [456],
    789,
];

$arrayPacked = [
    123,
    ... [456, ... [789] ],
    101112,
];

$arrayPacked = [
    123,
    ... [ 456 ],
    789,
];

$arrayPacked = [
    123,
    ... [ 456, ... [ 789 ] ],
    101112,
];

// Not applicable:

$arrayAlreadyPacked = [
    123,
    456,
];

$arrayUnpacked = [
    0 => 123,
    // 1 => 456,
    2 => 789,
];

$arrayUnpacked = [
    0 => 123,
    ... [],
    2 => 789,
];

$arrayUnpacked = [
    0 => 123,
    ... [ 2 => 456, ... [ 3 => 789 ] ],
    2 => 789,
];

$arrayUnpacked = [
    0 => 123,
    ... [ 4, 5, 6 ],
    2 => 789,
];

$arrayUnpacked = [
    0 => 123,
    ... [ 4, ... [ 5, 6 ] ],
    2 => 789,
];
