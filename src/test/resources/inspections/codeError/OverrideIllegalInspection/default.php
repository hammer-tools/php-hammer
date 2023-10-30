<?php

use Namespaced\Override as Override;
use Override as OverrideAlias;

trait ChildPrivateOverride
{
    // Must be an error: trait indirectly override Base::privateNotAcceptsOverride() that is private.
    #[<error descr="🔨 PHP Hammer: this method doesn't actually perform an override; remove this illegal #[Override] attribute.">\Override</error>]
    function privateNotAcceptsOverride()
    {
        doSomething();
    }

    // Must be an error: trait indirectly override Base::publicPrivateAcceptsOverride() that is public and BaseB::publicPrivateAcceptsOverride() is private.
    // Note: in that case, private methods will be ignored, so #[Override] is required here.
    #[<error descr="🔨 PHP Hammer: this method doesn't actually perform an override; remove this illegal #[Override] attribute.">\Override</error>]
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

trait NotChildOverride
{
    // Must be an error: existsOnParentClass() doesn't exists on parent classes indirectly (Child doesn't uses this trait).
    #[<error descr="🔨 PHP Hammer: this method doesn't actually perform an override; remove this illegal #[Override] attribute.">\Override</error>]
    function existsOnParentClass()
    {
        doSomething();
    }
}

trait ChildNotOverride
{
    // Must be an error: dontExistsOnParentClasses() doesn't exists on parent classes indirectly.
    #[<error descr="🔨 PHP Hammer: this method doesn't actually perform an override; remove this illegal #[Override] attribute.">\Override</error>]
    function dontExistsOnParentClasses()
    {
        doSomething();
    }
}

trait ChildRenamedOverride
{
    // Must be an error: renamedOnTrait() doesn't exists on parent classes indirectly.
    #[<error descr="🔨 PHP Hammer: this method doesn't actually perform an override; remove this illegal #[Override] attribute.">\Override</error>]
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
    #[<error descr="🔨 PHP Hammer: this method doesn't actually perform an override; remove this illegal #[Override] attribute.">\Override</error>, \stdClass]
    function dontExistsOnParentClasses()
    {
        doSomething();
    }

    // Must be an error: dontExistsOnParentClassesUsingAlias() doesn't exists on parent classes.
    #[<error descr="🔨 PHP Hammer: this method doesn't actually perform an override; remove this illegal #[Override] attribute.">OverrideAlias</error>]
    #[\stdClass]
    function dontExistsOnParentClassesUsingAlias()
    {
        doSomething();
    }

    // Must be an error: overridden privateNotAcceptsOverride() that is private.
    #[<error descr="🔨 PHP Hammer: this method doesn't actually perform an override; remove this illegal #[Override] attribute.">\Override</error>]
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
    #[<error descr="🔨 PHP Hammer: this method doesn't actually perform an override; remove this illegal #[Override] attribute.">\Override</error>]
    function test()
    {
        doSomething();
    }
}
