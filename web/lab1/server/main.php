<?php
require 'Point.php';
require 'Area.php';
require 'ScriptTime.php';

$scriptTime = new ScriptTime();
session_start();

$point = new Point($_GET['x'], $_GET['y']);
$area = new Area($_GET['r']);

$result = $area->checkHit($point);

$scriptTime->endScript();

$format = "<tr><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td></tr>";

response($format, $point->getX(), $point->getY(), $area->getRadius(), $result,
    $scriptTime->getStartTime(), $scriptTime->getEndTime(), $scriptTime->getRunningTime());

function response($format, ...$args): void
{
    if (!isset($_SESSION['data'])) $_SESSION['data'] = [];
    $_SESSION['data'][] = sprintf($format, count($_SESSION['data']), $args);
    foreach ($_SESSION['data'] as $row) echo $row;
}