<?php

abstract class A
{
    abstract public function exampleEmpty();
}

class B extends A
{
    public function __construct()
    {
    }

    public function exampleEmpty()
    {
        // Comments must be ignored.
    }

    public function exampleFilled()
    {
         return $this;
    }
}

class C extends B
{
    public function __construct()
    {
        // Not applicable:
        parent::__construct();
    }

    public function exampleEmpty()
    {

        // Not applicable:
        return parent::exampleEmpty();
    }

    public function exampleFilled()
    {
        // Not applicable:
        parent::exampleFilled();
    }
}
