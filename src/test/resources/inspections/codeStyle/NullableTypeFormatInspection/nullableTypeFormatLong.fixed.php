<?php

function dummy1(): int|null
{}

function dummy2(int|null $dummy)
{}

class Dummy1 {
    private int|null $dummy;
}

// Not applicable:

function dummy3(A&B $dummy)
{}

function dummy3b((A&B) $dummy)
{}

function dummy4((A&B)|null $dummy)
{}
