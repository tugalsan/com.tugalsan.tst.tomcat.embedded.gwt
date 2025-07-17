package com.tugalsan.tst.tomcat.embedded.gwt;

import com.tugalsan.api.log.server.TS_Log;
import com.tugalsan.api.thread.server.sync.TS_ThreadSyncTrigger;
import com.tugalsan.api.tomcat.embedded.gwt.server.*;
import com.tugalsan.api.tomcat.embedded.gwt.server.servlets.*;
import com.tugalsan.api.tomcat.embedded.gwt.server.utils.*;

public class Main {

    private final static TS_Log d = TS_Log.of(Main.class);

    //HOW TO EXECUTE
    //C:\me\codes\com.tugalsan\tst\com.tugalsan.tst.tomcat.embedded.gwt>target\bin\webapp.bat
    public static void main(String[] args) {
        var tomcatBall = TS_TomcatBall.of(
                TS_ThreadSyncTrigger.of(d.className + ".main"),
                "",
                servlets -> {
                    servlets.add(new TS_ServletAliveByMapping());
                }, connectors -> {
                    connectors.add(TS_TomcatConnector.ofUnSecure(8085));
                }
        );
        System.out.println("SAFE EXIT: " + tomcatBall);
    }
}
