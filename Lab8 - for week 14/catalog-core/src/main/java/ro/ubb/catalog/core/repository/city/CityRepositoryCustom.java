package ro.ubb.catalog.core.repository.city;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import ro.ubb.catalog.core.model.City;

import java.util.List;

public interface CityRepositoryCustom {
    List<City> findAllWithStations();
    List<City> findAllWithStationsAndStops();
}
