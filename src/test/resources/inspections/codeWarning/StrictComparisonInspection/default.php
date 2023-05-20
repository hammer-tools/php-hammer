<?php

$varInt = 123;
$varString = "123";
$varFloat = 12.3;
$varBoolean = true;
/** @var int|null */ $varNull = null;
/** @var int|null */ $varZero = 0;

$functionInt1 = function () { return 123; };
$functionInt2 = function (): int { return 123; };
$functionIntNull1 = function (): int|null { return 12.3; };
$functionIntNull2 = function (): null|int { return 12.3; };
$functionFloat = function () { return 12.3; };
$functionIntFloat = function (): int|float { return 12.3; };
$functionMixed = function (): mixed { return 12.3; };
$functionUnknown = function () { return something(); };

// Applicable:

$caseValid_1000 = 123 <warning descr="🔨 PHP Hammer: strict comparison can be used safely here.">==</warning> 123;

$caseValid_1100 = $varInt <warning descr="🔨 PHP Hammer: strict comparison can be used safely here.">==</warning> $varInt;

$caseValid_1200 = $varInt <warning descr="🔨 PHP Hammer: strict comparison can be used safely here.">==</warning> $functionInt1();

$caseValid_1300 = $varInt <warning descr="🔨 PHP Hammer: strict comparison can be used safely here.">==</warning> $functionInt2();

$caseValid_1400 = ($varInt) <warning descr="🔨 PHP Hammer: strict comparison can be used safely here.">==</warning> (($functionInt2()));

$caseValid_1500 = ($varInt) <warning descr="🔨 PHP Hammer: strict comparison can be used safely here.">!=</warning> (($functionInt2()));

function caseValid_1600(int $a) {
    return $a <warning descr="🔨 PHP Hammer: strict comparison can be used safely here.">==</warning> 123;
}

// Not applicable:

$caseInvalid_1000 = $varInt == $varString;

$caseInvalid_1100 = $varInt == $varFloat;

$caseInvalid_1200 = $varInt == $varBoolean;

$caseInvalid_1300 = $varInt == $functionIntNull1();

$caseInvalid_1400 = $varInt == $functionFloat();

$caseInvalid_1500 = $varInt == $functionIntFloat();

$caseInvalid_1600 = $varInt == $functionMixed();

$caseInvalid_1700 = $varInt == $functionUnknown();

function caseInvalid_1800($a) {
    return $a == 123;
}

function caseInvalid_1900($a, $b) {
    return $a == $b;
}

function caseInvalid_2000(int|null $a, int|null $b) {
    return $a == $b;
}

$caseInvalid_2100 = $varNull == $varZero;
