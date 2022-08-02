<?php

$dummy = function () {
    if (rand()) {
        return 1;
    }
    return 2;
};

$dummy = function () {
    if (rand() && rand()) {
        return rand();
    }
    return rand();
};
