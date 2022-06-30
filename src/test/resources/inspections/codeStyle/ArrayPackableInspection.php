<?php

$arrayPacked = <weak_warning descr="[PHP Hammer] Packed array can be simplified.">[
    0 => 123,
]</weak_warning>;

$arrayPacked = <weak_warning descr="[PHP Hammer] Packed array can be simplified.">[
    0 => 123,
    1 => 456,
]</weak_warning>;

$arrayPacked = <weak_warning descr="[PHP Hammer] Packed array can be simplified.">[
    0 => 123,
    456,
    2 => 789,
]</weak_warning>;

$arrayPacked = <weak_warning descr="[PHP Hammer] Packed array can be simplified.">[
    0 => 123,
    ... [ 1 => 456 ],
    2 => 789,
]</weak_warning>;

$arrayPacked = <weak_warning descr="[PHP Hammer] Packed array can be simplified.">[
    0 => 123,
    ... [ 1 => 456, ... [ 2 => 789 ] ],
    3 => 101112,
]</weak_warning>;

$arrayPacked = <weak_warning descr="[PHP Hammer] Packed array can be simplified.">[
    0 => 123,
    ... [ 456 ],
    2 => 789,
]</weak_warning>;

$arrayPacked = <weak_warning descr="[PHP Hammer] Packed array can be simplified.">[
    0 => 123,
    ... [ 456, ... [ 789 ] ],
    3 => 101112,
]</weak_warning>;

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
