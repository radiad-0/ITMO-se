package com.radiad.lab2.servlets;

import com.radiad.lab2.Area;
import com.radiad.lab2.Point;
import com.radiad.lab2.Row;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "AreaCheckServlet", value = "/area-check")
public class AreaCheckServlet extends HttpServlet {
    private ArrayList<Row> rows;

    @Override
    public void init() {
        rows = (ArrayList<Row>) getServletContext().getAttribute("rows");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        double startTime = System.nanoTime() / 1000000.0;

        Point point = new Point(request.getParameter("x"), request.getParameter("y"));
        Area area = new Area(request.getParameter("r"));

        String result = area.checkHit(point);

        double endTime = System.nanoTime() / 1000000.0;
        String scriptTime = String.format("%.9f", endTime - startTime);

        rows.add(new Row(point.getX(), point.getY(), area.getRadius(), result, String.format("%.9f", startTime), String.format("%.9f", endTime), scriptTime,
                request.getParameter("pointX"), request.getParameter("pointY")));
    }
}
