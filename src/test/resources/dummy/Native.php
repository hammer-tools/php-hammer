<?php

interface Traversable
{
}

interface Countable
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

class SplFileInfo
{
}
