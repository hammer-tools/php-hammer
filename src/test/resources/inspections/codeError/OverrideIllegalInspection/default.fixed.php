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

/**
 * @method void methodExistsAtPhpdocOnly()
 */
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

    // Must be an error: methodExistsAtPhpdocOnly() doesn't exists on parent classes in fact (it is just a phpdoc annotation).
    public function methodExistsAtPhpdocOnly()
    {
        doSomething();
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

/**
 * @method void test()
 */
class Dummy1000C {
    use Dummy1000D;
}

trait Dummy1000D {
    // Must be an error: test() is implemented by Dummy1000B::test() (via Dummy1000A) but not via Dummy1000C (phpdoc is not valid as implementation).
    #[\Override]
    function test()
    {
        doSomething();
    }
}

// Case 2000: Nested traits (trait using another trait):

trait NestedOuterTrait {
    use NestedInnerTrait;
}

trait NestedInnerTrait {
    // Skip: this method correctly overrides BaseNested::validOverride() via NestedOuterTrait -> NestedConcreteClass
    #[\Override]
    function validOverride()
    {
        doSomething();
    }

    // Must be an error: does not override any method
    function invalidOverride()
    {
        doSomething();
    }
}

class BaseNested {
    function validOverride()
    {
        doSomething();
    }
}

class NestedConcreteClass extends BaseNested {
    use NestedOuterTrait;
}

// Interface cases:

interface FooInterface {
    function bar(): void;

    function barB(): void;
}

interface BarInterface {
    function zoo(): void;
}

class BarClass implements FooInterface {
    // Skip: #[Override] works on interface method implementations.
    #[\Override]
    function bar(): void {
        doSomething();
    }

    // Must be an error: method is not in any interface or parent class.
    function baz(): void {
        doSomething();
    }

    // Skip: #[Override] works on interface method implementations.
    #[\Override]
    function barB(): void {
        doSomething();
    }
}

class DummyExtendsAndImplements extends Base implements FooInterface {
    // Skip: the method matches both a parent class method and an interface method.
    #[\Override]
    function existsOnParentClass() {
        doSomething();
    }
}

abstract class DummyMultipleInterfaces implements FooInterface, BarInterface {
    // Skip: #[Override] is valid because it implements FooInterface::bar().
    #[\Override]
    function bar() {
        doSomething();
    }

    // Skip: #[Override] is valid because it implements BarInterface::zoo().
    #[\Override]
    function zoo() {
        doSomething();
    }
}
