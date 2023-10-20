<?php

class Base {
    function requiresOverrideAttribute() {
        doSomething();
    }
}

$dummy = new class extends Base {
    // Must fails: requires #[\Override] attribute.
    // Must occur auto import, so instead of #[\Override], must be fixed as #[Override] (plus) use \Override on top of the file.
    function <weak_warning descr="🔨 PHP Hammer: this method performs an override; consider using the #[Override] attribute.">requiresOverrideAttribute</weak_warning>() {
        doSomething();
    }
};
