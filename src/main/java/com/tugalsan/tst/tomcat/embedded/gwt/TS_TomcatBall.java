package com.tugalsan.tst.tomcat.embedded.gwt;

import com.tugalsan.api.executable.client.TGS_ExecutableType1;
import com.tugalsan.api.list.client.TGS_ListUtils;
import com.tugalsan.api.log.server.TS_Log;
import com.tugalsan.api.thread.server.TS_ThreadWait;
import java.util.*;
import java.nio.file.*;
import org.apache.catalina.*;
import org.apache.catalina.startup.*;
import com.tugalsan.api.unsafe.client.*;
import com.tugalsan.tst.tomcat.embedded.gwt.servlets.TS_ServletDestroyByMapping;
import com.tugalsan.tst.tomcat.embedded.gwt.utils.*;
import java.time.Duration;

public record TS_TomcatBall(
        Path project,
        Path project_src_main_webapp,
        Path project_target_classes,
        Tomcat tomcat,
        Context context,
        CharSequence contextName_as_empty_or_slashName,
        WebResourceRoot resources,
        List<TS_ServletAbstract> servlets,
        List<TS_TomcatConnector> connectors) {

    final private static TS_Log d = TS_Log.of(TS_TomcatBall.class);

    public static TS_TomcatBall of(CharSequence contextName_as_empty_or_slashName, TGS_ExecutableType1<List<TS_ServletAbstract>> servlets, TGS_ExecutableType1<List<TS_TomcatConnector>> connectors) {
        var tomcatBall = TS_TomcatBuild.init(contextName_as_empty_or_slashName);
        List<TS_ServletAbstract> servletList = TGS_ListUtils.of();
        List<TS_TomcatConnector> connectorList = TGS_ListUtils.of();
        servlets.execute(servletList);
        connectors.execute(connectorList);
        TS_TomcatBuild.map(tomcatBall, servletList);
        TS_TomcatBuild.map(tomcatBall, new TS_ServletDestroyByMapping(tomcatBall));
        TS_TomcatBuild.startAndLock(tomcatBall, connectorList);
        return tomcatBall;
    }

    public void destroy() {
        var maxSecondsForConnectors = 5;
        var maxSecondsForTomcat = 5;
        var sequencial = true;
        if (sequencial) {//SEQUENCIAL WAY
            connectors().forEach(connector -> connector.destroy());
            TS_ThreadWait.of(Duration.ofSeconds(maxSecondsForConnectors));//TEST FOR SEQUENCIAL WAY
            TGS_UnSafe.execute(() -> context().destroy(), e -> d.ct("context.destroy", e));
            TS_ThreadWait.of(Duration.ofSeconds(maxSecondsForTomcat));//TEST FOR SEQUENCIAL WAY
        } else {
//            {//DESTROR ALL CONNECTORS
//                List<Callable<Boolean>> destroyConnectors = new ArrayList();
//                connectors.forEach(connector -> destroyConnectors.add(() -> {
//                    connector.destroy();
//                    return true;
//                }));
//                var all = TS_ThreadRunAll.of(Duration.ofSeconds(maxSecondsForConnectors), destroyConnectors);
//                if (all.hasError()) {
//                    System.out.println("ERROR ON DESTROY CONNECTORS:");
//                    all.exceptions.forEach(e -> {
//                        e.printStackTrace();
//                    });
//                } else {
//                    System.out.println("CONNECTORS DESTROYED SUCCESSFULLY");
//                }
//            }
//            {//DESTROY TOMCAT
//                Callable<Boolean> destroyTomcat = () -> {
//                    context().destroy();
//                    return true;
//                };
//                var all = TS_ThreadRunAll.of(Duration.ofSeconds(maxSecondsForTomcat), destroyTomcat);
//                if (all.hasError()) {
//                    System.out.println("ERROR ON DESTROY TOMCAT:");
//                    all.exceptions.forEach(e -> {
//                        e.printStackTrace();
//                    });
//                } else {
//                    System.out.println("TOMCAT DESTROYED SUCCESSFULLY");
//                }
//            }
        }
        {//FINNAL SEALING
            TGS_UnSafe.execute(() -> tomcat.stop(), e -> d.ct("tomcat.stop", e));
            TS_ThreadWait.of(Duration.ofSeconds(maxSecondsForTomcat));//TEST FOR SEQUENCIAL WAY
            TGS_UnSafe.execute(() -> tomcat.destroy(), e -> d.ct("tomcat.destroy", e));
        }
    }

}
