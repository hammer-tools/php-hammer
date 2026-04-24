<?php

// Must report: nullable variable passed to non-nullable parameter
$test = function (?string $nullableParam): void {
    $another = function (string $param): void {};
    $another(<warning descr="🔨 PHP Hammer: nullable value passed to non-nullable parameter $param.">$nullableParam</warning>);
};

// Must report: null literal passed to non-nullable parameter
$test = function (): void {
    $another = function (string $param): void {};
    $another(<warning descr="🔨 PHP Hammer: nullable value passed to non-nullable parameter $param.">null</warning>);
};

// Must report: union type with null (long format) passed to non-nullable parameter
$test = function (string|null $nullableParam): void {
    $another = function (string $param): void {};
    $another(<warning descr="🔨 PHP Hammer: nullable value passed to non-nullable parameter $param.">$nullableParam</warning>);
};

// Skip: nullable variable passed to nullable parameter
$test = function (?string $nullableParam): void {
    $another = function (?string $param): void {};
    $another($nullableParam);
};

// Skip: null literal passed to nullable parameter
$test = function (): void {
    $another = function (?string $param): void {};
    $another(null);
};

// Skip: non-nullable variable passed to non-nullable parameter
$test = function (string $notNullableParam): void {
    $another = function (string $param): void {};
    $another($notNullableParam);
};

// Skip: untyped variable (cannot determine if nullable)
$test = function ($untypedParam): void {
    $another = function (string $param): void {};
    $another($untypedParam);
};

// Skip: mixed type parameter (accepts everything including null)
$test = function (?string $nullableParam): void {
    $another = function (mixed $param): void {};
    $another($nullableParam);
};

// Skip: parameter with no type declaration (accepts null)
$test = function (?string $nullableParam): void {
    $another = function ($param): void {};
    $another($nullableParam);
};

// Must report: using class methods (from the original issue)
$dummy = new class
{
    public function methodWithNullableParam(?string $nullableParam = null): void
    {
        $this->methodWithoutNullableParam(<warning descr="🔨 PHP Hammer: nullable value passed to non-nullable parameter $notNullable.">$nullableParam</warning>);
    }

    private function methodWithoutNullableParam(string $notNullable): void
    {
        echo $notNullable;
    }
};
