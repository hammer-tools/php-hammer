<?php

$dummy = "<weak_warning descr="🔨 PHP Hammer: variable must have curly braces.">$a</weak_warning>";

$dummy = "dummy <weak_warning descr="🔨 PHP Hammer: variable must have curly braces.">$a</weak_warning> dummy";

// Not applicable:

$dummy = '$a';

$dummy = "\$a";

$dummy = "dummy \$a dummy";

$dummy = "dummy ${a} dummy";

$dummy = "dummy {$a} dummy";
