package client;

import client.ui.Console;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args){
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("client.config");

        Console ui = (Console) context.getBean("console");

        ui.start();
    }
}
