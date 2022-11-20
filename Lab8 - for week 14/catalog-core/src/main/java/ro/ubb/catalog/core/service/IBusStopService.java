package ro.ubb.catalog.core.service;


import ro.ubb.catalog.core.exceptions.BusManagementException;
import ro.ubb.catalog.core.model.BusStop;

import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

public interface IBusStopService {

    /**
     * Saves the given busStop to the BusStopRepository.
     *
     * @param busId - the id of the bus which stops in the station
     * @param stationId - the id of the station where the bus stop
     * @param stopTime - the time of the stop
     * @throws IllegalArgumentException if the given id is null
     */
    void save(Long busId, Long stationId, String stopTime);

    /**
     * Updates attributes of a given busStop
     * @param stopTime - the time of the stop
     * @throws IllegalArgumentException if the given id is null
     * @throws BusManagementException if the busStop with the given id doesn't exist
     */
    void update(Long busId, Long stationId, String stopTime);

    /**
     * Deletes a busStop based on its id from the BusRepository
     *
     * @throws BusManagementException   if the busStop does not exist
     * @throws IllegalArgumentException if the given id is null
     */
    void delete(Long busId, Long stationId);

    /**
     *
     * @return the busStops from the repository
     */
    List<BusStop> findAll();

}
