<?php

$dummy = static function () {
};

class DummyA
{
    function dummy()
    {
        $dummy = static function () {
        };

        $self = $this;

        $dummy = static function () use ($self) {
            return $self;
        };

        $dummy = static fn() => true;
    }
}

$dummy = static fn() => true;

$dummy = static fn() => static fn() => true;

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

        $dummy = static fn() => true;

        $dummy = fn() => fn() => $this;
    }
}
