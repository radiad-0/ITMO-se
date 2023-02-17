<?php

class ScriptTime {
    private string $startTime;
    private string $endTime;
    private float $startMicroTime;
    private float $endMicroTime;

    public function __construct() {
        date_default_timezone_set('Europe/Moscow');
        $this->startTime = date('H:i:s');
        $this->startMicroTime = microtime(true) * 1000000 % 1000000;
    }

    public function endScript(): void
    {
        $this->endTime = date('H:i:s');
        $this->endMicroTime = microtime(true) * 1000000 % 1000000;
    }

    public function getStartTime(): string
    {
        return "$this->startTime:$this->startMicroTime";
    }

    public function getEndTime(): string
    {
        return "$this->endTime:$this->endMicroTime";
    }

    public function getRunningTime(): string
    {
        return ($this->endMicroTime - $this->startMicroTime) . ' microseconds';
    }


}