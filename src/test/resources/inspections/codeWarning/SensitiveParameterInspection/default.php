<?php

// Must be reported.
$dummy = function (<warning descr="🔨 PHP Hammer: the sensitive word \"password\" was used.">$password</warning>) {
};

// Must report all.
$dummy = function (
    <warning descr="🔨 PHP Hammer: the sensitive word \"password\" was used.">$passwords</warning>, // "password" plural
    <warning descr="🔨 PHP Hammer: the sensitive word \"ssn\" was used.">$ssn</warning>, // common
    <warning descr="🔨 PHP Hammer: the sensitive word \"ssn\" was used.">$SSN</warning>, // upper case
    <warning descr="🔨 PHP Hammer: the sensitive word \"session\" was used.">$session_id</warning>, // snake_case
    <warning descr="🔨 PHP Hammer: the sensitive word \"session\" was used.">$Sessions_ID</warning>, // snake_case
    <warning descr="🔨 PHP Hammer: the sensitive word \"session\" was used.">$sessionId</warning>, // camelCase
    <warning descr="🔨 PHP Hammer: the sensitive word \"session\" was used.">$SessionId</warning>, // PascalCase
    <warning descr="🔨 PHP Hammer: the sensitive word \"private key\" was used.">$myPrivateKey</warning>, // two-words
    <warning descr="🔨 PHP Hammer: the sensitive words \"bearer\" and \"token\" was used.">$bearerToken</warning>, // Multiple sensitive words
    <warning descr="🔨 PHP Hammer: the sensitive words \"bearer\", \"token\" and \"secret\" was used.">$bearerTokenSecret</warning>, // A lot of sensitive words
    <warning descr="🔨 PHP Hammer: the sensitive words \"bearer\", \"token\", \"secret\", ... was used.">$bearerTokenSecretHash</warning>, // A lot more of sensitive words
) {
};

// Must report all: applicable to abstract classes, but non-abstract method.
abstract class Dummy1000
{
    function dummy(string <warning descr="🔨 PHP Hammer: the sensitive word \"secret\" was used.">$secret</warning>)
    {
    }
}

// Must report all: true and false.
/**
 * @param true $secret
 * @param false $code
 */
function test(<warning descr="🔨 PHP Hammer: the sensitive word \"secret\" was used.">$secret</warning>, <warning descr="🔨 PHP Hammer: the sensitive word \"code\" was used.">$code</warning>) {
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
