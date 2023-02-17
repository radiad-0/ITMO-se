package com.radiad.lab2.servlets;

import com.radiad.lab2.Row;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;

import java.util.ArrayList;

@WebListener
public class ContextListener implements ServletContextListener {
    private ArrayList<Row> rows;
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        rows = new ArrayList<>();
        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute("rows", rows);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        /* This method is called when the servlet Context is undeployed or Application Server shuts down. */
    }
}
