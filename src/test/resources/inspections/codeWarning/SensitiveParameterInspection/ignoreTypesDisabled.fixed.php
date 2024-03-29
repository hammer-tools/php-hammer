<?php

// Must report all: bool, int and float types ignore option is disabled.
/** @param int|null $lastCode */
$dummy = function (
    #[SensitiveParameter] bool  $secret,
    #[SensitiveParameter] int   $code,
    #[SensitiveParameter] float $hash,
    #[SensitiveParameter]       $lastCode,
    #[SensitiveParameter] $firstCode = 0
) {
};

// Must report all: true and false.
/**
 * @param true $secret
 * @param false $code
 */
function test(#[SensitiveParameter] $secret, #[SensitiveParameter] $code) {
}
