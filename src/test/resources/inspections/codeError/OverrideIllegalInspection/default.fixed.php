<?php

use Namespaced\Override as Override;
use Override as OverrideAlias;

trait NotChildOverride
{
    // Skip: trait is not used by no place, so we can just ignore if it is #[Override] or not.
    #[\Override]
    function traitNotUsedWithOverride()
    {
        doSomething();
    }

    // Skip: same here, without #[Override].
    function traitNotUsedWithoutOverride()
    {
        doSomething();
    }
}

trait ChildPrivateOverride
{
    // Must be an error: trait indirectly override Base::privateNotAcceptsOverride() that is private.
    function privateNotAcceptsOverride()
    {
        doSomething();
    }

    // Must be an error: trait indirectly override Base::publicPrivateAcceptsOverride() that is public and BaseB::publicPrivateAcceptsOverride() is private.
    // Note: in that case, private methods will be ignored, so #[Override] is required here.
    #[\Override]
    function publicPrivateAcceptsOverride()
    {
        doSomething();
    }

    // Skip: trait indirectly override Base::protectedAcceptsOverride().
    #[\Override]
    function protectedAcceptsOverride()
    {
        doSomething();
    }
}

trait ChildOverride
{
    // Skip: trait indirectly override Base::existsOnParentClass().
    #[\Override]
    function existsOnParentClass()
    {
        doSomething();
    }
}

trait ChildNotOverride
{
    // Must be an error: dontExistsOnParentClasses() doesn't exists on parent classes indirectly.
    function dontExistsOnParentClasses()
    {
        doSomething();
    }
}

trait ChildRenamedOverride
{
    // Must be an error: renamedOnTrait() doesn't exists on parent classes indirectly.
    function renamedOnTrait()
    {
        doSomething();
    }
}

class Base
{
    function existsOnParentClass()
    {
        doSomething();
    }

    function existsOnParentClassUsingAlias()
    {
        doSomething();
    }

    function willBeRenamedOnTrait()
    {
        doSomething();
    }

    function publicPrivateAcceptsOverride()
    {
        doSomething();
    }

    protected function protectedAcceptsOverride()
    {
        doSomething();
    }

    private function privateNotAcceptsOverride()
    {
        doSomething();
    }
}

class Child
    extends Base
{
    use ChildPrivateOverride;
    use ChildNotOverride;
    use ChildOverride;
    use ChildRenamedOverride {
        renamedOnTrait as willBeRenamedOnTrait;
    }
}

class BaseB
{
    private function publicPrivateAcceptsOverride()
    {
        doSomething();
    }

    protected function protectedAcceptsOverride()
    {
        doSomething();
    }
}

class ChildB
    extends BaseB
{
    use ChildPrivateOverride;
}

$dummy = new class
    extends Base {
    // Must be an error: dontExistsOnParentClasses() doesn't exists on parent classes.
    #[\stdClass]
    function dontExistsOnParentClasses()
    {
        doSomething();
    }

    // Must be an error: dontExistsOnParentClassesUsingAlias() doesn't exists on parent classes.
    #[\stdClass]
    function dontExistsOnParentClassesUsingAlias()
    {
        doSomething();
    }

    // Must be an error: overridden privateNotAcceptsOverride() that is private.
    function privateNotAcceptsOverride()
    {
        doSomething();
    }

    // Skip: override is correct here.
    #[\Override]
    function existsOnParentClass()
    {
        doSomething();
    }

    // Skip: override is correct here.
    #[OverrideAlias]
    function existsOnParentClassUsingAlias()
    {
        doSomething();
    }

    // Skip: not really an #[\Override] attribute.
    #[Override]
    function dontReallyOverrideAttribute()
    {
        doSomething();
    }

    // Skip: overridden Base::protectedAcceptsOverride() and BaseB::protectedAcceptsOverride().
    #[\Override]
    function protectedAcceptsOverride()
    {
        doSomething();
    }
};

// Case 1000:

class Dummy1000A {
    function test() {
        doSomething();
    }
}

class Dummy1000B extends Dummy1000A {
    use Dummy1000D;
}

class Dummy1000C {
    use Dummy1000D;
}

trait Dummy1000D {
    // Must be an error: test() is implemented by Dummy1000B::test() (via Dummy1000A) but not via Dummy1000C.
    #[\Override]
    function test()
    {
        doSomething();
    }
}
