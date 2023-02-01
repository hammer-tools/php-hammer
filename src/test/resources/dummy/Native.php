<?php

interface Traversable
{
}

interface IteratorAggregate
    extends Traversable
{
}

class ArrayObject
    implements IteratorAggregate
{
}

class Generator implements Traversable
{
}
