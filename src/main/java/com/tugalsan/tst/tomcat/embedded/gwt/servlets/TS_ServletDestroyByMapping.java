package com.tugalsan.tst.tomcat.embedded.gwt.servlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.tugalsan.api.log.server.*;
import com.tugalsan.tst.tomcat.embedded.gwt.*;

public class TS_ServletDestroyByMapping extends TS_ServletAbstract {

    final private static TS_Log d = TS_Log.of(TS_ServletDestroyByMapping.class);

    public TS_ServletDestroyByMapping(TS_TomcatBall tomcatBall) {
        this.tomcatBall = tomcatBall;
    }
    final private TS_TomcatBall tomcatBall;

    @Override
    public String name() {
        return TS_ServletDestroyByMapping.class.getSimpleName();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println(TS_ServletDestroyByMapping.class.getName());
        resp.flushBuffer();
        var waitSeconds = 5;
        if (tomcatBall == null) {
            d.ce("doGet", "tomcatBall == null");
        } else {
            tomcatBall.destroy(waitSeconds, waitSeconds);
        }
//        System.exit(0);
    }
}
