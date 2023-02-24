package ru.regiuss.practice.dictinoary.server;

import org.apache.catalina.startup.Tomcat;

import java.io.File;
import java.net.URL;

public class Main {
    public static void main(String[] args) throws Exception {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(7019);
        //tomcat.addWebapp("", isJar() ? getResourceFromJarFile() : getResourceFromFs());
        tomcat.addWebapp("", new File("src/main/").getAbsolutePath());
        tomcat.getConnector();
        tomcat.start();
        tomcat.getServer().await();
    }

    public static boolean isJar() {
        URL resource = Main.class.getResource("/");
        return resource == null;
    }

    public static String getResourceFromJarFile() {
        File jarFile = new File(System.getProperty("java.class.path"));
        return jarFile.getAbsolutePath();
    }

    public static String getResourceFromFs() {
        URL resource = Main.class.getResource("/");
        return resource.getFile();
    }
}
