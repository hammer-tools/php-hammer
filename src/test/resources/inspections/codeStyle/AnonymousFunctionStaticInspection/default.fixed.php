<?php

$dummy = static function () {
};

class DummyParent
{
    static function staticFunction()
    {
    }

    function nonStaticFunction()
    {
    }
}

class DummyA extends DummyParent
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

        (static function () {
            parent::staticFunction();
            self::staticFunction();
            static::staticFunction();
            DummyParent::staticFunction();
            DummyA::staticFunction();
        })();
    }
}

$dummy = static fn() => true;

$dummy = static fn() => static fn() => true;

// Not applicable:

$dummy = static function () {
};

class DummyB extends DummyParent
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

        (function () {
            parent::nonStaticFunction();
            self::nonStaticFunction();
            static::nonStaticFunction();
            DummyParent::nonStaticFunction();
            DummyB::nonStaticFunction();
        })();

        (function () {
            parent::staticFunction();
            self::staticFunction();
            static::staticFunction();
            DummyParent::staticFunction();
            DummyB::staticFunction();

            (function () {
                parent::nonStaticFunction();
                self::nonStaticFunction();
                static::nonStaticFunction();
                DummyParent::nonStaticFunction();
                DummyB::nonStaticFunction();
            })();
        })();
    }
}
