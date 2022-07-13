<?php

use function array_merge;

$dummy = [];

$dummy = [1, 2, 3];

// Not applicable:

$dummy = array_merge([1, 2, 3], [1, 2, 3]);

$dummy = array_merge(... [1, 2, 3]);

$dummy = array_merge(... x());

$dummy = array_merge(...);
