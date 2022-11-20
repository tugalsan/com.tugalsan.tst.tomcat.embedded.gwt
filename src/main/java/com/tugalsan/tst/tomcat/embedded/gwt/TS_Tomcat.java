package com.tugalsan.tst.tomcat.embedded.gwt;

import java.util.*;
import com.tugalsan.api.executable.client.*;
import com.tugalsan.api.list.client.*;
import com.tugalsan.api.stream.client.*;
import com.tugalsan.api.unsafe.client.*;
import com.tugalsan.tst.tomcat.embedded.gwt.servlets.*;
import com.tugalsan.tst.tomcat.embedded.gwt.utils.*;

public class TS_Tomcat {

    private TS_Tomcat(TS_TomcatBall tomcatBall) {
        this.tomcatBall = tomcatBall;
    }
    public final TS_TomcatBall tomcatBall;

    public static TS_Tomcat of(Class mainClass, CharSequence contextName_as_empty_or_slashName, TGS_ExecutableType1<List<ServletAbstract>> servlets, TGS_ExecutableType1<List<TS_TomcatConnector>> connectors) {
        var tomcat = new TS_Tomcat(TS_TomcatBuild.init(mainClass, contextName_as_empty_or_slashName));
        List<ServletAbstract> servletList = TGS_ListUtils.of();
        List<TS_TomcatConnector> connectorList = TGS_ListUtils.of();
        servlets.execute(servletList);
        connectors.execute(connectorList);
        TS_TomcatBuild.map(tomcat.tomcatBall, servletList);
        TS_TomcatBuild.startAndLock(tomcat.tomcatBall, connectorList);
        return tomcat;
    }

    public void destroy() {
        tomcatBall.connectors().forEach(connector -> {
            TGS_UnSafe.execute(() -> {
                connector.destroy();
            }, e -> TGS_StreamUtils.doNothing());
        });
//        TGS_UnSafe.execute(() -> {
//            tomcatBall.context().destroy();
//        }, e -> TGS_StreamUtils.doNothing());
    }
}
