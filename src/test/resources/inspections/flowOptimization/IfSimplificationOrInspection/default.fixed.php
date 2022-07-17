<?php

$dummy = function () use ($dummy) {
    if ($dummy) {
        return true;
    }

    if ($dummy) {
        return true;
    }
};

$dummy = function () use ($dummy) {
    if (($dummy || $dummy) || $dummy) {
        return true;
    }

    if ($dummy || ($dummy || $dummy)) {
        return true;
    }
};

$dummy = function () use ($dummy) {
    if ($dummy) {
        return true;
    }

    if ($dummy) {
        return true;
    } else {
        return false;
    }
};

$dummy = function () use ($dummy) {
    if ($dummy) {
        return true;
    }

    if ($dummy) {
        return true;
    } elseif ($dummy) {
        return false;
    }
};

$dummy = function () use ($dummy) {
    if ($dummy) {
        return true;
    }

    if ($dummy) {
        return true;
    } elseif ($dummy) {
        return false;
    } else {
        return false;
    }
};

$dummy = function () use ($dummy) {
    if ($dummy) {
        return true;
    }

    if ($dummy) {
        return true;
    } else if ($dummy) {
        return false;
    }
};

$dummy = function () use ($dummy) {
    if ($dummy) {
        return true;
    }

    if ($dummy) {
        return true;
    } else if ($dummy) {
        return false;
    } else {
        return true;
    }
};

$dummy = function () use ($dummy) {
    if ($dummy) {
        return true;
    } else if ($dummy) {
        return true;
    } elseif ($dummy) {
        return false;
    }
};

$dummy = function () use ($dummy) {
    if ($dummy) {
        return false;
    } else if ($dummy) {
        return true;
    } else if ($dummy) {
        return true;
    }
};

$dummy = function () use ($dummy) {
    if ($dummy) {
        return true;
    } elseif ($dummy) {
        return true;
    } else if ($dummy) {
        return false;
    }
};

$dummy = function () use ($dummy) {
    if ($dummy) {
        return false;
    } elseif ($dummy) {
        return true;
    } else if ($dummy) {
        return true;
    }
};

$dummy = function () use ($dummy) {
    if ($dummy) {
        return false;
    } elseif ($dummy) {
        return true;
    } elseif ($dummy) {
        return true;
    }
};

$dummy = function () use ($dummy) {
    if ($dummy) {
        return true;
    }

    if ($dummy) {
        // Comment.
        return /** Comment. */
            true; # Comment.
    }
};

$dummy = function () use ($dummy) {
    if (((($dummy)) || ((($dummy))))) {
        return true;
    }

    if ($dummy) {
        return true;
    }
};

$dummy = function () use ($dummy) {
    if ($dummy) {
        return true;
    }

    if (((($dummy)) || ((($dummy))))) {
        return true;
    }
};

$dummy = function () use ($dummy) {
    if ($dummy instanceof DateTime) {
        return true;
    }

    if ($dummy) {
        return true;
    }
};

$dummy = function () use ($dummy) {
    if ($dummy) {
        throw new Exception;
    }

    if ($dummy) {
        throw new Exception;
    }
};

$dummy = function () use ($dummy) {
    if ($dummy) {
        exit(0);
    }

    if ($dummy) {
        exit(0);
    }
};

// Not applicable:

$dummy = function () use ($dummy) {
    if ($dummy) {
        return true;
    }

    if ($dummy) {
        return false;
    }
};

$dummy = function () use ($dummy) {
    if ($dummy) {
        return true;
    }

    if ($dummy && $dummy) {
        return true;
    }
};

$dummy = function () use ($dummy) {
    if ($dummy && $dummy) {
        return true;
    }

    if ($dummy) {
        return true;
    }
};

$dummy = function () use ($dummy) {
    if ($dummy) {
        return true;
    }

    if (((($dummy)) && ((($dummy))))) {
        return true;
    }
};

$dummy = function () use ($dummy) {
    if (((($dummy)) && ((($dummy))))) {
        return true;
    }

    if ($dummy) {
        return true;
    }
};

$dummy = function () use ($dummy) {
    if ($dummy) {
        $dummy = 1;
    }

    if ($dummy) {
        $dummy = 1;
    }
};

$dummy = function () use ($dummy) {
    if ($dummy) {
        exit(0);
    }

    if ($dummy) {
        exit(1);
    }
};
