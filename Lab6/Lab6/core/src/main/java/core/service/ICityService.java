package core.service;


import core.domain.City;
import core.exceptions.BusManagementException;
import core.exceptions.ValidatorException;

import java.util.List;
import java.util.Set;
import java.util.concurrent.Future;

public interface ICityService {

    /**
     * Saves the given city to the CityRepository.
     *
     * @param name - the name of the city
     * @param population - the population of the city
     * @throws ValidatorException if city is not valid
     * @throws IllegalArgumentException if the given id is null
     */
    void save(City city);

    /**
     * Updates attributes of a given city
     * @param id - the id of the city to be modified
     * @param name - the name of the city
     * @param population - the population of the city
     * @throws ValidatorException if the new city is not valid
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
}
