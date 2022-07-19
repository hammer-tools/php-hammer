<?php

switch (true) {
    case true :
    case false:
    default:
}

switch (true) {
    case true <weak_warning descr="🔨 PHP Hammer: Wrong switch() \"case\" separator.">;</weak_warning>
    case false<weak_warning descr="🔨 PHP Hammer: Wrong switch() \"case\" separator.">;</weak_warning>
    default<weak_warning descr="🔨 PHP Hammer: Wrong switch() \"default\" separator.">;</weak_warning>
}

