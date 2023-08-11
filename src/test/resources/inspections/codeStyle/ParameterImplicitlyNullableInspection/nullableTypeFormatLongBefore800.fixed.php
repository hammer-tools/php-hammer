<?php

$dummy = function (?int $a = null) {
};

$dummy = function (int|string $a = null) { // not applicable
};

/**
 * @method dummyA(?int $a = null)
 * @method dummyB(int | string $a = null) not applicable
 */
class Dummy {
}
