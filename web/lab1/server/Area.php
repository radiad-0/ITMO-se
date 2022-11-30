<?php

class Area {
    private $radius;
    private bool $isValid;
    private string $warning;

    public function __construct($radius) {
        $this->radius = $radius;
        $this->validateRadius();
    }

    public function checkHit(Point $point) {
        if ($point->isValid() && $this->isValid) {
            $result = 'hit';
            $x = (int)$point->getX();
            $y = (double)$point->getY();

            if (($x > 0 and $y > 0) or
                ($x > 0 and $y < 0 and 2 * $x + $y > $this->radius) or
                ($x < 0 and $y > 0 and sqrt($x * $x + $y * $y) > $this->radius / 2) or
                ($x < 0 and $y < 0 and $x > -$this->radius and $y > -$this->radius / 2)) $result = 'miss';

            return $result;
        }
        else return array_merge($point->getWarnings(), (array) $this->warning)[0];
    }

    private function validateRadius(): void
    {
        $this->isValid = false;
        if (!is_numeric($this->radius)){
            $this->warning = "радиус должен быть числом";
            return;
}
        if ((double) $this->radius - (int) $this->radius){
            $this->warning = "радиус должен быть целым числом";
            return;
        }
        if (!(1 <= $this->radius && $this->radius <= 5)){
            $this->warning = "неверный диапазон радиуса";
            return;
        }
        $this->radius = (int) $this->radius;
        $this->isValid = true;
    }

    public function getRadius()
    {
        return $this->radius;
    }

    public function isValid(): bool
    {
        return $this->isValid;
    }

    public function getWarning(): string
    {
        return $this->warning;
    }


}