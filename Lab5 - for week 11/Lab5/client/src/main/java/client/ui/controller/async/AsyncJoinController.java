package client.ui.controller.async;

import core.dto.BusStationFilterPopulationDTO;
import core.dto.BusStationsResponseFilterPopulationDTO;
import core.exceptions.BusManagementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@Service
public class AsyncJoinController {

    public static final Logger logger = LoggerFactory.getLogger(AsyncJoinController.class);

    private final String url = "http://localhost:8085/api/join";

    private final ExecutorService executorService;

    private final RestTemplate restTemplate;

    public AsyncJoinController(ExecutorService executorService, RestTemplate restTemplate){
        this.executorService = executorService;
        this.restTemplate = restTemplate;
    }

    public CompletableFuture<List<BusStationFilterPopulationDTO>> filterBusStationsByPopulation(int maxPopulation) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                BusStationsResponseFilterPopulationDTO filteredStations = restTemplate.getForObject(url + "/filterBusStationsByPopulation/" + maxPopulation, BusStationsResponseFilterPopulationDTO.class);
                if (filteredStations == null)
                    throw new BusManagementException("There is no data for the given filter");

                return filteredStations.getFilteredStationsDTO();
            } catch (ResourceAccessException e) {
                throw new BusManagementException("Inaccessible server");
            }
        }, executorService);
    }

}
