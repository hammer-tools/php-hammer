<?php

class Dummy
{
    const DUMMY = null;

    private <weak_warning descr="🔨 PHP Hammer: class reference format must be \"Dummy\".">self</weak_warning> $a;
    public Dummy|int|null $b;

    function dummy(Dummy $b): <weak_warning descr="🔨 PHP Hammer: class reference format must be \"Dummy\".">self</weak_warning>
    {
        return
            <weak_warning descr="🔨 PHP Hammer: class reference format must be \"Dummy\".">self</weak_warning>::DUMMY ||
            Dummy::DUMMY;
    }

    function dummyB(<weak_warning descr="🔨 PHP Hammer: class reference format must be \"Dummy\".">self</weak_warning>|int $a): Dummy|int|null
    {
        if ($a instanceof <weak_warning descr="🔨 PHP Hammer: class reference format must be \"Dummy\".">self</weak_warning>) {
        }

        return new Dummy;
    }
}

class DummyB extends Dummy
{
    function dummy(Dummy $b): <weak_warning descr="🔨 PHP Hammer: class reference format must be \"DummyB\".">self</weak_warning>
    {
        return <weak_warning descr="🔨 PHP Hammer: class reference format must be \"DummyB\".">self</weak_warning>::DUMMY;
    }
}

// Not applicable:

$dummy = Dummy::DUMMY;

class DummyC extends Dummy
{
    function dummy(Dummy $b)
    {
        return Dummy::DUMMY;
    }
}

trait DummyATrait
{
    function dummy(self $b): self
    {
      return self::class;
    }
}

trait DummyBTrait
{
    function dummy(DummyTrait $b): DummyTrait
    {
      return DummyTrait::class;
    }
}
