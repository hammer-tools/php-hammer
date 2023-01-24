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

class_exists(\Illuminate\Test::class);
class_alias(\Illuminate\Test::class, \Illuminate\Test::class);

interface_exists(\Illuminate\Test::class);

// Not applicable:

var_dump('Example');
var_dump('Illuminate\\Test' . 'Exception');
