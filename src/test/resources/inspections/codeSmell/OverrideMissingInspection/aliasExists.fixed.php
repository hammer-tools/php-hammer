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
    #[Override] function requiresOverrideAttribute() {
        doSomething();
    }
};
