<?php

new class {
    public function __construct(
        // Must fail: must be de-promoted.
        <weak_warning descr="🔨 PHP Hammer: default value of the parameter must be \"null\".">public readonly string $fail010 = 'abc'</weak_warning>,

        // Must fail: must be de-promoted.
        <weak_warning descr="🔨 PHP Hammer: default value of the parameter must be \"null\".">readonly string $fail020 = 'abc'</weak_warning>,

        /**
         * Must fail: must be de-promoted.
         * Note: phpdoc must be moved to new field.
         */
        <weak_warning descr="🔨 PHP Hammer: default value of the parameter must be \"null\".">readonly string $fail030 = 'abc'</weak_warning>,

        // Skip: $skip010 is already null.
        protected readonly string|null $skip010 = null,
        $lastParameter = null
    ) {
    }
};
