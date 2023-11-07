<?php

// Must fail: extract() must be protected.
extract($dummy, \EXTR_SKIP);

// Must fail: extract() must be protected, prefix is set.
extract($dummy, \EXTR_SKIP, prefix: "prefix");

// Skip: already protected.
extract($dummy, EXTR_SKIP);

// Skip: already protected.
extract($dummy, flags: EXTR_SKIP);

// Skip: explicitly overwrite.
extract($dummy, EXTR_OVERWRITE);
