<?php

$dummy1000 = function ($dummy) {
    if ($dummy && $dummy) {
        return true;
    }
};

$dummy1500 = function ($dummy) {
    if ($dummy && $dummy)
        return true;
};

$dummy2000 = function ($dummy) {
    if (($dummy && $dummy) && $dummy && $dummy && ($dummy && $dummy)) {
        return true;
    }
};

$dummy3000 = function ($dummy) {
    if ($dummy && $dummy) {
        // Comment.
        return /** Comment. */
            true; # Comment.
    }
};

$dummy4000 = function ($dummy) {
    if (((($dummy)) && ((($dummy)))) && $dummy) {
        return true;
    }
};

$dummy5000 = function ($dummy) {
    if ($dummy && ((($dummy)) && ((($dummy))))) {
        return true;
    }
};

$dummy6000 = function ($dummy) {
    if ($dummy instanceof DateTime && $dummy) {
        return true;
    }
};

$dummy7000 = function ($dummy) {
    if ($dummy && $dummy) {
        throw new Exception;
    }
};

$dummy8000 = function ($dummy) {
    if ($dummy && $dummy) {
        exit(0);
    }
};

$dummy9000 = function ($dummy) {
    if ($dummy) {
        return true;
    } else if ($dummy && $dummy) {
        return true;
    }

    if ($dummy) {
        return true;
    }
};

$dummy10000 = function ($dummy) {
    if ($dummy) {
        return true;
    } else if ($dummy && $dummy && $dummy && $dummy) {
        return true;
    }

    if ($dummy) {
        return true;
    }
};

$dummy11000 = function ($dummy) {
    if ($dummy) {
        return true;
    } elseif ($dummy && $dummy) {
        return true;
    }

    if ($dummy) {
        return true;
    }
};

$dummy12000 = function ($dummy) {
    if ($dummy) {
        return true;
    } else if ($dummy && $dummy) {
        return true;
    }

    if ($dummy) {
        return true;
    }
};

$dummy13000 = function ($dummy) {
    if ($dummy && $dummy) {
        return true;
    }

    if ($dummy) {
        return true;
    }
};

// Not applicable:

$dummy = function ($dummy) {
    if ($dummy) {
        if ($dummy) {
            return true;
        } else {
            return false;
        }
    }
};

$dummy = function ($dummy) {
    if ($dummy) {
        if ($dummy) {
            return true;
        } elseif ($dummy) {
            return false;
        }
    }
};

$dummy = function ($dummy) {
    if ($dummy) {
        if ($dummy || $dummy) {
            return true;
        }
    }
};

$dummy = function ($dummy) {
    if ($dummy || $dummy) {
        if ($dummy) {
            return true;
        }
    }
};

$dummy = function ($dummy) {
    if ($dummy) {
        if (((($dummy)) || ((($dummy))))) {
            return true;
        }
    }
};

$dummy = function ($dummy) {
    if (((($dummy)) || ((($dummy))))) {
        if ($dummy) {
            return true;
        }
    }
};

$dummy = function ($dummy) {
    if ($dummy) {
        $dummy = 1;

        if ($dummy) {
            $dummy = 1;
        }
    }
};

$dummy = function ($dummy) {
    if ($dummy) {
        if ($dummy) {
            $dummy = 1;
        }

        $dummy = 1;
    }
};

$dummy = function ($dummy) {
    if ($dummy) {
        exit(0);
    }
};

$dummy = function ($dummy) {
    if ($dummy) {
        if ($dummy) {
            return true;
        }
    } else {
        return true;
    }
};

$dummy = function ($dummy) {
    if ($dummy) {
        if ($dummy) {
            return true;
        }
    } else if ($dummy) {
        return true;
    }
};

$dummy10000 = function ($dummy) {
    if ($dummy) {
        return true;
    } else if ($dummy) {
        if ($dummy || $dummy) {
            return true;
        }
    }
};

$dummy = function ($dummy) {
    if ($dummy) {
        return true;
    } else if ($dummy) {
        if ($dummy) {
            return true;
        }
    } else if ($dummy) {
        return true;
    }
};

$dummy = function ($dummy) {
    if ($dummy) {
        return true;
    } else if ($dummy) {
        if ($dummy) {
            return true;
        }
    } else {
        return true;
    }
};

$dummy = function ($dummy) {
    if ($dummy) {
        return true;
    } elseif ($dummy) {
        if ($dummy) {
            return true;
        }
    } elseif ($dummy) {
        return true;
    }
};

$dummy = function ($dummy) {
    if ($dummy) {
        return true;
    } elseif ($dummy) {
        if ($dummy) {
            return true;
        }
    } else {
        return true;
    }
};
