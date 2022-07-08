<?php

function case1()
{
    if (case1()) {
        case1();

    }

    return true;
}

function case2()
{
    $something = true;

    if ($something) {
        $something = false;

    }

    return $something;
}

function case3()
{
    if (case3()) {
    } else if (case3()) {
        return 1;
    } else if (case3()) {
    } elseif (case3()) {
    } else {
    }

    return;
}

function case4()
{
    if (case4()) {
        case4();
    }

    return;
}

function case5()
{
    if (case5()) {
        case5();
    }

    return true;
}
