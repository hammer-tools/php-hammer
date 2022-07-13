<?php

class IncorrectName
{
}

// Not applicable as it is not at the root level of the file:

if (!class_exists("Dummy")) {
    class DummyNonRooted {
    }
}
