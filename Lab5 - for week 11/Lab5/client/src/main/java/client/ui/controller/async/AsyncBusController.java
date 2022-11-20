package client.ui.controller.async;

import core.domain.Bus;
import core.exceptions.BusManagementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import core.dto.BusDTO;
import core.dto.BusesDTO;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

@Service
public class AsyncBusController {
    public static final Logger logger = LoggerFactory.getLogger(AsyncBusController.class);

    private final String url = "http://localhost:8085/api/buses";

    private final ExecutorService executorService;

    private final RestTemplate restTemplate;

    public AsyncBusController(ExecutorService executorService, RestTemplate restTemplate) {
        this.executorService = executorService;
        this.restTemplate = restTemplate;
    }

    public CompletableFuture<Iterable<Bus>> findAll(){
        return CompletableFuture.supplyAsync(() -> {
            try {
                BusesDTO buses = restTemplate.getForObject(url, BusesDTO.class);
                if (buses == null)
                    throw new BusManagementException("Could not retrieve buses from server");
                return buses.getBuses().stream().map(busDTO -> new Bus(busDTO.getId(), busDTO.getModelName(), busDTO.getFuel(), busDTO.getCapacity())).collect(Collectors.toSet());
            } catch (ResourceAccessException resourceAccessException) {
                throw new BusManagementException("Inaccessible server");
            }
        }, executorService);
    }

    public CompletableFuture<String> save(String modelName, String fuel, int capacity){
        logger.trace("add bus - method entered and returned a completable future");
        return CompletableFuture.supplyAsync(() -> {
            try {
                BusDTO b = new BusDTO(modelName, fuel, capacity);
                restTemplate.postForObject(url, b, BusDTO.class);
                return "Bus added";
            } catch (ResourceAccessException resourceAccessException){
                return "Inaccessible server";
            }
            catch (Exception ex){
                return ex.getMessage();
            }
        }, executorService);
    }

    public CompletableFuture<String> update(Long budId, String modelName, String fuel, int capacity){
        logger.trace("update bus - method entered and returned a completable future");
        return CompletableFuture.supplyAsync(() -> {
            try{
                BusDTO b = new BusDTO(modelName, fuel, capacity);
                b.setId(budId);
                restTemplate.put(url + "/{id}", b, b.getId());
                return "Bus updated successfully!";
            } catch (ResourceAccessException resourceAccessException){
                throw new BusManagementException("Inaccessible server");
            }
            catch (Exception ex){
                throw new BusManagementException(ex.getMessage());
            }
        }, executorService);
    }

    public CompletableFuture<String> delete(Long id){
        logger.trace("delete bus - method entered and returned a completable future");
        return CompletableFuture.supplyAsync(() -> {
            try {
                restTemplate.delete(url + "/{id}", id);
                return "Bus deleted successfully!";
            } catch (ResourceAccessException resourceAccessException){
                throw new BusManagementException("Inaccessible server");
            }
            catch (Exception ex){
                throw new BusManagementException(ex.getMessage());
            }
        }, executorService);
    }

}
