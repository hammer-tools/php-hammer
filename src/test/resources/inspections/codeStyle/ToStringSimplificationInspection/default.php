<?php

$dummy = <weak_warning descr="🔨 PHP Hammer: Call to __toString() can be simplified.">$dummy->__toString()</weak_warning>;
$dummy = <weak_warning descr="🔨 PHP Hammer: Call to __toString() can be simplified.">(new Dummy)->__toString()</weak_warning>;
$dummy = <weak_warning descr="🔨 PHP Hammer: Call to __toString() can be simplified.">$dummy->call()->__toString()</weak_warning>;
$dummy = <weak_warning descr="🔨 PHP Hammer: Call to __toString() can be simplified.">Dummy::call()->__toString()</weak_warning>;
$dummy = <weak_warning descr="🔨 PHP Hammer: Call to __toString() can be simplified.">call()->__ToString()</weak_warning>;

// Not applicable:

class Dummy
{
    function __toString()
    {
        return parent::__toString();
    }
}
