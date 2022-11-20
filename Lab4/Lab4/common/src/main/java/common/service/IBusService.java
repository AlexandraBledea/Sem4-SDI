package common.service;

import common.domain.Bus;
import common.exceptions.BusManagementException;
import common.exceptions.ValidatorException;

import java.util.Set;
import java.util.concurrent.Future;

public interface IBusService {

    String GET_ALL = "getBuses";
    String SAVE_ONE = "saveBus";
    String UPDATE_ONE = "updateBus";
    String DELETE_ONE = "deleteBus";

    /**
     * Saves the given bus to the BusRepository.
     *
     * @param bus - given bus
     * @throws ValidatorException if bus is not valid
     * @throws IllegalArgumentException if the given id is null
     * @return a message if the bus was saved successfully
     */
    Future<String> save(Bus bus);

    /**
     * Updates attributes of a given bus
     * @param bus - the bus with the new attributes
     * @throws ValidatorException if the new bus is not valid
     * @throws BusManagementException if the bus with the given id doesn't exist
     * @throws IllegalArgumentException if the given id is null
     * @return a message if the bus was updated successfully
     */
    Future<String> update(Bus bus);

    /**
     * Deletes a bus based on its id from the BusRepository
     *
     * @param id - id of the bus to be deleted
     * @throws IllegalArgumentException if the given id is null
     * @throws BusManagementException   if the bus does not exist or
     *                                  if there are busStops connected to the bus
     * @return a message if the bus was deleted successfully
     */
    Future<String> delete(Long id);

    /**
     *
     * @return the buses from the repository
     */
    Future<Set<Bus>> findAll();
}
