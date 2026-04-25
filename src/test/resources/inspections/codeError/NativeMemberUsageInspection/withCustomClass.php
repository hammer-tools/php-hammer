<?php

namespace App\Models {
    class Company {}
}

namespace {
    // Should be flagged: native type only
    $dummy = function (string $x) {
        <error descr="🔨 PHP Hammer: native type must not be used as object.">$x</error>->dummy();
    };

    // Should NOT be flagged: custom class type
    $dummy = function (\App\Models\Company $company) {
        $company->dummy();
    };

    // Should NOT be flagged: built-in class type
    $dummy = function (\DateTime $x) {
        $x->dummy();
    };
}

