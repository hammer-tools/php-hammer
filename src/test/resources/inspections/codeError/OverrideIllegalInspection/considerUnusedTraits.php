<?php

trait NotChildOverride
{
    // Must be an error: trait is not used by no place, but because option is enabled, we must check it and guarantee that it is also illegal.
    <error descr="🔨 PHP Hammer: this method doesn't actually perform an override; remove this illegal #[Override] attribute.">#[\Override]</error>
    function traitNotUsedWithOverride()
    {
        doSomething();
    }

    // Skip: just fine here, without #[Override].
    function traitNotUsedWithoutOverride()
    {
        doSomething();
    }
}
