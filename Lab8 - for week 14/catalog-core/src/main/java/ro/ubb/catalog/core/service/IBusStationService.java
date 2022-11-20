package ro.ubb.catalog.core.service;


import ro.ubb.catalog.core.exceptions.BusManagementException;
import ro.ubb.catalog.core.model.BusStation;
import ro.ubb.catalog.core.model.BusStop;

import java.util.List;
import java.util.Set;
import java.util.concurrent.Future;

public interface IBusStationService {

    /**
     * Saves the given busStation to the BusStationRepository.
     *
     * @param name - the name of the station
     * @param cityId - the id of the city in which the station is
     * @throws IllegalArgumentException if the given id is null
     */

    void save(String name, Long cityId);

    /**
     * Updates attributes of a given busStation
     * @param id - the id of the bus station to be modified
     * @param name - the name of the station
     * @throws IllegalArgumentException if the given id is null
     * @throws BusManagementException if the busStation with the given id doesn't exist
     */
    void update(Long id, String name);

    /**
     * Deletes a busStation based on it's id from the BusStationRepository
     *
     * @param id - id of the busStation to be deleted
     * @throws IllegalArgumentException if the given id is null
     * @throws BusManagementException   if the busStation does not exist or
     *                                  if there are busStops connected to the busStation
     */
    void delete(Long id);

    /**
     *
     * @return the busStations from the repository
     */
    List<BusStation> findAll();

    BusStation findBusStationByName(String name);

    Set<BusStop> findAllStopsForStation(Long id);
}
