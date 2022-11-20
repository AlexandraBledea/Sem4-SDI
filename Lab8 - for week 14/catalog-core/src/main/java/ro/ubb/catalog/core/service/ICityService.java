package ro.ubb.catalog.core.service;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import ro.ubb.catalog.core.exceptions.BusManagementException;
import ro.ubb.catalog.core.model.BusStation;
import ro.ubb.catalog.core.model.BusStop;
import ro.ubb.catalog.core.model.City;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Future;

public interface ICityService {

    /**
     * Saves the given city to the CityRepository.
     *
     * @param city - the city to be saved
     * @throws IllegalArgumentException if the given id is null
     */
    void save(City city);

    /**
     * Updates attributes of a given city
     * @param id - the id of the city to be modified
     * @param name - the name of the city
     * @param population - the population of the city
     * @throws IllegalArgumentException if the given id is null
     * @throws BusManagementException if the city with the given id doesn't exist
     */
    void update(Long id, String name, int population);

    /**
     * Deletes a city based on it's id from the CityRepository
     *
     * @param id - id of the city to be deleted
     * @throws IllegalArgumentException if the given id is null
     * @throws BusManagementException   if the city does not exist or
     *                                  if there are busStations connected to the city
     */
    void delete(Long id);

    /**
     *
     * @return the cities from the repository
     */
    List<City> findAll();

    City findCityByName(String name);

    Set<BusStation> findAllStationsForCity(Long id);

    Set<BusStop> findAllStopsForCity(Long cityId, Long stationId);
}
