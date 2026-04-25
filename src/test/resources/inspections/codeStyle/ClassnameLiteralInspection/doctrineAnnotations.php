<?php

class Dummy
{
    /**
     * @ORM\ManyToOne(targetEntity="ACME\Entity\Course")
     */
    private Course $course;
}

