package com.tugalsan.tst.tomcat.embedded.gwt.servlets;

import com.tugalsan.api.thread.server.TS_ThreadWait;
import com.tugalsan.tst.tomcat.embedded.gwt.utils.TS_TomcatBall;
import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.time.Duration;

//ServletByAnnotation
@WebServlet(
        name = "ServletDestroy",//JUST A UNQUE TAG
        urlPatterns = {"/ServletDestroy"}
)
public class ServletDestroy extends HttpServlet {

    public static TS_TomcatBall tomcatBall;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var waitSeconds = 5;
        if (tomcatBall != null) {
            tomcatBall.destroy(waitSeconds, waitSeconds);
        }
        TS_ThreadWait.of(Duration.ofSeconds(waitSeconds));//TEST FOR SEQUENCIAL WAY
        System.exit(0);
    }
}
