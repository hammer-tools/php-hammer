<?php

$dummy = <weak_warning descr="🔨 PHP Hammer: anonymous function can be static.">function</weak_warning> () {
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
        $dummy = <weak_warning descr="🔨 PHP Hammer: anonymous function can be static.">function</weak_warning> () {
        };

        $self = $this;

        $dummy = <weak_warning descr="🔨 PHP Hammer: anonymous function can be static.">function</weak_warning> () use ($self) {
            return $self;
        };

        $dummy = <weak_warning descr="🔨 PHP Hammer: anonymous function can be static.">fn</weak_warning>() => true;

        (<weak_warning descr="🔨 PHP Hammer: anonymous function can be static.">function</weak_warning> () {
            parent::staticFunction();
            self::staticFunction();
            static::staticFunction();
            DummyParent::staticFunction();
            DummyA::staticFunction();
        })();
    }
}

$dummy = <weak_warning descr="🔨 PHP Hammer: anonymous function can be static.">fn</weak_warning>() => true;

$dummy = <weak_warning descr="🔨 PHP Hammer: anonymous function can be static.">fn</weak_warning>() => <weak_warning descr="🔨 PHP Hammer: anonymous function can be static.">fn</weak_warning>() => true;

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
