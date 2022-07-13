<?php

$dummy = array_map(strlen(...), []);

$dummy = array_map(strlen(...), []);

$dummy = array_map(strlen(...), []);

$dummy = array_map(strlen(...), []);

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
