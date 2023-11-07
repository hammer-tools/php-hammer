<?php

new class {
    public readonly string|null $fail010;
    readonly string|null $fail020;
    /**
     * Must fail: must be de-promoted.
     * Note: phpdoc must be moved to new field.
     */
    readonly string|null $fail030;

    public function __construct(
        // Must fail: must be de-promoted.
        string|null                    $fail010 = null,

        // Must fail: must be de-promoted.
        string|null                    $fail020 = null,

        string|null                    $fail030 = null,

        // Skip: $skip010 is already null.
        protected readonly string|null $skip010 = null,
                                       $lastParameter = null
    )
    {
        $this->fail030 ??= 'abc';
        $this->fail020 ??= 'abc';
        $this->fail010 ??= 'abc';
    }
};
