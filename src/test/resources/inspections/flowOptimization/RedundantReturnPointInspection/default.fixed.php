<?php

$dummy = function () {
    if (x()) {
        x();

    }

    return true;
};

$dummy = function () {
    $something = true;

    if ($something) {
        $something = false;

    }

    return $something;
};

$dummy = function () {
    if (x()) {
    } else if (x()) {
        return 1;
    } else if (x()) {
    } elseif (x()) {
    } else {
    }

    return;
};

$dummy = function () {
    if (x()) {
        x();
    }

    return;
};

$dummy = function () {
    if (x()) {
        x();
    }

    return true;
};
