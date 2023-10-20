<?php

use Override as Override;

class Base {
    function requiresOverrideAttribute() {
        doSomething();
    }
}

$dummy = new class extends Base {
    // Must fails: requires #[\Override] attribute.
    // Must not occur auto import, #[Override] is already imported.
    function <weak_warning descr="🔨 PHP Hammer: this method performs an override; consider using the #[Override] attribute.">requiresOverrideAttribute</weak_warning>() {
        doSomething();
    }
};
