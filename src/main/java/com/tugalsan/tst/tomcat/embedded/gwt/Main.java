package com.tugalsan.tst.tomcat.embedded.gwt;

import com.tugalsan.tst.tomcat.embedded.gwt.servlets.TS_ServletAliveByMapping;
import com.tugalsan.tst.tomcat.embedded.gwt.utils.*;

public class Main {

    //HOW TO EXECUTE
    //C:\me\codes\com.tugalsan\tst\com.tugalsan.tst.tomcat.embedded.gwt>target\bin\webapp.bat
    public static void main(String[] args) {
        var tomcatBall = TS_TomcatBall.of("",
                servlets -> {
                    servlets.add(new TS_ServletAliveByMapping());
                }, connectors -> {
                    connectors.add(TS_TomcatConnector.ofUnSecure(8085));
//                    connectors.add(TS_TomcatConnector.ofUnSecure(8086));
                }
        );
        System.out.println("SAFE EXIT: " + tomcatBall);
    }
}
