package com.olexyn.mock.http.server;

import com.olexyn.mock.http.server.servlets.AsyncServlet;
import com.olexyn.mock.http.server.servlets.BlockingServlet;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

public class MockJettyServer {

    final static int PORT = 8090;

    final static int MAX_THREADS = 100;
    final static int MIN_THREADS = 10;
    final static int IDLE_TIMEOUT = 120;


    public void start() throws Exception {

        QueuedThreadPool threadPool = new QueuedThreadPool(MAX_THREADS, MIN_THREADS, IDLE_TIMEOUT);

        Server server = new Server(threadPool);
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(PORT);
        server.setConnectors(new Connector[]{connector});

        ServletHandler servletHandler = new ServletHandler();
        server.setHandler(servletHandler);

        servletHandler.addServletWithMapping(BlockingServlet.class, "/status");
        servletHandler.addServletWithMapping(AsyncServlet.class, "/heavy/async");

        server.start();
    }
}