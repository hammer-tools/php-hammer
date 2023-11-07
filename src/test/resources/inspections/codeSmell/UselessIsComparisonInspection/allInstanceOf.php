<?php

class DummyBase
{
}

class DummyChild extends DummyBase
{
}

// Report all:

(function () {
    $dummyInt = 123;

    $dummyInt instanceof DummyChild;
    !($dummyInt instanceof DummyChild);
    123 instanceof DummyChild;
    !(123 instanceof DummyChild);

    if ($dummyInt instanceof DummyChild) {
        assert($dummyInt instanceof DummyChild);

        return $dummyInt instanceof DummyChild;
    }
})();

(function () {
    $dummyString = DummyChild::class;

    $dummyString instanceof DummyChild;
    !($dummyString instanceof DummyChild);
})();

(function () {
    $dummyArray = [ '123' ];

    $dummyArray instanceof DummyChild;
    !($dummyArray instanceof DummyChild);
})();

(function () {
    $dummyBooleanTrue = true;

    $dummyBooleanTrue instanceof DummyChild;
    !($dummyBooleanTrue instanceof DummyChild);
})();

(function () {
    $dummyBooleanFalse = false;

    $dummyBooleanFalse instanceof DummyChild;
    !($dummyBooleanFalse instanceof DummyChild);
})();

(function () {
    $dummyNull = null;

    $dummyNull instanceof DummyChild;
    !($dummyNull instanceof DummyChild);
})();

(function () {
    $dummyObject = new DummyBase();

    $dummyObject instanceof DummyChild;
    !($dummyObject instanceof DummyChild);
    $dummyObject instanceof DummyBase;
    !($dummyObject instanceof DummyBase);
    $dummyObject instanceof SplFileInfo;
    !($dummyObject instanceof SplFileInfo);

    if ($dummyInt instanceof DummyBase) {
        assert($dummyInt instanceof DummyBase);

        return $dummyInt instanceof DummyBase;
    }
})();

(function () {
    $dummyObject = new DummyChild();

    $dummyObject instanceof DummyChild;
    !($dummyObject instanceof DummyChild);
    $dummyObject instanceof DummyBase;
    !($dummyObject instanceof DummyBase);
    $dummyObject instanceof SplFileInfo;
    !($dummyObject instanceof SplFileInfo);
})();

(function () {
    $dummyResource = getResource();

    $dummyResource instanceof DummyChild;
    !($dummyResource instanceof DummyChild);
})();

(function () {
    getResource() instanceof DummyChild;
    !(getResource() instanceof DummyChild);
})();

(function () {
    /** @var int|null $dummyIntNullable */
    $dummyIntNullable = 123;

    $dummyIntNullable instanceof DummyChild;
    !($dummyIntNullable instanceof DummyChild);
})();

(function () {
    /** @var int|object $dummyIntObject */
    $dummyIntObject = 123;

    $dummyIntObject instanceof DummyChild;
    !($dummyIntObject instanceof DummyChild);
})();

(function () {
    /** @var int|DummyChild $dummyIntObject */
    $dummyIntObject = 123;

    $dummyIntObject instanceof DummyChild;
    !($dummyIntObject instanceof DummyChild);
})();

(function () {
    $dummyResourceOrFalse = getResourceOrFalse();

    $dummyResourceOrFalse instanceof DummyChild;
    !($dummyResourceOrFalse instanceof DummyChild);
})();

// Skip all: cannot resolve type, so just accept it.

(function () {
    /** @var mixed $dummyMixed */
    $dummyMixed = 123;

    $dummyMixed instanceof DummyChild;
    !($dummyMixed instanceof DummyChild);
})();

(function ($dummyUnknown) {
    $dummyUnknown instanceof DummyChild;
    !($dummyUnknown instanceof DummyChild);
})();
