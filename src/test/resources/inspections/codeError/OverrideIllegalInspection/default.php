<?php

use Namespaced\Override as Override;
use Override as OverrideAlias;

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
}

$dummy = new class extends Base {
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
};
