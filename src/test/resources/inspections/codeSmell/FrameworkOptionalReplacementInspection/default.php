<?php

<weak_warning descr="🔨 PHP Hammer: optional() can be replaced by null safe operator.">optional(USER)</weak_warning>->id;

<weak_warning descr="🔨 PHP Hammer: optional() can be replaced by null safe operator.">optional(USER)</weak_warning>?->id;

<weak_warning descr="🔨 PHP Hammer: optional() can be replaced by null safe operator.">optional($user)</weak_warning>->id;

<weak_warning descr="🔨 PHP Hammer: optional() can be replaced by null safe operator.">optional($user)</weak_warning>?->id;

<weak_warning descr="🔨 PHP Hammer: optional() can be replaced by null safe operator.">optional($user)</weak_warning>->id();

<weak_warning descr="🔨 PHP Hammer: optional() can be replaced by null safe operator.">optional($user)</weak_warning>?->id();

<weak_warning descr="🔨 PHP Hammer: optional() can be replaced by null safe operator.">optional($user->id)</weak_warning>->id();

<weak_warning descr="🔨 PHP Hammer: optional() can be replaced by null safe operator.">optional($user->id)</weak_warning>?->id();

<weak_warning descr="🔨 PHP Hammer: optional() can be replaced by null safe operator.">optional($user->id())</weak_warning>->id();

<weak_warning descr="🔨 PHP Hammer: optional() can be replaced by null safe operator.">optional($user->id())</weak_warning>?->id();

<weak_warning descr="🔨 PHP Hammer: optional() can be replaced by null safe operator.">optional(user())</weak_warning>->id();

<weak_warning descr="🔨 PHP Hammer: optional() can be replaced by null safe operator.">optional(user())</weak_warning>?->id();

<weak_warning descr="🔨 PHP Hammer: optional() can be replaced by null safe operator.">optional(User::ID)</weak_warning>->id();

<weak_warning descr="🔨 PHP Hammer: optional() can be replaced by null safe operator.">optional(User::ID)</weak_warning>?->id();

<weak_warning descr="🔨 PHP Hammer: optional() can be replaced by null safe operator.">optional(User::id())</weak_warning>->id();

<weak_warning descr="🔨 PHP Hammer: optional() can be replaced by null safe operator.">optional(User::id())</weak_warning>?->id();

<weak_warning descr="🔨 PHP Hammer: optional() can be replaced by null safe operator.">optional(User::$id)</weak_warning>->id();

<weak_warning descr="🔨 PHP Hammer: optional() can be replaced by null safe operator.">optional(User::$id)</weak_warning>?->id();

<weak_warning descr="🔨 PHP Hammer: optional() can be replaced by null safe operator.">optional($user->id)</weak_warning>->id()->id();

<weak_warning descr="🔨 PHP Hammer: optional() can be replaced by null safe operator.">optional($user->id)</weak_warning>?->id()->id();

<weak_warning descr="🔨 PHP Hammer: optional() can be replaced by null safe operator.">optional($user->id)</weak_warning>->id()?->id();

<weak_warning descr="🔨 PHP Hammer: optional() can be replaced by null safe operator.">optional($user->id)</weak_warning>?->id()?->id();

<weak_warning descr="🔨 PHP Hammer: optional() can be replaced by null safe operator.">optional(<weak_warning descr="🔨 PHP Hammer: optional() can be replaced by null safe operator.">optional($user)</weak_warning>->id)</weak_warning>->id;

// Not applicable:

optional()->id;

optional(123)->id;

optional(false)->id;

optional(true)->id;

optional(null)->id;

optional($user);

optional($user, function () { })->id;
