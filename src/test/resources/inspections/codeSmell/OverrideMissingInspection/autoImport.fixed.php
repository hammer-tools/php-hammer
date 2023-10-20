<?php

class Base {
    function requiresOverrideAttribute() {
        doSomething();
    }
}

$dummy = new class extends Base {
    // Must fails: requires #[\Override] attribute.
    // Must occur auto import, so instead of #[\Override], must be fixed as #[Override] (plus) use \Override on top of the file.
    #[Override] function requiresOverrideAttribute() {
        doSomething();
    }
};
