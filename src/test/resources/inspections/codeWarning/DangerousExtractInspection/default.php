<?php

// Must fail: extract() must be protected.
<warning descr="🔨 PHP Hammer: dangerous extract() call.">extract($dummy)</warning>;

// Must fail: extract() must be protected, prefix is set.
<warning descr="🔨 PHP Hammer: dangerous extract() call.">extract($dummy, prefix: "prefix")</warning>;

// Skip: already protected.
extract($dummy, EXTR_SKIP);

// Skip: already protected.
extract($dummy, flags: EXTR_SKIP);

// Skip: explicitly overwrite.
extract($dummy, EXTR_OVERWRITE);
