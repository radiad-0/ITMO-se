<%@ page import="java.util.ArrayList" %>
<%@ page import="com.radiad.lab2.Row" %>
<%@ page import="java.util.Objects" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="utf-8">
    <title>lab1</title>
    <link rel="icon" type="image/png" href="image/not_stonks.png">
    <link rel="stylesheet" type="text/css" href="css/main.css">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src=js/main.js></script>
</head>
<body>
<div class="page info" style="font-style: cursive; color: #f7c909; font-size: large;">
    <p>Давлетов Айдар Раилевич. 335090. Вариант 1402</p>
</div>

<div class="page">
    <div class="img">
        <p class="title">Area:</p>
        <img src="image/area.png" id="area">
        <%
            ArrayList<Row> rows = (ArrayList<Row>) application.getAttribute("rows");
            if (rows != null) {
                int i = 0;
                for (Row row : rows) {
                    if (Objects.equals(row.getResult(), "hit")) {
        %>
        <div class="pointHit"
             style="left: <%= row.getPointX() %>px;
                     top: <%= row.getPointY() %>px;"></div>
        <%
                    } else {
        %>
        <div class="pointMiss"
             style="left: <%= row.getPointX() %>px;
                     top: <%= row.getPointY() %>px;"></div>
        <%
                    }
                }
            }
        %>
    </div>
    <div class="form">
        <div class="input" id="buttons">
            <p class="title">Select "x" coordinate:</p>
            <input type="button" id="x-5" value="-5">
            <input type="button" id="x-4" value="-4">
            <input type="button" id="x-3" value="-3">
            <input type="button" id="x-2" value="-2">
            <input type="button" id="x-1" value="-1">
            <input type="button" id="x0" value="0">
            <input type="button" id="x1" value="1">
            <input type="button" id="x2" value="2">
            <input type="button" id="x3" value="3">
        </div>
        <div class="input" id="text">
            <p class="title">Enter "y" coordinate:</p>
            <input type="text" id="y" placeholder="{ -5 ... 3 }">
        </div>
        <div class="input" id="checkboxes">
            <p class="title" id="r">Select only one radius "R":</p>
            <label><input type="checkbox" id="r1" value="1"> 1</label>
            <label><input type="checkbox" id="r2" value="2"> 2</label>
            <label><input type="checkbox" id="r3" value="3"> 3</label>
            <label><input type="checkbox" id="r4" value="4"> 4</label>
            <label><input type="checkbox" id="r5" value="5"> 5</label>
        </div>
        <div id="send" class="button">send</div>
    </div>
</div>

<div class="table">
    <table border="1">
        <caption>Previous requests</caption>
        <tr>
            <th>Id</th>
            <th>x</th>
            <th>y</th>
            <th>R</th>
            <th>Result</th>
            <th>Start time of script</th>
            <th>End time of script</th>
            <th>script running time</th>
        </tr>
        <tbody id="results">
        <%
            if (rows != null) {
                int i = 0;
                for (Row row : rows) {
        %>
        <tr>
            <th><%= i++ %>
            </th>
            <th><%= row.getX() %>
            </th>
            <th><%= row.getY() %>
            </th>
            <th><%= row.getR() %>
            </th>
            <th><%= row.getResult() %>
            </th>
            <th><%= row.getStartTime() %>
            </th>
            <th><%= row.getEndTime() %>
            </th>
            <th><%= row.getScriptTime() %>
            </th>
        </tr>
        <%
                }
            }
        %>
        </tbody>
    </table>
</div>

<div class="page info">
    <p>
        <a onclick="window.open('https://se.ifmo.ru/courses')">ИТМО ВТ</a> |
        <a onclick="window.open('https://se.ifmo.ru/courses/web')">Web lab1</a> |
        <a onclick="window.open('https://github.com/radiad-0/WebLabs/tree/main')">radiad</a> |
        2022
    </p>
</div>
</body>
</html>