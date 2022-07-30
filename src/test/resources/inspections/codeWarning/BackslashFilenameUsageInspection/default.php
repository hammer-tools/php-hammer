<?php

$dummy = basename(<warning descr="🔨 PHP Hammer: Using backslash on filesystem-related name">'\\file.php'</warning>);

$dummy = basename(<warning descr="🔨 PHP Hammer: Using backslash on filesystem-related name">"\\file.php"</warning>);

$dummy = basename(<warning descr="🔨 PHP Hammer: Using backslash on filesystem-related name">'\/file.php'</warning>); // Backslash is being used as literal here.

$dummy = basename(<warning descr="🔨 PHP Hammer: Using backslash on filesystem-related name">"\/file.php"</warning>); // Backslash is being used as literal here.

$dummy = basename(<warning descr="🔨 PHP Hammer: Using backslash on filesystem-related name">"dir\\file.php"</warning>);

$dummy = basename(<warning descr="🔨 PHP Hammer: Using backslash on filesystem-related name">"\\dir\\file.php"</warning>);

$dummy = basename(<warning descr="🔨 PHP Hammer: Using backslash on filesystem-related name">"dir\\"</warning>);

$dummy = basename(<warning descr="🔨 PHP Hammer: Using backslash on filesystem-related name">'\\\\file.php'</warning>);

$dummy = basename(<warning descr="🔨 PHP Hammer: Using backslash on filesystem-related name">"\\\\file.php"</warning>);

$dummy = copy(<warning descr="🔨 PHP Hammer: Using backslash on filesystem-related name">"\\example.php"</warning>, <warning descr="🔨 PHP Hammer: Using backslash on filesystem-related name">"\\example.php"</warning>);

$dummy = RENAME(<warning descr="🔨 PHP Hammer: Using backslash on filesystem-related name">"\\example.php"</warning>, <warning descr="🔨 PHP Hammer: Using backslash on filesystem-related name">"\\example.php"</warning>);

$dummy = glob(<warning descr="🔨 PHP Hammer: Using backslash on filesystem-related name">"\\example.*"</warning>);

$dummy = basename(__DIR__ . <warning descr="🔨 PHP Hammer: Using backslash on filesystem-related name">'\\file.php'</warning>);

$dummy = basename(__DIR__ . __DIR__ . <warning descr="🔨 PHP Hammer: Using backslash on filesystem-related name">'\\file.php'</warning> . __DIR__);

$dummy = basename((__DIR__) . __DIR__ . <warning descr="🔨 PHP Hammer: Using backslash on filesystem-related name">'\\file.php'</warning> . __DIR__);

$dummy = basename((__DIR__) . __DIR__ . (<warning descr="🔨 PHP Hammer: Using backslash on filesystem-related name">'\\file.php'</warning>) . __DIR__);

$dummy = basename((__DIR__ . __DIR__ . (<warning descr="🔨 PHP Hammer: Using backslash on filesystem-related name">'\\file.php'</warning>) . __DIR__));

// Not applicable:

$dummy = basename();

$dummy = basename("file.php");

$dummy = basename("dir/file.php");

$dummy = basename("dir/file.php", "dir\\file.php");

$dummy = basename(str_replace("\\", DIRECTORY_SEPARATOR, "\\file.php"));

$dummy = nonFilesystemRelated("\\file.php");
