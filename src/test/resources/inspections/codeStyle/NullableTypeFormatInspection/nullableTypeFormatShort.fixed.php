<?php

function dummy1(): ?int
{
}

function dummy2(?int $dummy)
{
}

class Dummy1
{
    private ?int $dummy;
}

// Not applicable:

function dummy3(A&B $dummy)
{}

function dummy4((A&B)|null $dummy)
{}
