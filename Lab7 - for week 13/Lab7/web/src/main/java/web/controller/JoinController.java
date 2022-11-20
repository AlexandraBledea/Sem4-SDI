package web.controller;

import core.service.IJoinService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import core.dto.BusStationFilterPopulationDTO;
import core.dto.BusStationsResponseFilterPopulationDTO;

import java.util.List;

@RestController
public class JoinController {

    public static final Logger logger = LoggerFactory.getLogger(JoinController.class);

    private final IJoinService joinService;

    public JoinController(IJoinService joinService) {
        this.joinService = joinService;
    }

    @RequestMapping("/join/filterBusStationsByPopulation/{maxPopulation}")
    BusStationsResponseFilterPopulationDTO filterBusStationByPopulation(@PathVariable int maxPopulation){
        logger.trace("filterBusStationsByPopulation method entered: - " + maxPopulation);

        List<BusStationFilterPopulationDTO> filteredStations = this.joinService.filterBusStationByPopulation(maxPopulation);

        BusStationsResponseFilterPopulationDTO response = new BusStationsResponseFilterPopulationDTO(filteredStations);

        logger.trace("filterBusStationsByPopulation method finished");

        return response;
    }
}
