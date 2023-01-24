<?php

var_dump(\Example::class);

class_exists();
class_exists('\\Illuminate\\Test');
class_alias(\Illuminate\Test::class, '\\Illuminate\\Test');

interface_exists('\\Illuminate\\Test');
