<?php

// Must be reported.
$dummy = function (#[SensitiveParameter] $password) {
};

// Must report all.
$dummy = function (
    #[SensitiveParameter] $passwords, // "password" plural
    #[SensitiveParameter] $ssn, // common
    #[SensitiveParameter] $SSN, // upper case
    #[SensitiveParameter] $session_id, // snake_case
    #[SensitiveParameter] $Sessions_ID, // snake_case
    #[SensitiveParameter] $sessionId, // camelCase
    #[SensitiveParameter] $SessionId, // PascalCase
    #[SensitiveParameter] $myPrivateKey, // two-words
    #[SensitiveParameter] $bearerToken, // Multiple sensitive words
    #[SensitiveParameter] $bearerTokenSecret, // A lot of sensitive words
    #[SensitiveParameter] $bearerTokenSecretHash, // A lot more of sensitive words
) {
};

// Must report all: applicable to abstract classes, but non-abstract method.
abstract class Dummy1000
{
    function dummy(#[SensitiveParameter] string $secret)
    {
    }
}

// Skip all: not using sensitive words.
$dummy = function (
    $notReport,
    $authenticated, // not "auth" or "authentication", specifically.
) {
};

// Skip all: bool, int and float types.
/** @param int|null $lastCode */
$dummy = function (
    bool  $secret,
    int   $code,
    float $hash,
          $lastCode,
          $firstCode = 0
) {
};

// Skip all: hard to understand each word.
$dummy = function (
    $SeSSiON,
    $SeSSiONId,
) {
};

// Skip all: using attribute.
$dummy = function (
    #[\SensitiveParameter] $session,
) {
};

// Skip all: not supported for phpdoc.
/** @method example(string $session) */

// Skip all: not applicable to abstract methods.
abstract class Dummy1010
{
    abstract function dummy(string $secret);
}
