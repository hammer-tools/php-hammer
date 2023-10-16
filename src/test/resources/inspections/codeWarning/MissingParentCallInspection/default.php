<?php

// Case 1000:

class Dummy1000_A {
    /** intentionally empty */
    public function method() {
    }
}

class Dummy1000_B extends Dummy1000_A {
    public function method() {
        doSomething();
    }
}

class Dummy1000_C extends Dummy1000_B {
    /** parent call is required, because Dummy1000_B::method() is not empty. */
    public function <warning descr="🔨 PHP Hammer: missing parent::method() call.">method</warning>() {
    }
}

// Case 1005:

class Dummy1005_A {
    /** intentionally empty */
    public function method() {
    }
}

abstract class Dummy1005_B extends Dummy1005_A {
    public function method() {
        doSomething();
    }
}

class Dummy1005_C extends Dummy1005_B {
    /** parent call is required, because Dummy1005_B::method() is not empty. */
    public function <warning descr="🔨 PHP Hammer: missing parent::method() call.">method</warning>() {
    }
}

// Case 1006:

class Dummy1006_A {
    /** intentionally empty */
    public function method() {
    }
}

class Dummy1006_B extends Dummy1006_A {
    public function method() {
        doSomething();
    }
}

abstract class Dummy1006_C extends Dummy1006_B {
    /** parent call is required, because Dummy1006_B::method() is not empty. */
    public function <warning descr="🔨 PHP Hammer: missing parent::method() call.">method</warning>() {
    }
}

// Case 1007:

abstract class Dummy1007_A {
    /** intentionally empty */
    public function method() {
    }
}

abstract class Dummy1007_B extends Dummy1007_A {
    public function method() {
        doSomething();
    }
}

abstract class Dummy1007_C extends Dummy1007_B {
    /** parent call is required, because Dummy1007_B::method() is not empty. */
    public function <warning descr="🔨 PHP Hammer: missing parent::method() call.">method</warning>() {
    }
}

// Case 1010:

class Dummy1010_A {
    /** intentionally empty */
    public function method() {
    }
}

class Dummy1010_B extends Dummy1010_A {
    public function method() {
        doSomething();
    }
}

class Dummy1010_C extends Dummy1010_B {
    /** parent call is required, because Dummy1010_B::method() is not empty. */
    public function <warning descr="🔨 PHP Hammer: missing parent::method() call.">method</warning>() {
        doSomething();
        doSomething();
    }
}

// Case 1020:

class Dummy1020_A {
    /** intentionally empty */
    public function method() {
    }
}

class Dummy1020_B extends Dummy1020_A {
    public function method($a, $b = 1, ...$c) {
        doSomething($a, $b, ...$c);
    }
}

class Dummy1020_C extends Dummy1020_B {
    /** parent call is required, because Dummy1020_B::method() is not empty. */
    public function <warning descr="🔨 PHP Hammer: missing parent::method() call.">method</warning>($a, $b = 1, ...$c) {
        doSomething();
        doSomething();
    }
}

// Case 1021:

class Dummy1021_A {
    /** intentionally empty */
    public function method() {
    }
}

class Dummy1021_B extends Dummy1021_A {
    public function method($a, $b = 1) {
        doSomething($a, $b);
    }
}

class Dummy1021_C extends Dummy1021_B {
    /** parent call is required, because Dummy1021_B::method() is not empty. */
    public function <warning descr="🔨 PHP Hammer: missing parent::method() call.">method</warning>($a, $b = 1, ...$c) {
        doSomething();
        doSomething();
    }
}

// Case 1022:

class Dummy1022_A {
    /** intentionally empty */
    public function method() {
    }
}

class Dummy1022_B extends Dummy1022_A {
    public function method($a, $b) {
        doSomething($a, $b);
    }
}

class Dummy1022_C extends Dummy1022_B {
    /** parent call is required, because Dummy1022_B::method() is not empty. */
    public function <warning descr="🔨 PHP Hammer: missing parent::method() call.">method</warning>($a) {
        doSomething();
        doSomething();
    }
}

// Case 1030:

class Dummy1030_A {
    public function method() {
        doSomething();
    }
}

class Dummy1030_B extends Dummy1030_A {
    /** parent call is required, because Dummy1030_A::method() is not empty. */
    public function <warning descr="🔨 PHP Hammer: missing parent::method() call.">method</warning>() {
    }
}

class Dummy1030_C extends Dummy1030_B {
    /** parent call is required, because Dummy1030_B::method() is not empty (after the previous quick-fix). */
    public function method() {
    }
}

// Case 1040:

class Dummy1040_A {
    /** intentionally empty */
    public function method() {
    }
}

class Dummy1040_B extends Dummy1040_A {
    public function method() {
        doSomething();
    }
}

class Dummy1040_C extends Dummy1040_B {
    /** parent call is required, because Dummy1040_B::method() is not empty. */
    /** parent::call() is called, but not from base scope. */
    public function <warning descr="🔨 PHP Hammer: missing parent::method() call.">method</warning>() {
        return new class extends Dummy1040_B {
            public function method() {
                parent::method();
            }
        };
    }
}

// Case 1050:

class Dummy1050_A {
    /** intentionally empty */
    public function method() {
    }
}

class Dummy1050_B extends Dummy1050_A {
    public function method() {
        doSomething();
    }
}

class Dummy1050_C extends Dummy1050_B {
    /** parent::call() is called. */
    public function method() {
        parent::method();

        return new class extends Dummy1050_B {
            /** parent::call() is not called, but need. */
            public function <warning descr="🔨 PHP Hammer: missing parent::method() call.">method</warning>() {
            }
        };
    }
}

