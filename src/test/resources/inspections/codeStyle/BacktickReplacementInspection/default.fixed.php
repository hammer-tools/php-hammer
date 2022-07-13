<?php

$dummy = shell_exec('dummy');

$dummy = shell_exec($dummy);

$dummy = shell_exec("dummy {$dummy}");

$dummy = shell_exec('dummy
          dummy');

$dummy = shell_exec('dummy \n dummy');

$dummy = shell_exec("dummy \n {$dummy}");
