<?php

// Not applicable:
use function var_export;

// Native functions.
<warning descr="🔨 PHP Hammer: Debug-related function usage.">var_dump()</warning>;
<warning descr="🔨 PHP Hammer: Debug-related function usage.">print_r()</warning>;
<warning descr="🔨 PHP Hammer: Debug-related function usage.">get_defined_vars()</warning>;
<warning descr="🔨 PHP Hammer: Debug-related function usage.">debug_print_backtrace()</warning>;

// xdebug-related functions.
<warning descr="🔨 PHP Hammer: Debug-related function usage.">xdebug_break()</warning>;

// Frameworks-related functions.
<warning descr="🔨 PHP Hammer: Debug-related function usage.">Illuminate\Support\Debug\Dumper::dump()</warning>;
<warning descr="🔨 PHP Hammer: Debug-related function usage.">dump()</warning>;

// Quick-fix is not available:
if (<warning descr="🔨 PHP Hammer: Debug-related function usage.">xdebug_break()</warning>) {
}
