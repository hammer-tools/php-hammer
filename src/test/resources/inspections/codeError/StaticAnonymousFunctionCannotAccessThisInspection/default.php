<?php

class Dummy
{
    function dummy()
    {
        $dummy = <error descr="🔨 PHP Hammer: Static anonymous functions cannot access $this.">static</error> function () {
            return $this;
        };

        $dummy = <error descr="🔨 PHP Hammer: Static anonymous functions cannot access $this.">static</error> fn() => $this;
    }
}
