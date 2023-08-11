<?php

$dummy = function (<weak_warning descr="🔨 PHP Hammer: parameter type is implicitly null.">int $a = null</weak_warning>) {
};

$dummy = function (int|string $a = null) { // not applicable
};

/**
 * @method dummyA(<weak_warning descr="🔨 PHP Hammer: parameter type is implicitly null.">int $a = null</weak_warning>)
 * @method dummyB(int | string $a = null) not applicable
 */
class Dummy {
}
