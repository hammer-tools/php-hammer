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
    #[\Override] function requiresOverrideAttribute() {
        doSomething();
    }

    // Must fails: incorrect #[\Override] attribute class used (eg. wrong import).
    #[\Override] #[Override]
    public function incorrectOverrideAttribute() {
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
