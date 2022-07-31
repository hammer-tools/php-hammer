<?php

$dummy = <weak_warning descr="🔨 PHP Hammer: backtick operator can be replaced by shell_exec().">`dummy`</weak_warning>;

$dummy = <weak_warning descr="🔨 PHP Hammer: backtick operator can be replaced by shell_exec().">`{$dummy}`</weak_warning>;

$dummy = <weak_warning descr="🔨 PHP Hammer: backtick operator can be replaced by shell_exec().">`dummy {$dummy}`</weak_warning>;

$dummy = <weak_warning descr="🔨 PHP Hammer: backtick operator can be replaced by shell_exec().">`dummy
          dummy`</weak_warning>;

$dummy = <weak_warning descr="🔨 PHP Hammer: backtick operator can be replaced by shell_exec().">`dummy \n dummy`</weak_warning>;

$dummy = <weak_warning descr="🔨 PHP Hammer: backtick operator can be replaced by shell_exec().">`dummy \n {$dummy}`</weak_warning>;
