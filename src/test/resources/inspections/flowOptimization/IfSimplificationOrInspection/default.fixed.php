<?php

$dummy1000 = function ($dummy) {
    if ($dummy || $dummy) {
        return true;
    }
};

$dummy1500 = function ($dummy) {
    if ($dummy || $dummy)
        return true;
};

$dummy2000 = function ($dummy) {
    if (($dummy || $dummy) || $dummy || $dummy || ($dummy || $dummy)) {
        return true;
    }
};

$dummy3000 = function ($dummy) {
    if ($dummy || $dummy) {
        return true;
    } else {
        return false;
    }
};

$dummy4000 = function ($dummy) {
    if ($dummy || $dummy) {
        return true;
    } elseif ($dummy) {
        return false;
    }
};

$dummy5000 = function ($dummy) {
    if ($dummy || $dummy) {
        return true;
    } elseif ($dummy) {
        return false;
    } else {
        return false;
    }
};

$dummy6000 = function ($dummy) {
    if ($dummy || $dummy) {
        return true;
    } else if ($dummy) {
        return false;
    }
};

$dummy7000 = function ($dummy) {
    if ($dummy || $dummy) {
        return true;
    } else if ($dummy) {
        return false;
    } else {
        return true;
    }
};

$dummy8000 = function ($dummy) {
    if ($dummy || $dummy) {
        return true;
    } elseif ($dummy) {
        return false;
    }
};

$dummy9000 = function ($dummy) {
    if ($dummy) {
        return false;
    } else if ($dummy || $dummy) {
        return true;
    }
};

$dummy10000 = function ($dummy) {
    if ($dummy || $dummy) {
        return true;
    } else if ($dummy) {
        return false;
    }
};

$dummy11000 = function ($dummy) {
    if ($dummy) {
        return false;
    } else if ($dummy || $dummy) {
        return true;
    }
};

$dummy12000 = function ($dummy) {
    if ($dummy) {
        return false;
    } elseif ($dummy || $dummy) {
        return true;
    }
};

$dummy12500 = function ($dummy) {
    if ($dummy) {
        return false;
    } else if ($dummy || $dummy) {
        return true;
    }
};

$dummy13000 = function ($dummy) {
    if ($dummy || $dummy) {
        // Comment.
        return /** Comment. */
            true; # Comment.
    }
};

$dummy14000 = function ($dummy) {
    if (((($dummy)) || ((($dummy)))) || $dummy) {
        return true;
    }
};

$dummy15000 = function ($dummy) {
    if ($dummy || ((($dummy)) || ((($dummy))))) {
        return true;
    }
};

$dummy16000 = function ($dummy) {
    if ($dummy instanceof DateTime || $dummy) {
        return true;
    }
};

$dummy17000 = function ($dummy) {
    if ($dummy || $dummy) {
        throw new Exception;
    }
};

$dummy18000 = function ($dummy) {
    if ($dummy || $dummy) {
        exit(0);
    }
};

// Not applicable:

$dummy = function ($dummy) {
    if ($dummy) {
        return true;
    }

    if ($dummy) {
        return false;
    }
};

$dummy = function ($dummy) {
    if ($dummy) {
        return true;
    }

    if ($dummy && $dummy) {
        return true;
    }
};

$dummy = function ($dummy) {
    if ($dummy && $dummy) {
        return true;
    }

    if ($dummy) {
        return true;
    }
};

$dummy = function ($dummy) {
    if ($dummy) {
        return true;
    }

    if (((($dummy)) && ((($dummy))))) {
        return true;
    }
};

$dummy = function ($dummy) {
    if (((($dummy)) && ((($dummy))))) {
        return true;
    }

    if ($dummy) {
        return true;
    }
};

$dummy = function ($dummy) {
    if ($dummy) {
        $dummy = 1;
    }

    if ($dummy) {
        $dummy = 1;
    }
};

$dummy = function ($dummy) {
    if ($dummy) {
        exit(0);
    }

    if ($dummy) {
        exit(1);
    }
};
