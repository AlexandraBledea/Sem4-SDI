package client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@ComponentScan({"client.service", "client.tcp", "client.ui"})
public class ClientConfig {
    @Bean
    ExecutorService executorService() {
        return Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors()
        );
    }
}
