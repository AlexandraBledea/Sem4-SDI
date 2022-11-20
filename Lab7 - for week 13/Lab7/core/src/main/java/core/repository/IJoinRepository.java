package core.repository;

import core.domain.BusStation;
import core.dto.BusDTO;
import core.dto.BusStationDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import core.dto.BusStationFilterPopulationDTO;

import java.util.List;

public interface IJoinRepository extends IRepository<BusStation, Long> {


    @Query("SELECT new core.dto.BusStationFilterPopulationDTO(s.id, s.name, c.name, c.population) " +
           "FROM City c INNER JOIN BusStation s on c.id = s.city.id " +
           "WHERE c.population < :maxPopulation")
    List<BusStationFilterPopulationDTO> filterBusStationByPopulation(@Param("maxPopulation") int maxPopulation);


}
