<?php

var_dump(<weak_warning descr="🔨 PHP Hammer: string can be replaced by ::class equivalent.">'Illuminate\Test'</weak_warning>);
var_dump(<weak_warning descr="🔨 PHP Hammer: string can be replaced by ::class equivalent.">'Illuminate\\Test'</weak_warning>);
var_dump(<weak_warning descr="🔨 PHP Hammer: string can be replaced by ::class equivalent.">"Illuminate\\Test"</weak_warning>);

if ($dummy::class === <weak_warning descr="🔨 PHP Hammer: string can be replaced by ::class equivalent.">'Illuminate\\Test'</weak_warning>) {
}

var_dump(<weak_warning descr="🔨 PHP Hammer: string can be replaced by ::class equivalent.">'\Illuminate\Test'</weak_warning>);
var_dump(<weak_warning descr="🔨 PHP Hammer: string can be replaced by ::class equivalent.">'\\Illuminate\\Test'</weak_warning>);
var_dump(<weak_warning descr="🔨 PHP Hammer: string can be replaced by ::class equivalent.">"\\Illuminate\\Test"</weak_warning>);

if ($dummy::class === <weak_warning descr="🔨 PHP Hammer: string can be replaced by ::class equivalent.">'\\Illuminate\\Test'</weak_warning>) {
}

var_dump(<weak_warning descr="🔨 PHP Hammer: string can be replaced by ::class equivalent.">'\\Example'</weak_warning>);

class_exists(<weak_warning descr="🔨 PHP Hammer: string can be replaced by ::class equivalent.">"\\Illuminate\\Test"</weak_warning>);
class_alias(\Illuminate\Test::class, <weak_warning descr="🔨 PHP Hammer: string can be replaced by ::class equivalent.">"\\Illuminate\\Test"</weak_warning>);

interface_exists(<weak_warning descr="🔨 PHP Hammer: string can be replaced by ::class equivalent.">'\\Illuminate\\Test'</weak_warning>);

// Not applicable:

var_dump('Example');
var_dump('Illuminate\\Test' . 'Exception');

var_dump('\\NonExisting\\Example');
