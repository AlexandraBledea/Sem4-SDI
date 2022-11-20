package server;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import server.tcp.ServerApp;

public class Main {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("server.config");

        ServerApp serverApp = (ServerApp) context.getBean("serverApp");

        serverApp.startServer();
    }

}
