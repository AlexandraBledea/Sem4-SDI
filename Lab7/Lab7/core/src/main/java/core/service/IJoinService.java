package core.service;

import core.dto.BusDTO;
import core.dto.BusStationFilterPopulationDTO;

import java.util.List;

public interface IJoinService {
    String HOST = "localhost";
    int PORT = 8085;

    List<BusStationFilterPopulationDTO> filterBusStationByPopulation(int maxPopulation);

}
