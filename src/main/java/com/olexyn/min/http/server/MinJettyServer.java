package com.olexyn.min.http.server;

import jakarta.servlet.Servlet;
import org.eclipse.jetty.ee10.servlet.ServletContextHandler;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

import java.util.HashMap;
import java.util.Map;

public class MinJettyServer {

    public int PORT = 8080;
    public int MAX_THREADS = 128;
    public int MIN_THREADS = 16;
    public int IDLE_TIMEOUT = 120;

    final private Map<String, Class<? extends Servlet>> servletURIMapping = new HashMap<>();


    public void start() {

        QueuedThreadPool threadPool = new QueuedThreadPool(MAX_THREADS, MIN_THREADS, IDLE_TIMEOUT);
        Server server = new Server(threadPool);
        ServerConnector connector = new ServerConnector(server);


        connector.setPort(PORT);
        server.setConnectors(new Connector[]{connector});




        ServletContextHandler servletHandler = new ServletContextHandler();

        for (Map.Entry<String, Class<? extends Servlet>> entry : servletURIMapping.entrySet()) {
            servletHandler.addServlet(entry.getValue(), entry.getKey());
        }

        server.setHandler(servletHandler);
        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void addServletWithMapping(String path, Class<? extends Servlet> servlet) {
        servletURIMapping.put(path, servlet);
    }
}
