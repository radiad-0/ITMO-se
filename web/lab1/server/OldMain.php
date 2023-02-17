<?php

//разбить весь код на классы, функции и тд
//"показать знание php"
session_start();
date_default_timezone_set('Europe/Moscow');
$start_time = date('H:i:s');
$start = microtime(true)*1000000%1000000;
if (!isset($_SESSION['data']))
    $_SESSION['data'] = array();


$format = "<tr><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td></tr>";
$x = $_GET['x'];
$y = $_GET['y'];
$r = $_GET['r'];

if (!is_numeric($x) || !is_numeric($y) || !is_numeric($r)) $result = 'неверный тип данных';
else {
    $x += 0;
    $r += 0;
    if (gettype($x) != 'integer' || gettype($r) != 'integer') $result = 'необходимо ввсести целое число';
    else if (!(-5 <= $x && $x <= 3) || !(-5 <= $y && $y <= 3) || !(1 <= $r && $r <= 5)) $result = 'неверный диапазон данных';
    else {
        $result = 'hit';
        if (($x > 0 and $y > 0) or
            ($x > 0 and $y < 0 and 2 * $x + $y > $r) or
            ($x < 0 and $y > 0 and sqrt($x * $x + $y * $y) > $r / 2) or
            ($x < 0 and $y < 0 and $x > -$r and $y > -$r / 2)) $result = 'miss';
    }
}

$end_time = date('H:i:s');
$end = microtime(true)*1000000%1000000;

$_SESSION['data'][] = sprintf($format, count($_SESSION['data']), $x, $y, $r, $result, $start_time . ':' . $start, $end_time . ':' . $end, $end-$start . ' microseconds');


foreach ($_SESSION['data'] as $row) echo $row;
exit();