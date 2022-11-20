package core.service;

import core.domain.Driver;
import core.dto.BusStationDTO;
import core.dto.DriverDTO;
import core.dto.PagingRequest;
import core.dto.PagingResponse;

import java.util.List;

public interface IDriverService {

    void save(Driver driver);

    void update(Long id, String name);

    void delete(Long id);

    List<Driver> findAll();

    PagingResponse<DriverDTO> findAll(PagingRequest pagingRequest);

    Driver findDriverByCnp(String cnp);
}
