package com.tugalsan.tst.tomcat.embedded.gwt;

import com.tugalsan.tst.tomcat.embedded.gwt.servlets.*;
import com.tugalsan.tst.tomcat.embedded.gwt.utils.*;

public class Main {

    //HOW TO EXECUTE
    //C:\me\codes\com.tugalsan\tst\com.tugalsan.tst.tomcat.embedded.gwt>target\bin\webapp.bat
    public static void main(String[] args) {
        var tomcatBall = TS_Tomcat.of(
                  null/*Main.class*/,
                "",
                servlets -> {
                    servlets.add(new ServletByMapping());
                }, connectors -> {
                    connectors.add(TS_TomcatConnector.ofUnSecure(8085));
//                    connectors.add(TS_TomcatConnector.ofUnSecure(8086));
                }
        );
        System.out.println(tomcatBall);
    }
}
