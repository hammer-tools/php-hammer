<?php

// The fact that the filename starts with a number can throw an Exception.
// In this case, the inspection should not generate a quick-fix to avoid this problem.

class Dummy {
}
