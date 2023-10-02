<?php

class DummyA
{
    function dummy()
    {
        $dummy = <error descr="🔨 PHP Hammer: static anonymous functions cannot access $this.">static</error> function () {
            return $this;
        };

        $dummy = <error descr="🔨 PHP Hammer: static anonymous functions cannot access $this.">static</error> fn() => $this;
    }
}

// Not applicable:

class DummyB
{
    function dummy()
    {
        return static function () {
            return new class {
                function dummy1()
                {
                    return $this;
                }

                function dummy2()
                {
                    return $This;
                }
            };
        };
    }
}
