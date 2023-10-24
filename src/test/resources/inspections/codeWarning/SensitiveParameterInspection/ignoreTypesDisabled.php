<?php

// Must report all: bool, int and float types ignore option is disabled.
/** @param int|null $lastCode */
$dummy = function (
    bool  <warning descr="🔨 PHP Hammer: the sensitive word \"secret\" was used.">$secret</warning>,
    int   <warning descr="🔨 PHP Hammer: the sensitive word \"code\" was used.">$code</warning>,
    float <warning descr="🔨 PHP Hammer: the sensitive word \"hash\" was used.">$hash</warning>,
          <warning descr="🔨 PHP Hammer: the sensitive word \"code\" was used.">$lastCode</warning>,
          <warning descr="🔨 PHP Hammer: the sensitive word \"code\" was used.">$firstCode</warning> = 0
) {
};

// Must report all: true and false.
/**
 * @param true $secret
 * @param false $code
 */
function test(<warning descr="🔨 PHP Hammer: the sensitive word \"secret\" was used.">$secret</warning>, <warning descr="🔨 PHP Hammer: the sensitive word \"code\" was used.">$code</warning>) {
}
