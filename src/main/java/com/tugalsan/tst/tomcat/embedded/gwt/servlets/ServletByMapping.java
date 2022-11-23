package com.tugalsan.tst.tomcat.embedded.gwt.servlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class ServletByMapping extends TS_ServletAbstract {

    @Override
    public String name() {
        return ServletByMapping.class.getSimpleName();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println(ServletByMapping.class.getName());
    }
}
