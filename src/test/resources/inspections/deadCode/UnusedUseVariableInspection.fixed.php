<?php

$dummy = function() use ($b) {
    return $b;
};

$dummy = function() use (&$b) {
    return $b;
};
