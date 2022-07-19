<?php

namespace Illuminate\Test {
    class Test
    {
        static function dd()
        {
        }

        function dump()
        {
        }
    }

    <warning descr="🔨 PHP Hammer: Debug-related function usage.">Test::dd()</warning>;
    <warning descr="🔨 PHP Hammer: Debug-related function usage.">(new Test)->dump()</warning>;
}
