<?php

class Point {
    private $x;
    private $y;
    private bool $isValid;
    private array $warnings;

    public function __construct($x, $y) {
        $this->x = $x;
        $this->y = $y;
        $this->validatePoint();
    }

    private function validateX(): bool
    {
        if (!is_numeric($this->x)){
            $this->warnings[] = "x должен быть числом";
            return false;
        }
        if ((double) $this->x - (int) $this->x){
            $this->warnings[] = "x должен быть целым числом";
            return false;
        }
        if (!(-5 <= $this->x && $this->x <= 3)){
            $this->warnings[] = "неверный диапазон x";
            return false;
        }
        $this->x = (int) $this->x;
        return true;
    }

    private function validateY(): bool
    {
        if (!is_numeric($this->y)){
            $this->warnings[] = "y должен быть числом";
            return false;
        }
        if (!(-5 <= $this->y && $this->y <= 3)){
            $this->warnings[] = "неверный диапазон y";
            return false;
        }
        return true;
    }

    private function validatePoint(): void
    {
        $this->isValid = $this->validateX() && $this->validateY();
    }

    public function getX()
    {
        return $this->x;
    }

    public function getY()
    {
        return $this->y;
    }

    public function isValid(): bool
    {
        return $this->isValid;
    }
    public function getWarnings(): array
    {
        return $this->warnings;
    }
}