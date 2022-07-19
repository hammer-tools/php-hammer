<?php

function dummy1(): <weak_warning descr="🔨 PHP Hammer: Nullable type must be written as \"?int\".">int|null</weak_warning>
{
}

function dummy2(<weak_warning descr="🔨 PHP Hammer: Nullable type must be written as \"?int\".">int|null</weak_warning> $dummy)
{
}

class Dummy1
{
    private <weak_warning descr="🔨 PHP Hammer: Nullable type must be written as \"?int\".">int|null</weak_warning> $dummy;
}
