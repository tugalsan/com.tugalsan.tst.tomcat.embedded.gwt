package com.tugalsan.tst.tomcat.embedded.gwt.servlets;

import com.tugalsan.tst.tomcat.embedded.gwt.TS_TomcatBall;
import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

//ServletByAnnotation
@WebServlet(
        name = "TS_ServletDestroy",//JUST A UNQUE TAG
        urlPatterns = {"/TS_ServletDestroy"}
)
public class TS_ServletDestroy extends HttpServlet {

    public static TS_TomcatBall tomcatBall;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var waitSeconds = 5;
        if (tomcatBall != null) {
            tomcatBall.destroy(waitSeconds, waitSeconds);
        }
        System.exit(0);
    }
}
