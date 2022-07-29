<?php

var_dump(\Illuminate\Test::class);
var_dump(\Illuminate\Test::class);
var_dump(\Illuminate\Test::class);

if ($dummy::class === \Illuminate\Test::class) {
}

var_dump(\Illuminate\Test::class);
var_dump(\Illuminate\Test::class);
var_dump(\Illuminate\Test::class);

if ($dummy::class === \Illuminate\Test::class) {
}

var_dump(\Example::class);

// Not applicable:

var_dump('Example');
var_dump('Illuminate\\Test' . 'Exception');
