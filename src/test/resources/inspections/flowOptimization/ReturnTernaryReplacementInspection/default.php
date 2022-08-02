<?php

$dummy = function () {
    <weak_warning descr="🔨 PHP Hammer: return-ternary can be replaced by if().">return</weak_warning> rand() ? 1 : 2;
};

$dummy = function () {
    <weak_warning descr="🔨 PHP Hammer: return-ternary can be replaced by if().">return</weak_warning> (rand() && rand()) ? rand() : rand();
};
