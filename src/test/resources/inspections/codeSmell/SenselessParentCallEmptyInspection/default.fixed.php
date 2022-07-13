<?php

abstract class A
{
    abstract function example();
}

class B extends A
{
    function example()
    {
    }
}

class C extends B
{
    function example()
    {
    }
}
