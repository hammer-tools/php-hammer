<?php

$dummy = array_map(<weak_warning descr="🔨 PHP Hammer: call to array_map() can be replaced by first-class callback.">function ($dummy) {
    return strlen($dummy);
}</weak_warning>, []);

$dummy = array_map(<weak_warning descr="🔨 PHP Hammer: call to array_map() can be replaced by first-class callback.">static function ($dummy) {
    return strlen($dummy);
}</weak_warning>, []);

$dummy = array_map(<weak_warning descr="🔨 PHP Hammer: call to array_map() can be replaced by first-class callback.">fn($dummy) => strlen($dummy)</weak_warning>, []);

$dummy = array_map(<weak_warning descr="🔨 PHP Hammer: call to array_map() can be replaced by first-class callback.">static fn($dummy) => strlen($dummy)</weak_warning>, []);

// Not applicable:

$dummy = array_map(static fn() => 123, []);

$dummy = array_map(static fn() => strlen('123'), []);

$dummy = array_map(static fn($self) => strlen($self) + 1, []);

$dummy = array_map(static fn($self) => array_merge($self, $self), []);

$dummy = array_map(static fn($dummy) => strlen(... $dummy), []);

$dummy = array_map(static function ($self) {
    if ($self) {
    }

    return strlen($self);
}, []);

$dummy = array_map(static function ($dummy) {
    strlen($dummy);
}, []);
