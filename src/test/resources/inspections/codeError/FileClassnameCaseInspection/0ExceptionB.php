<?php

// The fact that the filename starts with a number can throw an Exception.
// In this case, the inspection should not generate a quick-fix to avoid this problem.

class <error descr="[PHP Hammer] Class name (\"Dummy\") does not match the file that stores it (\"0ExceptionB.php\").">Dummy</error> {
}
