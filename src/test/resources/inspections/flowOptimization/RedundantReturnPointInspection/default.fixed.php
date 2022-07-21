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

$dummy = function () {
    if (x()) {
        x();
    }

    return /** ... */ (FN () => 123 /** ... */);
};

$dummy = function () {
    if (x()) {
    }

    return new DateTime;
};

$dummy = function () {
    if (x()) {
    }

    return DateTime(1, 2, 3);
};

$dummy = function () {
    if (x()) {
    }

    return DateTime::createFromFormat(
    );
};
