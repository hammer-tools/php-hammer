<?php

function warning1(): <weak_warning descr="🔨 PHP Hammer: nullable type must be on rightmost side (\"int|string|null\").">int|null|string</weak_warning>
{}

function warning2(): <weak_warning descr="🔨 PHP Hammer: nullable type must be on rightmost side (\"int|string|null\").">null|int|string</weak_warning>
{}

function warning3(): <weak_warning descr="🔨 PHP Hammer: nullable type must be on rightmost side (\"\int|null\").">\null|\int</weak_warning>
{}

function clear1(): int|string|null
{}
