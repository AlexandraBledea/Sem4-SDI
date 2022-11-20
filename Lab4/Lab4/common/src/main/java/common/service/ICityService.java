package common.service;

import common.domain.City;
import common.exceptions.ValidatorException;
import common.exceptions.BusManagementException;

import java.util.Set;
import java.util.concurrent.Future;

public interface ICityService {

    String GET_ALL = "getCities";
    String SAVE_ONE = "saveCity";
    String UPDATE_ONE = "updateCity";
    String DELETE_ONE = "deleteCity";

    /**
     * Saves the given city to the CityRepository.
     *
     * @param city - given city
     * @throws ValidatorException if city is not valid
     * @throws IllegalArgumentException if the given id is null
     * @return a message if the city was saved successfully
     */
    Future<String> save(City city);

    /**
     * Updates attributes of a given city
     * @param city - the city with the new attributes
     * @throws ValidatorException if the new city is not valid
     * @throws IllegalArgumentException if the given id is null
     * @throws BusManagementException if the city with the given id doesn't exist
     * @return a messgae if the city was updated successfully
     */
    Future<String> update(City city);

    /**
     * Deletes a city based on it's id from the CityRepository
     *
     * @param id - id of the city to be deleted
     * @throws IllegalArgumentException if the given id is null
     * @throws BusManagementException   if the city does not exist or
     *                                  if there are busStations connected to the city
     * @return a message if the city was deleted successfully
     */
    Future<String> delete(Long id);

    /**
     *
     * @return the cities from the repository
     */
    Future<Set<City>> findAll();
}
