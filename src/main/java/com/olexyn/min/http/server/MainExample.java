package com.olexyn.min.http.server;

import com.olexyn.min.http.server.servlets.AsyncServlet;
import com.olexyn.min.http.server.servlets.BlockingServlet;


/**
 * How to embed this server.
 */
public class MainExample {
    public static void main(String... args) throws Exception {

        MinJettyServer server = new MinJettyServer();

        server.PORT = 8090;
        server.MAX_THREADS = 100;
        server.MIN_THREADS = 10;
        server.IDLE_TIMEOUT = 120;

        server.addServletWithMapping("/status", BlockingServlet.class);
        server.addServletWithMapping("/heavy/async", AsyncServlet.class);

        new MinJettyServer().start();
    }
}
