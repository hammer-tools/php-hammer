<?php

$dummy = basename('/file.php');

$dummy = basename("/file.php");

$dummy = basename('//file.php'); // Backslash is being used as literal here.

$dummy = basename("//file.php"); // Backslash is being used as literal here.

$dummy = basename("dir/file.php");

$dummy = basename("/dir/file.php");

$dummy = basename("dir/");

$dummy = basename('//file.php');

$dummy = basename("//file.php");

$dummy = copy("/example.php", "/example.php");

$dummy = RENAME("/example.php", "/example.php");

$dummy = glob("/example.*");

$dummy = basename(__DIR__ . '/file.php');

$dummy = basename(__DIR__ . __DIR__ . '/file.php' . __DIR__);

$dummy = basename((__DIR__) . __DIR__ . '/file.php' . __DIR__);

$dummy = basename((__DIR__) . __DIR__ . ('/file.php') . __DIR__);

$dummy = basename((__DIR__ . __DIR__ . ('/file.php') . __DIR__));

// Not applicable:

$dummy = basename();

$dummy = basename("file.php");

$dummy = basename("dir/file.php");

$dummy = basename("dir/file.php", "dir\\file.php");

$dummy = basename(str_replace("\\", DIRECTORY_SEPARATOR, "\\file.php"));

$dummy = nonFilesystemRelated("\\file.php");
