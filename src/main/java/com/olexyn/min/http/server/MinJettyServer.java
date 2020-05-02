package com.olexyn.min.http.server;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

import javax.servlet.Servlet;
import java.util.HashMap;
import java.util.Map;

public class MinJettyServer {

    public int PORT = 8080;
    public int MAX_THREADS = 128;
    public int MIN_THREADS = 16;
    public int IDLE_TIMEOUT = 120;

    final private Map<String, Class<? extends Servlet>> servletURIMapping = new HashMap<>();


    public void start() throws Exception {

        QueuedThreadPool threadPool = new QueuedThreadPool(MAX_THREADS, MIN_THREADS, IDLE_TIMEOUT);
        Server server = new Server(threadPool);
        ServerConnector connector = new ServerConnector(server);
        ServletHandler servletHandler = new ServletHandler();

        connector.setPort(PORT);
        server.setConnectors(new Connector[]{connector});
        server.setHandler(servletHandler);

        for (Map.Entry<String, Class<? extends Servlet>> entry : servletURIMapping.entrySet()) {
            servletHandler.addServletWithMapping(entry.getValue(), entry.getKey());
        }

        server.start();
    }


    public void addServletWithMapping(String pathSpec, Class<? extends Servlet> servlet) {
        servletURIMapping.put(pathSpec, servlet);
    }
}