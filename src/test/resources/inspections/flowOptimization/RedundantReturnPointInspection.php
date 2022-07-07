<?php

function case1()
{
    if (case1()) {
        case1();

        <weak_warning descr="[PHP Hammer] Redundant return point.">return true;</weak_warning>
    }

    return true;
}

function case2()
{
    $something = true;

    if ($something) {
        $something = false;

        <weak_warning descr="[PHP Hammer] Redundant return point.">return $something;</weak_warning>
    }

    return $something;
}

function case3()
{
    if (case3()) {
        <weak_warning descr="[PHP Hammer] Redundant return point.">return;</weak_warning>
    } else if (case3()) {
        return 1;
    } else if (case3()) {
        <weak_warning descr="[PHP Hammer] Redundant return point.">return;</weak_warning>
    } elseif (case3()) {
        <weak_warning descr="[PHP Hammer] Redundant return point.">return;</weak_warning>
    } else {
        <weak_warning descr="[PHP Hammer] Redundant return point.">return;</weak_warning>
    }

    return;
}

function case4()
{
    if (case4()) {
        case4();
        <weak_warning descr="[PHP Hammer] Redundant return point.">return;</weak_warning>
    }

    return;
}

function case5()
{
    if (case5()) {
        case5();
        <weak_warning descr="[PHP Hammer] Redundant return point.">return TRUE;</weak_warning>
    }

    return true;
}
