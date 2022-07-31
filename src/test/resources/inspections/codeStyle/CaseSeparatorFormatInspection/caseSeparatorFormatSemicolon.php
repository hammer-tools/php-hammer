<?php

switch (true) {
    case true <weak_warning descr="🔨 PHP Hammer: wrong switch() \"case\" separator.">:</weak_warning>
    case false<weak_warning descr="🔨 PHP Hammer: wrong switch() \"case\" separator.">:</weak_warning>
    default<weak_warning descr="🔨 PHP Hammer: wrong switch() \"default\" separator.">:</weak_warning>
}

switch (true) {
    case true ;
    case false;
    default;
}

