package server.config;

import common.validators.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@ComponentScan({"server.repository", "server.service", "server.tcp", "common.validators"})
public class ServerConfig {
    @Bean
    ExecutorService executorService(){
        return Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors()
        );
    }

//    @Bean
//    BusValidator busValidator(){
//        return new BusValidator();
//    }
//
//    @Bean
//    CityValidator cityValidator(){
//        return new CityValidator();
//    }
//
//    @Bean
//    BusStationValidator busStationValidator(){
//        return new BusStationValidator();
//    }
//
//    @Bean
//    BusStopValidator busStopValidator(){
//        return new BusStopValidator();
//    }
}
