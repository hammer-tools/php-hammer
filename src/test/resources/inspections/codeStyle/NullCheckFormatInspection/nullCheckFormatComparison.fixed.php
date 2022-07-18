<?php

$dummy = $dummy === null;

$dummy = $dummy !== null;

$dummy = ($dummy = $dummy) === null;

$dummy = $dummy === null;

$dummy = $dummy !== null;

$dummy = ($dummy = $dummy) === null;

$dummy = null === $dummy;

$dummy = null !== $dummy;

$dummy = null !== ($dummy = $dummy);

// Not applicable:

$dummy = $dummy == null;

$dummy = $dummy != null;
