package com.tugalsan.tst.tomcat.embedded.gwt;

import com.tugalsan.api.log.server.TS_Log;
import java.util.*;
import java.nio.file.*;
import org.apache.catalina.connector.*;
import com.tugalsan.api.unsafe.client.*;

public class TS_TomcatConnector {

    final private static TS_Log d = TS_Log.of(TS_TomcatConnector.class);

    private String type(Path keystorePath) {
        var fn = keystorePath.getFileName().toString().toLowerCase(Locale.ROOT);
        if (fn.endsWith(".p12")) {
            return "PKCS12";
        } else if (fn.endsWith(".jks")) {
            return "JKS";
        }
        return "Unknown";
    }

    private TS_TomcatConnector(int port, String keyAlias, String keystorePass, Path keystorePath) {
        this.port = port;
        this.keyAlias = keyAlias;
        this.keystorePass = keystorePass;
        this.keystorePath = keystorePath;
        connector = new Connector();
        connector.setPort(port);
        if (keyAlias == null) {
            return;
        }
        connector.setSecure(true);
        connector.setScheme("https");
        connector.setProperty("keyAlias", keyAlias);
        connector.setProperty("keystorePass", keystorePass);
        connector.setProperty("keystoreType", type(keystorePath));
        connector.setProperty("keystoreFile", keystorePath.getFileName().toString());
        connector.setProperty("clientAuth", "false");
        connector.setProperty("protocol", "HTTP/1.1");
        connector.setProperty("sslProtocol", "TLS");
        connector.setProperty("maxThreads", "200");
        connector.setProperty("protocol", "org.apache.coyote.http11.Http11AprProtocol");
        connector.setProperty("SSLEnabled", "true");
    }
    final public int port;
    final public String keyAlias;
    final public String keystorePass;
    final public Path keystorePath;
    final public Connector connector;

    public static TS_TomcatConnector ofUnSecure(int port) {
        return new TS_TomcatConnector(port, null, null, null);
    }

    //keyAlias = "tomcat"
    //keystorePass = "password"
    //keystorePath = "keystore.jks"
    public static TS_TomcatConnector ofSecure(int port, String keyAlias, String keystorePass, Path keystorePath) {
        return new TS_TomcatConnector(port, keyAlias, keystorePass, keystorePath);
    }

    public void destroy() {
        TGS_UnSafe.execute(() -> connector.destroy(), e -> d.ct("destroy", e));
    }
}
