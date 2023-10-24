<?php

use Namespaced\Override as Override;

class Base {
    function requiresOverrideAttribute() {
        doSomething();
    }

    function requiresOverrideAttributeB() {
        doSomething();
    }

    function incorrectOverrideAttribute() {
        doSomething();
    }

    function overrideAttributeAlreadyApplied() {
        doSomething();
    }
}

class Child extends Base {
    use ExampleTrait;
    use PartiallyOverrideTrait;
}

trait ExampleTrait {
    // Must fails: requires #[\Override] attribute because it only override Base::requiresOverrideAttribute() at Child class.
    #[\Override] function requiresOverrideAttribute() {
        doSomething();
    }

    // Skip: doesn't requires #[\Override] attribute.
    function notRequiresOverridden() {
        doSomething();
    }
}

class NotChild {
    use PartiallyOverrideTrait;
}

trait PartiallyOverrideTrait {
    // Skip: doesn't requires #[\Override] attribute because it overrides Base::requiresOverrideAttributeB() at Child class, but not for NotChild.
    // Note: to be considered an override, it needs to be an override for all methods that use the trait.
    function requiresOverrideAttributeB() {
        doSomething();
    }
}

$dummy = new class extends Base {
    // Must fails: requires #[\Override] attribute.
    #[\Override] function requiresOverrideAttribute() {
        doSomething();
        parent::requiresOverrideAttribute();
    }

    // Must fails: incorrect #[\Override] attribute class used (eg. wrong import).
    #[\Override] #[Override]
    public function incorrectOverrideAttribute() {
        doSomething();
        parent::incorrectOverrideAttribute();
    }

    // Skip: already correctly contains #[\Override] attribute.
    #[\Override]
    function overrideAttributeAlreadyApplied() {
        doSomething();
        parent::overrideAttributeAlreadyApplied();
    }

    // Skip: #[\Override] attribute is not required here, parent classes don't contains this method declaration.
    function notRequiresOverridden() {
        doSomething();
    }
};
