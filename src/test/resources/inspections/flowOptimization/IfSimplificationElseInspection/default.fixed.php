<?php

$dummy = function ($dummy) {
    if ($dummy) {
        return true;
    } else {
        return true;
    }
};

$dummy = function ($dummy) {
    if ($dummy) {
        return false;
    } elseif ($dummy) {
        return true;
    } else {
        return true;
    }
};

$dummy = function ($dummy) {
    if ($dummy) {
        return false;
    } else if ($dummy) {
        return true;
    } else {
        return true;
    }
};

$dummy = function ($dummy) {
    if ($dummy) {
        return false;
    } elseif ($dummy) {
        return true;
    } else {
        return true;
    }
};
