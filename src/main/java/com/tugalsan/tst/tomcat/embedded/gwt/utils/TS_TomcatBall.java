package com.tugalsan.tst.tomcat.embedded.gwt.utils;

import java.util.*;
import java.nio.file.*;
import org.apache.catalina.*;
import org.apache.catalina.startup.*;
import com.tugalsan.api.stream.client.*;
import com.tugalsan.api.unsafe.client.*;
import com.tugalsan.tst.tomcat.embedded.gwt.servlets.*;

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

    public void destroy() {
        connectors().forEach(connector -> connector.destroy());
        TGS_UnSafe.execute(() -> context().destroy(), e -> TGS_StreamUtils.doNothing());
    }

}
