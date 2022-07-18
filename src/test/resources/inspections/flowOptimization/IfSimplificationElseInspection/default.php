<?php

$dummy = function ($dummy) {
    <weak_warning descr="[PHP Hammer] Useless conditional can be safely dropped.">if</weak_warning> ($dummy) {
        return true;
    } else {
        return true;
    }
};

$dummy = function ($dummy) {
    if ($dummy) {
        return false;
    } <weak_warning descr="[PHP Hammer] Useless conditional can be safely dropped.">elseif</weak_warning> ($dummy) {
        return true;
    } else {
        return true;
    }
};

$dummy = function ($dummy) {
    if ($dummy) {
        return false;
    } else <weak_warning descr="[PHP Hammer] Useless conditional can be safely dropped.">if</weak_warning> ($dummy) {
        return true;
    } else {
        return true;
    }
};

$dummy = function ($dummy) {
    if ($dummy) {
        return false;
    } <weak_warning descr="[PHP Hammer] Useless conditional can be safely dropped.">elseif</weak_warning> ($dummy) {
        return true;
    } else {
        return true;
    }
};
