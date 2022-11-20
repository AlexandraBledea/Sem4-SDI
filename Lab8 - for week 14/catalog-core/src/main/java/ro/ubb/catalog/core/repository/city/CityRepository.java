package ro.ubb.catalog.core.repository.city;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.City;
import ro.ubb.catalog.core.repository.IRepository;

import java.util.List;

@Component("CityRepositoryCriteriaAPI")
public interface CityRepository extends IRepository<City, Long>, CityRepositoryCustom {
    @Query("select distinct c from City c")
    @EntityGraph(value="citiesWithStations", type = EntityGraph.EntityGraphType.LOAD)
    List<City> findAllWithStationsQuery();

    @Query("select distinct c from City c")
    @EntityGraph(value = "citiesWithStationsAndStops", type = EntityGraph.EntityGraphType.LOAD)
    List<City> findAllWithStationsAndStopsQuery();

    City findCityByName(@Param("name") String name);

}
