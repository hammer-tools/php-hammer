<?php

var_dump(<weak_warning descr="🔨 PHP Hammer: String can be replaced by ::class equivalent.">'Illuminate\Test'</weak_warning>);
var_dump(<weak_warning descr="🔨 PHP Hammer: String can be replaced by ::class equivalent.">'Illuminate\\Test'</weak_warning>);
var_dump(<weak_warning descr="🔨 PHP Hammer: String can be replaced by ::class equivalent.">"Illuminate\\Test"</weak_warning>);

if ($dummy::class === <weak_warning descr="🔨 PHP Hammer: String can be replaced by ::class equivalent.">'Illuminate\\Test'</weak_warning>) {
}

var_dump(<weak_warning descr="🔨 PHP Hammer: String can be replaced by ::class equivalent.">'\Illuminate\Test'</weak_warning>);
var_dump(<weak_warning descr="🔨 PHP Hammer: String can be replaced by ::class equivalent.">'\\Illuminate\\Test'</weak_warning>);
var_dump(<weak_warning descr="🔨 PHP Hammer: String can be replaced by ::class equivalent.">"\\Illuminate\\Test"</weak_warning>);

if ($dummy::class === <weak_warning descr="🔨 PHP Hammer: String can be replaced by ::class equivalent.">'\\Illuminate\\Test'</weak_warning>) {
}

// Not applicable:

var_dump('Example');
var_dump('\\Example');
var_dump('Illuminate\\Test' . 'Exception');
