<?php

$dummy = <weak_warning descr="🔨 PHP Hammer: Null check must be via comparison.">is_null($dummy)</weak_warning>;

$dummy = <weak_warning descr="🔨 PHP Hammer: Null check must be via comparison.">!is_null($dummy)</weak_warning>;

$dummy = <weak_warning descr="🔨 PHP Hammer: Null check must be via comparison.">is_null($dummy = $dummy)</weak_warning>;

$dummy = $dummy === null;

$dummy = $dummy !== null;

$dummy = ($dummy = $dummy) === null;

$dummy = null === $dummy;

$dummy = null !== $dummy;

$dummy = null !== ($dummy = $dummy);

// Not applicable:

$dummy = $dummy == null;

$dummy = $dummy != null;
