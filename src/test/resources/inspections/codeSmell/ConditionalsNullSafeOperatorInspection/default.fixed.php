<?php

$example = new \Example;

$dummy1000 = (bool)$example?->isYesterday();

if ($example?->isYesterday()) {
}

$dummy2000 = (bool)$example?->isTomorrow();

if ($example?->isTomorrow()) {
}

$dummy3000 = $dummy &&
             $example?->isTomorrow();

if ($dummy &&
    $example?->isTomorrow()) {
}

$dummy4000 = (bool)$example?->self?->isTomorrow();

if ($example?->self?->isTomorrow()) {
}

$dummy4100 = (bool)$example?->self?->isTomorrow();

if ($example?->self?->isTomorrow()) {
}

$dummy5000 = $dummy &&
             $example?->self?->isTomorrow();

if ($dummy &&
    $example?->self?->isTomorrow()) {
}

$dummy5100 = $dummy?->dummy &&
             $example?->self?->isTomorrow();

if ($dummy?->dummy &&
    $example?->self?->isTomorrow()) {
}

$dummy6000 = (bool)$example->self?->isTomorrow();

if ($example->self?->isTomorrow()) {
}

$dummy7000 = $dummy->dummy &&
             $example->self?->isTomorrow();

if ($dummy->dummy &&
    $example->self?->isTomorrow()) {
}

$dummy8000 = (bool)$example?->self?->isTomorrow();

if ($example?->self?->isTomorrow()) {
}

$dummy9000 = $dummy?->dummy &&
             $example?->self?->isTomorrow();

if ($dummy?->dummy &&
    $example?->self?->isTomorrow()) {
}

$dummy10000 = (bool)$example?->self;

if ($example?->self) {
}

$dummy11000 = $dummy &&
              $example?->self;

if ($dummy &&
    $example?->self) {
}

$dummy11100 = $example?->self &&
              $dummy?->dummy;

if ($example?->self &&
    $dummy?->dummy) {
}

$dummy11200 = $example &&
              $dummy?->dummy;

if ($example &&
    $dummy?->dummy) {
}

$dummy12000 = $dummy &&
              $example?->self;

if ($dummy &&
    $example?->self) {
}

$dummy13000 = (bool)$example?->self?->isTomorrow();

if ($example?->self?->isTomorrow()) {
}

$dummy14000 = $example?->self &&
              $example?->self;

if ($example?->self &&
    $example?->self) {
}

$dummy15000 = Example::$selfStatic?->self &&
              Example::$selfStatic?->self;

if (Example::$selfStatic?->self &&
    Example::$selfStatic?->self) {
}

$dummy16000 = Example::$selfStatic?->self &&
              Example::$selfStatic?->self;

if (Example::$selfStatic?->self &&
    Example::$selfStatic?->self) {
}

$notApplicable17000 = $example?->self &&
                      $example?->isTomorrow();

if ($example?->self &&
    $example?->isTomorrow()) {
}

$dummy18000 = $example?->self &&
              $example?->isTomorrow() ? true : false;

$dummy19000 = (bool)$example?->self->self->isTomorrow();

$dummy20000 = (bool)$example->self?->self->isTomorrow();

$dummy21000 = ((bool)$example?->self) ? true : false;

$dummy21100 = ((bool)$example?->self) || $example ? true : false;

$dummy21200 = $example || ((bool)$example?->self) ? true : false;

$dummy21300 = ((bool)$example?->self) && $example ? true : false;

$dummy21400 = $example && ((bool)$example?->self) ? true : false;

$dummy22000 = $dummy === null &&
              $example->self?->isTomorrow();

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

$notApplicable5000 = $example->self &&
                     $example->self->self !== null &&
                     $example->self->isTomorrow();

$notApplicable6000 = $example->self &&
                     dummy($example?->self) &&
                     $example->self->isTomorrow();
