package core.service;

import core.repository.IJoinRepository;
import org.springframework.stereotype.Service;
import core.dto.BusStationFilterPopulationDTO;

import java.util.List;

@Service
public class JoinServiceImpl implements IJoinService{

    private final IJoinRepository joinRepository;

    public JoinServiceImpl(IJoinRepository joinRepository){
        this.joinRepository = joinRepository;
    }


    @Override
    public List<BusStationFilterPopulationDTO> filterBusStationByPopulation(int maxPopulation) {
        return this.joinRepository.filterBusStationByPopulation(maxPopulation);
    }
}
