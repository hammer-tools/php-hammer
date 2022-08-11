<?php

$example = new \Example;

$dummy1000 = $example <weak_warning descr="🔨 PHP Hammer: this operator can be replaced by null-safe operator.">&&</weak_warning>
             $example->isYesterday();

if ($example <weak_warning descr="🔨 PHP Hammer: this operator can be replaced by null-safe operator.">&&</weak_warning>
    $example->isYesterday()) {
}

$dummy2000 = $example <weak_warning descr="🔨 PHP Hammer: this operator can be replaced by null-safe operator.">&&</weak_warning>
             $example->isTomorrow();

if ($example <weak_warning descr="🔨 PHP Hammer: this operator can be replaced by null-safe operator.">&&</weak_warning>
    $example->isTomorrow()) {
}

$dummy3000 = $example <weak_warning descr="🔨 PHP Hammer: this operator can be replaced by null-safe operator.">&&</weak_warning>
             $dummy &&
             $example->isTomorrow();

if ($example <weak_warning descr="🔨 PHP Hammer: this operator can be replaced by null-safe operator.">&&</weak_warning>
    $dummy &&
    $example->isTomorrow()) {
}

$dummy4000 = $example <weak_warning descr="🔨 PHP Hammer: this operator can be replaced by null-safe operator.">&&</weak_warning>
             $example->self <weak_warning descr="🔨 PHP Hammer: this operator can be replaced by null-safe operator.">&&</weak_warning>
             $example->self->isTomorrow();

if ($example <weak_warning descr="🔨 PHP Hammer: this operator can be replaced by null-safe operator.">&&</weak_warning>
    $example->self <weak_warning descr="🔨 PHP Hammer: this operator can be replaced by null-safe operator.">&&</weak_warning>
    $example->self->isTomorrow()) {
}

$dummy4100 = $example?->self <weak_warning descr="🔨 PHP Hammer: this operator can be replaced by null-safe operator.">&&</weak_warning>
             $example->self?->isTomorrow();

if ($example?->self <weak_warning descr="🔨 PHP Hammer: this operator can be replaced by null-safe operator.">&&</weak_warning>
    $example->self?->isTomorrow()) {
}

$dummy5000 = $example <weak_warning descr="🔨 PHP Hammer: this operator can be replaced by null-safe operator.">&&</weak_warning>
             $dummy &&
             $example->self <weak_warning descr="🔨 PHP Hammer: this operator can be replaced by null-safe operator.">&&</weak_warning>
             $example->self->isTomorrow();

if ($example <weak_warning descr="🔨 PHP Hammer: this operator can be replaced by null-safe operator.">&&</weak_warning>
    $dummy &&
    $example->self <weak_warning descr="🔨 PHP Hammer: this operator can be replaced by null-safe operator.">&&</weak_warning>
    $example->self->isTomorrow()) {
}

$dummy5100 = $example?->self <weak_warning descr="🔨 PHP Hammer: this operator can be replaced by null-safe operator.">&&</weak_warning>
             $dummy?->dummy &&
             $example->self?->isTomorrow();

if ($example?->self <weak_warning descr="🔨 PHP Hammer: this operator can be replaced by null-safe operator.">&&</weak_warning>
    $dummy?->dummy &&
    $example->self?->isTomorrow()) {
}

$dummy6000 = $example->self <weak_warning descr="🔨 PHP Hammer: this operator can be replaced by null-safe operator.">&&</weak_warning>
             $example->self->isTomorrow();

if ($example->self <weak_warning descr="🔨 PHP Hammer: this operator can be replaced by null-safe operator.">&&</weak_warning>
    $example->self->isTomorrow()) {
}

$dummy7000 = $example->self <weak_warning descr="🔨 PHP Hammer: this operator can be replaced by null-safe operator.">&&</weak_warning>
             $dummy->dummy &&
             $example->self->isTomorrow();

if ($example->self <weak_warning descr="🔨 PHP Hammer: this operator can be replaced by null-safe operator.">&&</weak_warning>
    $dummy->dummy &&
    $example->self->isTomorrow()) {
}

$dummy8000 = $example?->self <weak_warning descr="🔨 PHP Hammer: this operator can be replaced by null-safe operator.">&&</weak_warning>
             $example->self->isTomorrow();

if ($example?->self <weak_warning descr="🔨 PHP Hammer: this operator can be replaced by null-safe operator.">&&</weak_warning>
    $example->self->isTomorrow()) {
}

$dummy9000 = $example?->self <weak_warning descr="🔨 PHP Hammer: this operator can be replaced by null-safe operator.">&&</weak_warning>
             $dummy?->dummy &&
             $example->self->isTomorrow();

if ($example?->self <weak_warning descr="🔨 PHP Hammer: this operator can be replaced by null-safe operator.">&&</weak_warning>
    $dummy?->dummy &&
    $example->self->isTomorrow()) {
}

$dummy10000 = $example <weak_warning descr="🔨 PHP Hammer: this operator can be replaced by null-safe operator.">&&</weak_warning>
              $example->self;

if ($example <weak_warning descr="🔨 PHP Hammer: this operator can be replaced by null-safe operator.">&&</weak_warning>
    $example->self) {
}

$dummy11000 = $dummy &&
              $example <weak_warning descr="🔨 PHP Hammer: this operator can be replaced by null-safe operator.">&&</weak_warning>
              $example->self;

if ($dummy &&
    $example <weak_warning descr="🔨 PHP Hammer: this operator can be replaced by null-safe operator.">&&</weak_warning>
    $example->self) {
}

