package client.ui.controller.async;

import core.domain.BusStation;
import core.dto.BusStationSaveDTO;
import core.dto.BusStationUpdateDTO;
import core.dto.BusStationsDTO;
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
public class AsyncBusStationController {
    public static final Logger logger = LoggerFactory.getLogger(AsyncBusStationController.class);

    private final String url = "http://localhost:8085/api/stations";

    private final ExecutorService executorService;

    private final RestTemplate restTemplate;

    public AsyncBusStationController(ExecutorService executorService, RestTemplate restTemplate) {
        this.executorService = executorService;
        this.restTemplate = restTemplate;
    }

    public CompletableFuture<Iterable<BusStation>> findAll(){
        return CompletableFuture.supplyAsync(() -> {
            try {
                BusStationsDTO stations = restTemplate.getForObject(url, BusStationsDTO.class);
                if (stations == null)
                    throw new BusManagementException("Could not retrieve stations from server");
                return stations.getStations().stream().map(busStationDTO -> new BusStation(busStationDTO.getId(), busStationDTO.getName(), busStationDTO.getCity())).collect(Collectors.toSet());
            } catch (ResourceAccessException resourceAccessException) {
                throw new BusManagementException("Inaccessible server");
            }
        }, executorService);
    }

    public CompletableFuture<String> save(String name, Long cityId){
        logger.trace("add station - method entered and returned a completable future");
        return CompletableFuture.supplyAsync(() -> {
            try {
                BusStationSaveDTO s = new BusStationSaveDTO(name, cityId);
                restTemplate.postForObject(url, s, BusStationSaveDTO.class);
                return "Station added";
            } catch (ResourceAccessException resourceAccessException){
                return "Inaccessible server";
            }
            catch (Exception ex){
                return ex.getMessage();
            }
        }, executorService);
    }

    public CompletableFuture<String> update(Long id, String name){
        logger.trace("update station - method entered and returned a completable future");
        return CompletableFuture.supplyAsync(() -> {
            try{
                BusStationUpdateDTO s = new BusStationUpdateDTO(name);
                s.setId(id);
                restTemplate.put(url + "/{id}", s, s.getId());
                return "Station updated successfully!";
            } catch (ResourceAccessException resourceAccessException){
                throw new BusManagementException("Inaccessible server");
            }
            catch (Exception ex){
                throw new BusManagementException(ex.getMessage());
            }
        }, executorService);
    }

    public CompletableFuture<String> delete(Long id){
        logger.trace("delete station - method entered and returned a completable future");
        return CompletableFuture.supplyAsync(() -> {
            try {
                restTemplate.delete(url + "/{id}", id);
                return "Station deleted successfully!";
            } catch (ResourceAccessException resourceAccessException){
                throw new BusManagementException("Inaccessible server");
            }
            catch (Exception ex){
                throw new BusManagementException(ex.getMessage());
            }
        }, executorService);
    }


}
