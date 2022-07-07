<?php

$dummy = <warning descr="[PHP Hammer] Function can be static.">function</warning> () {
};

class DummyA
{
    function dummy()
    {
        $dummy = <warning descr="[PHP Hammer] Function can be static.">function</warning> () {
        };

        $self = $this;

        $dummy = <warning descr="[PHP Hammer] Function can be static.">function</warning> () use ($self) {
            return $self;
        };
    }
}

// Not applicable:

$dummy = static function () {
};

class DummyB
{
    function dummy()
    {
        $dummy = static function () {
        };

        $dummy = function () {
            return get_class($this);
        };

        $dummy = function () {
            return function () {
                return function () {
                    return get_class($this);
                };
            };
        };
    }
}
