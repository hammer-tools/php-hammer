<?php

$dummy = function () {
};

class DummyA
{
    function dummy()
    {
        $dummy = function () {
        };

        $self = $this;

        $dummy = function () use ($self) {
            return $self;
        };

        $dummy = static function () {
            return static function () {
                return static function () {
                    return get_class($this);
                };
            };
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
