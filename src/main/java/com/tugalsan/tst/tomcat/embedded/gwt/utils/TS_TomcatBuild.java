package com.tugalsan.tst.tomcat.embedded.gwt.utils;

import java.util.*;
import org.apache.catalina.core.*;
import org.apache.catalina.startup.*;
import org.apache.catalina.webresources.*;
import com.tugalsan.api.unsafe.client.*;
import com.tugalsan.tst.tomcat.embedded.gwt.servlets.*;

public class TS_TomcatBuild {

    public static TS_TomcatBall init(Class mainClass, CharSequence contextName_as_empty_or_slashName) {
        //ref folders
        var project = TS_TomcatPath.project();
        var project_src_main_webapp = TS_TomcatPath.project_src_main_webapp();
        var project_target_classes = TS_TomcatPath.project_target_classes();
        System.out.println("project:" + project);
        System.out.println("webapp:" + project_src_main_webapp);
        System.out.println("classes:" + project_target_classes);
        //warm up
        System.setProperty("org.apache.catalina.startup.EXIT_ON_INIT_FAILURE", "true");
        var tomcat = new Tomcat();
        //Set project dir
        tomcat.setBaseDir(project.toString());
        //Create context
        var context = (StandardContext) tomcat.addWebapp(
                contextName_as_empty_or_slashName.toString(),
                project_src_main_webapp.toString()
        );
        //Set execution independent of current thread context classloader (compatibility with exec:java mojo)
        context.setParentClassLoader(mainClass.getClassLoader());
        // Declare an alternative location for your "WEB-INF/classes" dir so Servlet 3.0 annotation will work
        var resources = new StandardRoot(context);
        resources.addPreResources(new DirResourceSet(
                resources,
                "/WEB-INF/classes",
                project_target_classes.toString(),
                "/"
        ));
        context.setResources(resources);
        //TODO DATABASE
        {
//        ContextResource resource = buildResource(
//            H2_DATA_SOURCE_NAME,
//            DataSource.class.getName(),
//            h2DatasourceProperties()
//        );
//        context.getNamingResources().addResource(resource);
//        context.getServletContext().addListener(DataBaseSchemaInit.class);
        }
        return new TS_TomcatBall(
                project, project_src_main_webapp, project_target_classes,
                tomcat, context, contextName_as_empty_or_slashName, resources,
                new ArrayList(), new ArrayList()
        );
    }

    public static void map(TS_TomcatBall tomcatBall, List<ServletAbstract> servlets) {
        map(tomcatBall, servlets.toArray(ServletAbstract[]::new));
    }

    public static void map(TS_TomcatBall tomcatBall, ServletAbstract... servlets) {
        tomcatBall.servlets().addAll(List.of(servlets));
        Arrays.asList(servlets).forEach(servlet -> {
            tomcatBall.tomcat().addServlet(
                    tomcatBall.contextName_as_empty_or_slashName().toString(),
                    servlet.name(),
                    servlet
            );
            tomcatBall.context().addServletMappingDecoded(
                    "/" + servlet.name(),
                    servlet.name()
            );
        });
    }

    public static void startAndLock(TS_TomcatBall tomcatBall, List<TS_TomcatConnector> connectorConfigs) {
        startAndLock(tomcatBall, connectorConfigs.toArray(TS_TomcatConnector[]::new));
    }

    public static void startAndLock(TS_TomcatBall tomcatBall, TS_TomcatConnector... connectors) {
        tomcatBall.connectors().addAll(List.of(connectors));
        TGS_UnSafe.execute(() -> {
            ServletDestroy.tomcatBall = tomcatBall;
            tomcatBall.tomcat().start();
            Arrays.asList(connectors)
                    .forEach(c -> tomcatBall.tomcat().getService().addConnector(c.connector));
            tomcatBall.tomcat().getServer().await();
        });
    }
}
