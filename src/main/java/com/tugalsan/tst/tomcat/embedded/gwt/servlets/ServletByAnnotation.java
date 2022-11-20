package com.tugalsan.tst.tomcat.embedded.gwt.servlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

@WebServlet(
        name = "ServletHello",//JUST A UNQUE TAG
        urlPatterns = {"/anno"}
)
public class ServletByAnnotation extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println(ServletByAnnotation.class.getName());
    }
}
