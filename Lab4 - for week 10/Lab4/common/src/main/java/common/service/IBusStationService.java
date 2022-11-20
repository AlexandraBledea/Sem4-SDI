package common.service;

import common.domain.BusStation;
import common.exceptions.BusManagementException;
import common.exceptions.ValidatorException;

import java.util.Set;
import java.util.concurrent.Future;

public interface IBusStationService {

    String GET_ALL = "getBusStations";
    String SAVE_ONE = "saveBusStation";
    String UPDATE_ONE = "updateBusStation";
    String DELETE_ONE = "deleteBusStation";

    /**
     * Saves the given busStation to the BusStationRepository.
     *
     * @param busStation - given busStation
     * @throws ValidatorException if busStation is not valid
     * @throws IllegalArgumentException if the given id is null
     * @return a message if the bus station was saved successfully
     */

    Future<String> save(BusStation busStation);

    /**
     * Updates attributes of a given busStation
     * @param busStation - the bus station with the new attributes
     * @throws ValidatorException if the new busStation is not valid
     * @throws IllegalArgumentException if the given id is null
     * @throws BusManagementException if the busStation with the given id doesn't exist
     * @return a message if the bus station was updated successfully
     */
    Future<String> update(BusStation busStation);

    /**
     * Deletes a busStation based on it's id from the BusStationRepository
     *
     * @param id - id of the busStation to be deleted
     * @throws IllegalArgumentException if the given id is null
     * @throws BusManagementException   if the busStation does not exist or
     *                                  if there are busStops connected to the busStation
     * @return a message if the bus station was deleted successfully
     */
    Future<String> delete(Long id);

    /**
     *
     * @return the busStations from the repository
     */
    Future<Set<BusStation>> findAll();
}
