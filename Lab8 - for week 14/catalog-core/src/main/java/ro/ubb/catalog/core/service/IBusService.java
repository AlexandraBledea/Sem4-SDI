package ro.ubb.catalog.core.service;


import ro.ubb.catalog.core.exceptions.BusManagementException;
import ro.ubb.catalog.core.model.Bus;
import ro.ubb.catalog.core.model.BusStop;

import java.util.List;
import java.util.Set;
import java.util.concurrent.Future;

public interface IBusService {

    /**
     * Saves the given bus to the BusRepository.
     *
     * @param modelName - the model of the bus
     * @param fuel - the fuel of the bus
     * @param capacity - the capacity of the bus
     * @throws IllegalArgumentException if the given id is null
     */
    void save(String modelName, String fuel, int capacity, Long driverId);

    /**
     * Updates attributes of a given bus
     * @param busId - the id of bus to be modified
     * @param modelName - the model of the bus
     * @param fuel - the fuel of the bus
     * @param capacity - the capacity of the bus
     * @throws BusManagementException if the bus with the given id doesn't exist
     * @throws IllegalArgumentException if the given id is null
     */
    void update(Long busId, String modelName, String fuel, int capacity);

    /**
     * Deletes a bus based on its id from the BusRepository
     *
     * @param id - id of the bus to be deleted
     * @throws IllegalArgumentException if the given id is null
     * @throws BusManagementException   if the bus does not exist or
     *                                  if there are busStops connected to the bus
     */
    void delete(Long id);

    /**
     *
     * @return the buses from the repository
     */
    List<Bus> findAll();

    Bus findBusByModelName(String modelName);

    Set<BusStop> findAllStopsForBus(Long id);
}
