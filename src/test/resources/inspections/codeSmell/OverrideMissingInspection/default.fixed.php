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

    protected function protectedRequiresOverrideAttribute() {
        doSomething();
    }

    private function privateIncorrectOverrideAttribute() {
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

    // Skip: doesn't requires #[\Override] attribute, because it doesn't overrides Base::privateIncorrectOverrideAttribute() actually.
    function privateIncorrectOverrideAttribute() {
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

    // Must fails: requires #[\Override] attribute for protected method.
    #[\Override] public function protectedRequiresOverrideAttribute() {
        doSomething();
        parent::protectedRequiresOverrideAttribute();
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

    // Skip: #[\Override] attribute is not required here, parent method is private.
    function privateIncorrectOverrideAttribute() {
        doSomething();
    }
};
