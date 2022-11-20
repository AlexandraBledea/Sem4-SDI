package core.repository;

import core.domain.BusStation;
import org.springframework.data.repository.query.Param;

public interface IBusStationRepository extends IRepository<BusStation, Long> {

    BusStation findBusStationByName(@Param("name") String name);
}
