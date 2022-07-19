<?php

$dummy = function () {
    if (x()) {
        x();

        <weak_warning descr="🔨 PHP Hammer: Redundant return point.">return true;</weak_warning>
    }

    return true;
};

$dummy = function () {
    $something = true;

    if ($something) {
        $something = false;

        <weak_warning descr="🔨 PHP Hammer: Redundant return point.">return $something;</weak_warning>
    }

    return $something;
};

$dummy = function () {
    if (x()) {
        <weak_warning descr="🔨 PHP Hammer: Redundant return point.">return;</weak_warning>
    } else if (x()) {
        return 1;
    } else if (x()) {
        <weak_warning descr="🔨 PHP Hammer: Redundant return point.">return;</weak_warning>
    } elseif (x()) {
        <weak_warning descr="🔨 PHP Hammer: Redundant return point.">return;</weak_warning>
    } else {
        <weak_warning descr="🔨 PHP Hammer: Redundant return point.">return;</weak_warning>
    }

    return;
};

$dummy = function () {
    if (x()) {
        x();
        <weak_warning descr="🔨 PHP Hammer: Redundant return point.">return;</weak_warning>
    }

    return;
};

$dummy = function () {
    if (x()) {
        x();
        <weak_warning descr="🔨 PHP Hammer: Redundant return point.">return TRUE;</weak_warning>
    }

    return true;
};

$dummy = function () {
    if (x()) {
        x();
        <weak_warning descr="🔨 PHP Hammer: Redundant return point.">return /** ... */ true;</weak_warning>
    }

    return true;
};
