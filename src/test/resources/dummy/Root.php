<?php

/**
 * @property self $self
 */
class Example
{
    public static self $selfStatic;

    public static function getSelfStatic(): self
    {
    }

    public function getSelf(): self
    {
    }

    public function isTomorrow(): bool
    {
        return true;
    }
}