$dummy11100 = $dummy <weak_warning descr="🔨 PHP Hammer: this operator can be replaced by null-safe operator.">&&</weak_warning>
              $example?->self &&
              $dummy?->dummy;

if ($dummy <weak_warning descr="🔨 PHP Hammer: this operator can be replaced by null-safe operator.">&&</weak_warning>
    $example?->self &&
    $dummy?->dummy) {
}

$dummy11200 = $example &&
              $dummy <weak_warning descr="🔨 PHP Hammer: this operator can be replaced by null-safe operator.">&&</weak_warning>
              $dummy->dummy;

if ($example &&
    $dummy <weak_warning descr="🔨 PHP Hammer: this operator can be replaced by null-safe operator.">&&</weak_warning>
    $dummy->dummy) {
}

$dummy12000 = $example <weak_warning descr="🔨 PHP Hammer: this operator can be replaced by null-safe operator.">&&</weak_warning>
              $dummy &&
              $example?->self;

if ($example <weak_warning descr="🔨 PHP Hammer: this operator can be replaced by null-safe operator.">&&</weak_warning>
    $dummy &&
    $example?->self) {
}

$dummy13000 = $example?->self <weak_warning descr="🔨 PHP Hammer: this operator can be replaced by null-safe operator.">&&</weak_warning>
              $example?->self?->isTomorrow();

if ($example?->self <weak_warning descr="🔨 PHP Hammer: this operator can be replaced by null-safe operator.">&&</weak_warning>
    $example?->self?->isTomorrow()) {
}

$dummy14000 = $example <weak_warning descr="🔨 PHP Hammer: this operator can be replaced by null-safe operator.">&&</weak_warning>
              $example->self &&
              $example->self;

if ($example <weak_warning descr="🔨 PHP Hammer: this operator can be replaced by null-safe operator.">&&</weak_warning>
    $example->self &&
    $example->self) {
}

$dummy15000 = Example::$selfStatic <weak_warning descr="🔨 PHP Hammer: this operator can be replaced by null-safe operator.">&&</weak_warning>
              Example::$selfStatic->self &&
              Example::$selfStatic->self;

if (Example::$selfStatic <weak_warning descr="🔨 PHP Hammer: this operator can be replaced by null-safe operator.">&&</weak_warning>
    Example::$selfStatic->self &&
    Example::$selfStatic->self) {
}

$dummy16000 = Example::$selfStatic <weak_warning descr="🔨 PHP Hammer: this operator can be replaced by null-safe operator.">&&</weak_warning>
              Example::$selfStatic->self &&
              Example::$selfStatic->self;

if (Example::$selfStatic <weak_warning descr="🔨 PHP Hammer: this operator can be replaced by null-safe operator.">&&</weak_warning>
    Example::$selfStatic->self &&
    Example::$selfStatic->self) {
}

$notApplicable17000 = $example <weak_warning descr="🔨 PHP Hammer: this operator can be replaced by null-safe operator.">&&</weak_warning>
                      $example->self &&
                      $example->isTomorrow();

if ($example <weak_warning descr="🔨 PHP Hammer: this operator can be replaced by null-safe operator.">&&</weak_warning>
    $example->self &&
    $example->isTomorrow()) {
}

$dummy18000 = $example <weak_warning descr="🔨 PHP Hammer: this operator can be replaced by null-safe operator.">&&</weak_warning>
              $example->self &&
              $example->isTomorrow() ? true : false;

$dummy19000 = $example <weak_warning descr="🔨 PHP Hammer: this operator can be replaced by null-safe operator.">&&</weak_warning>
              $example->self->self->isTomorrow();

$dummy20000 = $example->self <weak_warning descr="🔨 PHP Hammer: this operator can be replaced by null-safe operator.">&&</weak_warning>
              $example->self->self->isTomorrow();

$dummy21000 = ($example <weak_warning descr="🔨 PHP Hammer: this operator can be replaced by null-safe operator.">&&</weak_warning> $example->self) ? true : false;

$dummy21100 = ($example <weak_warning descr="🔨 PHP Hammer: this operator can be replaced by null-safe operator.">&&</weak_warning> $example->self) || $example ? true : false;

$dummy21200 = $example || ($example <weak_warning descr="🔨 PHP Hammer: this operator can be replaced by null-safe operator.">&&</weak_warning> $example->self) ? true : false;

$dummy21300 = ($example <weak_warning descr="🔨 PHP Hammer: this operator can be replaced by null-safe operator.">&&</weak_warning> $example->self) && $example ? true : false;

$dummy21400 = $example && ($example <weak_warning descr="🔨 PHP Hammer: this operator can be replaced by null-safe operator.">&&</weak_warning> $example->self) ? true : false;

// Not applicable:

$notApplicable1000 = $example instanceof Example &&
                     $example->isTomorrow();

if ($example instanceof Example &&
    $example->isTomorrow()) {
}

$notApplicable2000 = Example::getSelfStatic() &&
                     Example::getSelfStatic()->self &&
                     Example::getSelfStatic()->self;

if (Example::getSelfStatic() &&
    Example::getSelfStatic()->self &&
    Example::getSelfStatic()->self) {
}

$notApplicable3000 = $example->getSelf() &&
                     $example->getSelf()->self &&
                     $example->getSelf()->self;

if ($example->getSelf() &&
    $example->getSelf()->self &&
    $example->getSelf()->self) {
}

$notApplicable4000 = $example || $example->self || $example->isTomorrow();

if ($example || $example->self || $example->isTomorrow()) {
}
