<?php
session_start();
foreach ($_SESSION['data'] as $row) echo $row;