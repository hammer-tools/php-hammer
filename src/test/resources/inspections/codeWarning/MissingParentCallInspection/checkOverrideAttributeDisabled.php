<?php

class Dummy1080_A {
    public function method() {
        doSomething();
    }
}

class Dummy1080_B extends Dummy1080_A {
    // Skip: when "check even with the #[Override] attribute" is disabled, parent call is not required.
    #[\Override]
    public function method() {
        doSomething();
    }
}
