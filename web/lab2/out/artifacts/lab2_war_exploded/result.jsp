<%@ page import="java.util.ArrayList" %>
<%@ page import="com.radiad.lab2.Row" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="utf-8">
    <title>lab1</title>
    <link rel="icon" type="image/png" href="image/not_stonks.png">
    <link rel="stylesheet" type="text/css" href="css/main.css">
</head>
<body>
<div class="page info" style="font-style: cursive; color: #f7c909; font-size: large;">
    <p>Давлетов Айдар Раилевич. 335090. Вариант 1402</p>
</div>

<div class="table">
    <table border="1">
        <caption>Result</caption>
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
        <tbody id="result">
        <%
            ArrayList<Row> rows = (ArrayList<Row>) application.getAttribute("rows");
            if (rows != null && !rows.isEmpty()) {
                Row lastRow = rows.get(rows.size()-1);
        %>
        <tr>
            <th><%= rows.size() - 1 %></th>
            <th><%= lastRow.getX() %></th>
            <th><%= lastRow.getY() %></th>
            <th><%= lastRow.getR() %></th>
            <th><%= lastRow.getResult() %></th>
            <th><%= lastRow.getStartTime() %></th>
            <th><%= lastRow.getEndTime() %></th>
            <th><%= lastRow.getScriptTime() %></th>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>
</div>

<div class="page">
    <form method="get" action="index.jsp">
        <input type="submit" value='Go back'>
    </form>
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
            <th><%= i++ %></th>
            <th><%= row.getX() %></th>
            <th><%= row.getY() %></th>
            <th><%= row.getR() %></th>
            <th><%= row.getResult() %></th>
            <th><%= row.getStartTime() %></th>
            <th><%= row.getEndTime() %></th>
            <th><%= row.getScriptTime() %></th>
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
