package ro.ubb.catalog.core.repository.busStation;


import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.BusStation;
import ro.ubb.catalog.core.repository.IRepository;

import java.util.List;
@Component("BusStationRepositorySQL")
public interface BusStationRepository extends IRepository<BusStation, Long>, BusStationRepositoryCustom {

    @Query("select distinct s from BusStation s")
    @EntityGraph(value="stationWithCityAndStops", type = EntityGraph.EntityGraphType.LOAD)
    List<BusStation> findAllWithCityAndStopsQuery();

    @Query("select distinct s from BusStation s")
    @EntityGraph(value="stationWithCities", type = EntityGraph.EntityGraphType.LOAD)
    List<BusStation> findAllWithCitiesQuery();

}
