package com.tugalsan.tst.tomcat.embedded.gwt.servlets;

import com.tugalsan.api.thread.server.TS_ThreadWait;
import com.tugalsan.tst.tomcat.embedded.gwt.utils.TS_TomcatBall;
import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import com.tugalsan.api.unsafe.client.*;
import java.time.Duration;

//ServletByAnnotation
@WebServlet(
        name = "ServletByAnnotation",//JUST A UNQUE TAG
        urlPatterns = {"/ServletByAnnotation"}
)
public class ServletDestroy extends HttpServlet {

    public static TS_TomcatBall tomcatBall;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TGS_UnSafe.execute(() -> {
            if (tomcatBall != null) {
                tomcatBall.destroy();
            }
            TS_ThreadWait.of(Duration.ofSeconds(5));
            System.exit(0);
        });
    }
}
