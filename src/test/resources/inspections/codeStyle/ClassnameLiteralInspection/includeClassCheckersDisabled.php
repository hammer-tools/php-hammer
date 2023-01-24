<?php

var_dump(<weak_warning descr="🔨 PHP Hammer: string can be replaced by ::class equivalent.">'\\Example'</weak_warning>);

class_exists();
class_exists('\\Illuminate\\Test');
class_alias(\Illuminate\Test::class, '\\Illuminate\\Test');

interface_exists('\\Illuminate\\Test');
