package com.tugalsan.tst.tomcat.embedded.gwt.utils;

import com.tugalsan.api.thread.server.TS_ThreadWait;
import java.util.*;
import java.nio.file.*;
import org.apache.catalina.*;
import org.apache.catalina.startup.*;
import com.tugalsan.api.unsafe.client.*;
import com.tugalsan.tst.tomcat.embedded.gwt.servlets.*;
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

    public void destroy(int maxSecondsForConnectors, int maxSecondsForTomcat) {
        {//SEQUENCIAL WAY
            connectors().forEach(connector -> connector.destroy());
            TS_ThreadWait.of(Duration.ofSeconds(maxSecondsForConnectors));//TEST FOR SEQUENCIAL WAY
            TGS_UnSafe.execute(() -> context().destroy());
            TS_ThreadWait.of(Duration.ofSeconds(maxSecondsForTomcat));//TEST FOR SEQUENCIAL WAY
        }
//        {//DESTROR ALL CONNECTORS
//            List<Callable<Boolean>> destroyConnectors = new ArrayList();
//            connectors.forEach(connector -> destroyConnectors.add(() -> {
//                connector.destroy();
//                return true;
//            }));
//            var all = TS_ThreadRunAll.of(Duration.ofSeconds(maxSecondsForConnectors), destroyConnectors);
//            if (all.hasError()) {
//                System.out.println("ERROR ON DESTROY CONNECTORS:");
//                all.exceptions.forEach(e -> {
//                    e.printStackTrace();
//                });
//            } else {
//                System.out.println("CONNECTORS DESTROYED SUCCESSFULLY");
//            }
//        }
//        {//DESTROY TOMCAT
//            Callable<Boolean> destroyTomcat = () -> {
//                context().destroy();
//                return true;
//            };
//            var all = TS_ThreadRunAll.of(Duration.ofSeconds(maxSecondsForTomcat), destroyTomcat);
//            if (all.hasError()) {
//                System.out.println("ERROR ON DESTROY TOMCAT:");
//                all.exceptions.forEach(e -> {
//                    e.printStackTrace();
//                });
//            } else {
//                System.out.println("CONNECTORS TOMCAT SUCCESSFULLY");
//            }
//        }
    }

}
