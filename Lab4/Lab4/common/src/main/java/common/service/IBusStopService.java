package common.service;

import common.domain.BusStation;
import common.domain.BusStop;
import common.exceptions.BusManagementException;
import common.exceptions.ValidatorException;
import common.utils.Pair;

import java.time.LocalTime;
import java.util.Set;
import java.util.concurrent.Future;

public interface IBusStopService {

    String GET_ALL = "getBusStops";
    String SAVE_ONE = "saveBusStop";
    String UPDATE_ONE = "updateBusStop";
    String DELETE_ONE = "deleteBusStop";

    /**
     * Saves the given busStop to the BusStopRepository.
     *
     * @param busStop - given bus
     * @throws ValidatorException if busStop is not valid
     * @throws IllegalArgumentException if the given id is null
     * @return a message if the stop was saved successfully
     */
    Future<String> save(BusStop busStop);

    /**
     * Updates attributes of a given busStop
     * @param busStop - the bus stop with the new attributes
     * @throws ValidatorException if the new busStop is not valid
     * @throws IllegalArgumentException if the given id is null
     * @throws BusManagementException if the busStop with the given id doesn't exist
     * @return a message if the stop was updated successfully
     */
    Future<String> update(BusStop busStop);

    /**
     * Deletes a busStop based on its id from the BusRepository
     *
     * @param id - id of the busStop to be deleted
     * @throws BusManagementException   if the busStop does not exist
     * @throws IllegalArgumentException if the given id is null
     * @return a message if the stop was deleted successfully
     */
    Future<String> delete(Pair<Long, Long> id);

    /**
     *
     * @return the busStops from the repository
     */
    Future<Set<BusStop>> findAll();
}
