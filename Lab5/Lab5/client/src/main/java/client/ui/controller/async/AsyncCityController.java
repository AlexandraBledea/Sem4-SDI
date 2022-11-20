package client.ui.controller.async;

import core.domain.City;
import core.exceptions.BusManagementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import core.dto.CitiesDTO;
import core.dto.CityDTO;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

@Service
public class AsyncCityController {
    public static final Logger logger = LoggerFactory.getLogger(AsyncCityController.class);

    private final String url = "http://localhost:8085/api/cities";

    private final ExecutorService executorService;

    private final RestTemplate restTemplate;

    public AsyncCityController(ExecutorService executorService, RestTemplate restTemplate) {
        this.executorService = executorService;
        this.restTemplate = restTemplate;
    }

    public CompletableFuture<Iterable<City>> findAll(){
        return CompletableFuture.supplyAsync(() -> {
            try {
                CitiesDTO cities = restTemplate.getForObject(url, CitiesDTO.class);
                if (cities == null)
                    throw new BusManagementException("Could not retrieve cities from server");
                return cities.getCities().stream().map(cityDTO -> new City(cityDTO.getId(), cityDTO.getName(), cityDTO.getPopulation())).collect(Collectors.toSet());
            } catch (ResourceAccessException resourceAccessException) {
                throw new BusManagementException("Inaccessible server");
            }
        }, executorService);
    }

    public CompletableFuture<String> save(String name, int population){
        logger.trace("add city - method entered and returned a completable future");
        return CompletableFuture.supplyAsync(() -> {
            try {
                CityDTO c = new CityDTO(name, population);
                restTemplate.postForObject(url, c, CityDTO.class);
                return "City added";
            } catch (ResourceAccessException resourceAccessException){
                return "Inaccessible server";
            }
            catch (Exception ex){
                return ex.getMessage();
            }
        }, executorService);
    }

    public CompletableFuture<String> update(Long id, String name, int population){
        logger.trace("update city - method entered and returned a completable future");
        return CompletableFuture.supplyAsync(() -> {
            try{
                CityDTO c = new CityDTO(name, population);
                c.setId(id);
                restTemplate.put(url + "/{id}", c, c.getId());
                return "City updated successfully!";
            } catch (ResourceAccessException resourceAccessException){
                throw new BusManagementException("Inaccessible server");
            }
            catch (Exception ex){
                throw new BusManagementException(ex.getMessage());
            }
        }, executorService);
    }

    public CompletableFuture<String> delete(Long id){
        logger.trace("delete city - method entered and returned a completable future");
        return CompletableFuture.supplyAsync(() -> {
            try {
                restTemplate.delete(url + "/{id}", id);
                return "City deleted successfully!";
            } catch (ResourceAccessException resourceAccessException){
                throw new BusManagementException("Inaccessible server");
            }
            catch (Exception ex){
                throw new BusManagementException(ex.getMessage());
            }
        }, executorService);
    }
}
