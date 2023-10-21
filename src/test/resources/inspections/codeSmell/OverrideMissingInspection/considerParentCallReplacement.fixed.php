<?php

use Namespaced\Override as Override;

class Base {
    function notParentCallReplacement() {
        doSomething();
    }

    function parentCallReplacement() {
        doSomething();
    }
}

$dummy = new class extends Base {
    // Must fails: not contains parent call, so requires #[\Override] attribute.
    #[\Override] function notParentCallReplacement() {
        doSomething();
        parent::parentCallReplacement(); // Another parent method, not itself.

        return new class extends Base {
            // Skip: apart case that calls parent correctly, must not confuses parent scope issue.
            function notParentCallReplacement() {
                doSomething();
                parent::notParentCallReplacement();
            }
        };
    }

    // Skip: parent call replaces #[\Override] attribute.
    function parentCallReplacement() {
        doSomething();
        PARENT::ParentCallReplacement();
    }
};
