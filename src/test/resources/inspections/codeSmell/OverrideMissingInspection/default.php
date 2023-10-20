<?php

use Namespaced\Override as Override;

class Base {
    function requiresOverrideAttribute() {
        doSomething();
    }

    function incorrectOverrideAttribute() {
        doSomething();
    }

    function overrideAttributeAlreadyApplied() {
        doSomething();
    }
}

$dummy = new class extends Base {
    // Must fails: requires #[\Override] attribute.
    function <weak_warning descr="🔨 PHP Hammer: this method performs an override; consider using the #[Override] attribute.">requiresOverrideAttribute</weak_warning>() {
        doSomething();
    }

    // Must fails: incorrect #[\Override] attribute class used (eg. wrong import).
    #[Override]
    public function <weak_warning descr="🔨 PHP Hammer: this method performs an override; consider using the #[Override] attribute.">incorrectOverrideAttribute</weak_warning>() {
        doSomething();
    }

    // Skip: already correctly contains #[\Override] attribute.
    #[\Override]
    function overrideAttributeAlreadyApplied() {
        doSomething();
    }

    // Skip: #[\Override] attribute is not required here, parent classes don't contains this method declaration.
    function notRequiresOverridden() {
        doSomething();
    }
};
