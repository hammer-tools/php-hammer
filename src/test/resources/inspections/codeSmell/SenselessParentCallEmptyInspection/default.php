<?php

abstract class Dummy1000_A
{
    abstract public function ignoreComments();
}

class Dummy1000_B extends Dummy1000_A
{
    public function __construct()
    {
    }

    public function ignoreComments()
    {
        // Comments must be ignored.
    }

    public function doSomething()
    {
        return $this;
    }
}

class Dummy1000_C extends Dummy1000_B
{
    public function __construct()
    {
        // Skip: magic methods (like __construct()) must not be validated.
        parent::__construct();
    }

    public function ignoreComments()
    {
        // Must fail: senseless, ignoreComments() is empty (contains only comments).
        <weak_warning descr="🔨 PHP Hammer: senseless call to empty parent::ignoreComments().">parent::ignoreComments()</weak_warning>;

        // Skip: valid, but will just return null.
        return parent::ignoreComments();
    }

    public function doSomething()
    {
        // Skip: parent method do something.
        parent::doSomething();
    }
}

// Case 9000 (issue #49):

/**
 * @mixin Dummy9160_A
 */
trait Dummy9160_Trait
{
}

class Dummy9160_A
{
    public function test(): void
    {
    }
}

class Dummy9160_B extends Dummy9160_A
{
    public function test(): void
    {
        doSomething();
    }
}

class Dummy9160_C extends Dummy9160_B
{
    use Dummy9160_Trait;

    public function test(): void
    {
        // Skip: parent::call() is valid here, related to Dummy9160_B::test(), not by Dummy9160_A::test().
        parent::test();
    }
}
