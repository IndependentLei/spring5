package com.ji.spring5.test.boot.tomcat;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.apache.coyote.http11.Http11Nio2Protocol;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.Set;

public class TomcatApplication {
    public static void main(String[] args) {
        try {
            // 创建 Tomcat
            Tomcat tomcat = new Tomcat();
            tomcat.setBaseDir("tomcat");

            // 创建项目文件夹 ， 即 docBase 文件夹
            File docBase = Files.createTempDirectory("boot.").toFile();
            docBase.deleteOnExit();

            // 创建 Tomcat 项目，在 Tomcat 中称为 Context
            Context context = tomcat.addContext("",docBase.getAbsolutePath());

            // 编程添加 Servlet
            context.addServletContainerInitializer((Set<Class<?>> c, ServletContext ctx) -> ctx.addServlet("aaa",new MyServlet()).addMapping("/hello"), Collections.emptySet());

            // 启动 Tomcat
            tomcat.start();

            // 创建连接，设置监听端口
            Connector  connector = new Connector(new Http11Nio2Protocol());
            connector.setPort(8080);
            tomcat.setConnector(connector);
        } catch (IOException | LifecycleException e) {
            throw new RuntimeException(e);
        }
    }

    static class MyServlet extends HttpServlet{
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            System.out.println("---------> doGet");
            resp.setContentType("application/html");
            resp.getWriter().print("<h3>hello</h3>");
        }
    }
}
