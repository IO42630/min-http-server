package com.olexyn.mock.http.server;


public class App {
    public static void main(String... args) throws Exception {
        new MockJettyServer().start();
    }
}
