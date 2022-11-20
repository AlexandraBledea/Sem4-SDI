package client.ui.controller.async;

import core.domain.BusStop;
import core.dto.BusStopDTO;
import core.dto.BusStopSaveDTO;
import core.dto.BusStopUpdateDTO;
import core.dto.BusStopsDTO;
import core.exceptions.BusManagementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

@Service
public class AsyncBusStopController {
    public static final Logger logger = LoggerFactory.getLogger(AsyncBusController.class);

    private final String url = "http://localhost:8085/api/stops";

    private final ExecutorService executorService;

    private final RestTemplate restTemplate;

    public AsyncBusStopController(ExecutorService executorService, RestTemplate restTemplate) {
        this.executorService = executorService;
        this.restTemplate = restTemplate;
    }

    public CompletableFuture<Iterable<BusStop>> findAll(){
        return CompletableFuture.supplyAsync(() -> {
            try {
                BusStopsDTO stops = restTemplate.getForObject(url, BusStopsDTO.class);
                if (stops == null) {
                    throw new BusManagementException("Could not retrieve stops from server");
                }
                return stops.getStops().stream().map(stopDTO -> new BusStop(stopDTO.getId(), stopDTO.getStopTime(), stopDTO.getBus(), stopDTO.getBusStation())).collect(Collectors.toSet());
            } catch (ResourceAccessException resourceAccessException) {
                throw new BusManagementException("Inaccessible server");
            }
        }, executorService);
    }

    public CompletableFuture<String> save(Long busId, Long stationId, String stopTime){
        logger.trace("add stop - method entered and returned a completable future");
        return CompletableFuture.supplyAsync(() -> {
            try {
                BusStopSaveDTO s = new BusStopSaveDTO(busId, stationId, stopTime);
                restTemplate.postForObject(url, s, BusStopDTO.class);
                return "Stop added";
            } catch (ResourceAccessException resourceAccessException){
                return "Inaccessible server";
            }
            catch (Exception ex){
                return ex.getMessage();
            }
        }, executorService);
    }

    public CompletableFuture<String> update(Long id, String stopTime){
        logger.trace("update stop - method entered and returned a completable future");
        return CompletableFuture.supplyAsync(() -> {
            try{
                BusStopUpdateDTO s = new BusStopUpdateDTO(stopTime);
                s.setId(id);
                restTemplate.put(url + "/{id}", s, s.getId());
                return "Stop updated successfully!";
            } catch (ResourceAccessException resourceAccessException){
                throw new BusManagementException("Inaccessible server");
            }
            catch (Exception ex){
                throw new BusManagementException(ex.getMessage());
            }
        }, executorService);
    }

    public CompletableFuture<String> delete(Long id){
        logger.trace("delete stop - method entered and returned a completable future");
        return CompletableFuture.supplyAsync(() -> {
            try {
                restTemplate.delete(url + "/{id}", id);
                return "Stop deleted successfully!";
            } catch (ResourceAccessException resourceAccessException){
                throw new BusManagementException("Inaccessible server");
            }
            catch (Exception ex){
                throw new BusManagementException(ex.getMessage());
            }
        }, executorService);
    }

}
