<?php

class Dummy
{
    const DUMMY = null;

    private self $a;
    public <weak_warning descr="🔨 PHP Hammer: class reference format must be \"self\".">Dummy</weak_warning>|int|null $b;

    function dummy(<weak_warning descr="🔨 PHP Hammer: class reference format must be \"self\".">Dummy</weak_warning> $b): self
    {
        return
            self::DUMMY ||
            <weak_warning descr="🔨 PHP Hammer: class reference format must be \"self\".">Dummy</weak_warning>::DUMMY;
    }

    function dummyB(self|int $a): <weak_warning descr="🔨 PHP Hammer: class reference format must be \"self\".">Dummy</weak_warning>|int|null
    {
        if ($a instanceof self) {
        }

        return new <weak_warning descr="🔨 PHP Hammer: class reference format must be \"self\".">Dummy</weak_warning>;
    }
}

// Not applicable:

$dummy = Dummy::DUMMY;

class DummyB extends Dummy
{
    function dummy(Dummy $b)
    {
        return
            self::DUMMY ||
            Dummy::DUMMY;
    }
}
