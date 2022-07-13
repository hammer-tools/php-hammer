<?php

class <error descr="[PHP Hammer] Class name (\"Dummy\") does not match the file that stores it (\"IncorrectName.php\").">Dummy</error> {
}

// Not applicable as it is not at the root level of the file:

if (!class_exists("Dummy")) {
    class DummyNonRooted {
    }
}
