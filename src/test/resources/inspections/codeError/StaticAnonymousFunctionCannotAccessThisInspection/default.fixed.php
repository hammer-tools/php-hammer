<?php

class DummyA
{
    function dummy()
    {
        $dummy = function () {
            return $this;
        };

        $dummy = fn() => $this;
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
