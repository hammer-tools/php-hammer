<?php

function dummy1(): <weak_warning descr="🔨 PHP Hammer: nullable type must be written as \"?int\".">int|null</weak_warning>
{
}

function dummy2(<weak_warning descr="🔨 PHP Hammer: nullable type must be written as \"?int\".">int|null</weak_warning> $dummy)
{
}

class Dummy1
{
    private <weak_warning descr="🔨 PHP Hammer: nullable type must be written as \"?int\".">int|null</weak_warning> $dummy;
}

// Not applicable:

function dummy3(A&B $dummy)
{}

function dummy3b((A&B) $dummy)
{}

function dummy4((A&B)|null $dummy)
{}
