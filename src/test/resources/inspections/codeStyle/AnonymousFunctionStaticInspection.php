<?php

$dummy = <weak_warning descr="[PHP Hammer] This anonymous function can be static.">function</weak_warning> () {
};

class DummyA
{
    function dummy()
    {
        $dummy = <weak_warning descr="[PHP Hammer] This anonymous function can be static.">function</weak_warning> () {
        };

        $self = $this;

        $dummy = <weak_warning descr="[PHP Hammer] This anonymous function can be static.">function</weak_warning> () use ($self) {
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