// Case 1060:

trait Dummy1060_Trait
{
    public function testOriginalA()
    {
        doSomething();
    }

    public function testOriginalB()
    {
        doSomething();
    }

    public function testOriginalC()
    {
    }
}

class Dummy1060_A
{
    use Dummy1060_Trait {
        testOriginalA as testCopiedA;
        testOriginalB as testCopiedB;
        testOriginalC as testCopiedC;
    }
}

class Dummy1060_B extends Dummy1060_A
{
    public function <warning descr="🔨 PHP Hammer: missing parent::testOriginalA() call.">testOriginalA</warning>()
    {
    }

    // Empty from trait, so don't need parent call.
    public function testOriginalC()
    {
    }

    public function <warning descr="🔨 PHP Hammer: missing parent::testCopiedA() call.">testCopiedA</warning>()
    {
    }

    // Empty from trait, so don't need parent call.
    public function testCopiedC()
    {
    }
}

class Dummy1060_C extends Dummy1060_B
{
    public function <warning descr="🔨 PHP Hammer: missing parent::testOriginalB() call.">testOriginalB</warning>()
    {
    }

    public function <warning descr="🔨 PHP Hammer: missing parent::testCopiedB() call.">testCopiedB</warning>()
    {
    }
}

// Not applicable:
// Case 9000:

class Dummy9000_A {
    /** intentionally empty */
    public function method() {
    }
}

class Dummy9000_B extends Dummy9000_A {
}

class Dummy9000_C extends Dummy9000_B {
    public function method() {
        /** parent call is not required, because Dummy9000_A::method() is empty. */
    }
}

// Case 9010:

class Dummy9010_A {
    /** intentionally empty */
    public function method() {
    }
}

class Dummy9010_B extends Dummy9010_A {
    /** intentionally empty */
    /** parent call is not required, because Dummy9010_A::method() is empty. */
    public function method() {
    }
}

class Dummy9010_C extends Dummy9010_B {
    /** parent call is not required, because Dummy9010_B::method() is empty. */
    public function method() {
    }
}

// Case 9020:

class Dummy9020_A {
    /** intentionally empty */
    public function method() {
    }
}

class Dummy9020_B extends Dummy9020_A {
    /** parent call is not required, because Dummy9020_A::method() is empty. */
    public function method() {
        doSomething();
    }
}

class Dummy9020_C extends Dummy9020_B {
    /** parent call declared, so no problem. */
    public function method() {
        doSomething();
        parent::method();
        doSomething();
    }
}

// Case 9030:

class Dummy9030_A {
    /** intentionally empty */
    public function method() {
    }
}

class Dummy9030_B extends Dummy9030_A {
    /** parent call is not required, because Dummy9030_A::method() is empty. */
    public function method() {
        doSomething();
    }
}

class Dummy9030_C extends Dummy9030_B {
    /** parent call declared, so no problem. */
    public function method() {
        doSomething();
        parent::method();
        doSomething();
    }
}

// Case 9040:

class Dummy9040_A {
    /** intentionally empty */
    public function method() {
    }
}

class Dummy9040_B extends Dummy9040_A {
    /** parent call is not required, because Dummy9040_A::method() is empty. */
    public function method() {
        doSomething();
    }
}

class Dummy9040_C extends Dummy9040_B {
    /** parent call declared, so no problem, even inside if(). */
    public function method() {
        if (doSomething()) {
            parent::method();
        }
    }
}

// Case 9050:

class Dummy9050_A {
    /** intentionally empty */
    public function method() {
    }
}

class Dummy9050_B extends Dummy9050_A {
    public function method() {
        doSomething();
    }
}

class Dummy9050_C extends Dummy9050_B {
    /** parent call declared, so no problem. */
    public function method() {
        parent::method();

        return new class extends Dummy9050_B {
            /** parent call declared, so no problem. */
            public function method() {
                parent::method();
            }
        };
    }
}

// Case 9060:

class Dummy9060_A {
    /** intentionally empty */
    public function method() {
    }
}

class Dummy9060_B extends Dummy9060_A {
    public function method() {
        doSomething();
    }
}

class Dummy9060_C extends Dummy9060_B {
    /** parent call declared, but UPPERCASE, no problem. */
    public function method() {
        parent::METHOD();
    }
}

// Case 9070:

class Dummy9070_A {
    /** intentionally empty */
    public function method() {
    }
}

class Dummy9070_B extends Dummy9070_A {
    public function method() {
        return null;
    }
}

class Dummy9070_C extends Dummy9070_B {
    /** parent call is not required, because parent just returns something. */
    public function method() {
    }
}

// Case 9080:

trait Dummy9080_A {
    /** parent call is not required, because it is a trait. */
    public function method() {
    }
}

class Dummy9080_B {
    use Dummy9080_A;
}

// Case 9100:

abstract class Dummy9100_A {
    abstract public function method();
}

class Dummy9100_C extends Dummy9100_A {
    /** parent call is not required, because parent is abstract. */
    public function method() {
    }
}

// Case 9120:

class Dummy9120_A {
    /** intentionally empty */
    public function method() {
    }
}

class Dummy9120_B extends Dummy9120_A {
    public function method() {
        /** Comments must be ignored. */
        // Comments must be ignored.
        # Comments must be ignored.
    }
}

class Dummy9120_C extends Dummy9120_B {
    /** parent call is not required, because parent is empty (having just comments). */
    public function method() {
    }
}

// Case 9130:

class Dummy9130_A {
    public function method() {
        doSomething();
    }
}

abstract class Dummy9130_B extends Dummy9130_A {
    /** parent call is not supported to abstract methods. */
    abstract public function method();
}
