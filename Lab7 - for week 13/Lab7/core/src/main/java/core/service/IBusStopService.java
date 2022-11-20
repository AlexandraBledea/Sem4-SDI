package core.service;


import core.domain.BusStop;
import core.dto.BusStationDTO;
import core.dto.BusStopDTO;
import core.dto.PagingRequest;
import core.dto.PagingResponse;
import core.exceptions.BusManagementException;
import core.exceptions.ValidatorException;

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
     * @throws ValidatorException if busStop is not valid
     * @throws IllegalArgumentException if the given id is null
     */
    void save(Long busId, Long stationId, String stopTime);

    /**
     * Updates attributes of a given busStop
     * @param id - the id of the bus stop
     * @param stopTime - the time of the stop
     * @throws ValidatorException if the new busStop is not valid
     * @throws IllegalArgumentException if the given id is null
     * @throws BusManagementException if the busStop with the given id doesn't exist
     */
    void update(Long busId, Long stationId, String stopTime);

    /**
     * Deletes a busStop based on its id from the BusRepository
     *
     * @param id - id of the busStop to be deleted
     * @throws BusManagementException   if the busStop does not exist
     * @throws IllegalArgumentException if the given id is null
     */
    void delete(Long busId, Long stationId);

    /**
     *
     * @return the busStops from the repository
     */
    List<BusStop> findAll();

    PagingResponse<BusStopDTO> findAll(PagingRequest pagingRequest);

    PagingResponse<BusStopDTO> findAllFilteredByStopTime(PagingRequest pagingRequest, String stopTime);

    BusStop findOne(Long busId, Long stationId);
}
