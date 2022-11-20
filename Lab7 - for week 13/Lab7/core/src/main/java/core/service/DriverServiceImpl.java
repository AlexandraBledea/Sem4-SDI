package core.service;

import core.converter.DriverConverter;
import core.domain.Driver;
import core.dto.DriverDTO;
import core.dto.PagingRequest;
import core.dto.PagingResponse;
import core.exceptions.BusManagementException;
import core.repository.IDriverRepository;
import core.utils.RestPagingUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DriverServiceImpl implements IDriverService {
    private final IDriverRepository driverRepository;
    private final DriverConverter driverConverter;

    public static final Logger logger = LoggerFactory.getLogger(CityServiceImpl.class);

    public DriverServiceImpl(IDriverRepository driverRepository, DriverConverter driverConverter){
        this.driverRepository = driverRepository;
        this.driverConverter = driverConverter;
    }

    @Override
    public void save(Driver driver) {
        logger.trace("add driver - method entered - name: " + driver.getName() + ", cnp: " + driver.getCnp());

        this.driverRepository.save(driver);

        logger.trace("add driver - method finished");
    }

    @Override
    @Transactional
    public void update(Long id, String name) {
        logger.trace("update driver - method entered - id: " + id + ", name: " + name);

        this.driverRepository.findById(id)
                .ifPresentOrElse((d) -> {
                    d.setName(name);
                },
                    () -> {throw  new BusManagementException("Driver not found!");}
        );

        logger.trace("update driver - method finished");
    }

    @Override
    public void delete(Long id) {

        logger.trace("delete driver - method entered - id: " + id);

        driverRepository.findById(id)
                .ifPresentOrElse((d) -> driverRepository.deleteById(d.getId()),
                        () -> {
                            throw new BusManagementException("Driver not found!");
                        });

        logger.trace("delete driver - method finished");
    }

    @Override
    public List<Driver> findAll() {
        logger.trace("findAll drivers - method entered");

        List<Driver> drivers = driverRepository.findAll();

        logger.trace("findAll drivers - method finished " + drivers);

        return drivers;
    }

    @Override
    public PagingResponse<DriverDTO> findAll(PagingRequest pagingRequest) {
        if(RestPagingUtil.isRequestPaged(pagingRequest)){

            Page<Driver> page;
            page = this.driverRepository.findAll(RestPagingUtil.buildPageRequest(pagingRequest));

            List<Driver> driverList = page.getContent();
            List<DriverDTO> driverDTOS = this.driverConverter.convertModelsToDTOsList(driverList);

            return new PagingResponse<>(
                    page.getTotalElements(),
                    (long) page.getNumber(),
                    (long) page.getNumberOfElements(),
                    RestPagingUtil.buildPageRequest(pagingRequest).getOffset(),
                    (long) page.getTotalPages(),
                    driverDTOS);
        } else {
            List<Driver> drivers = this.driverRepository.findAll();
            List<DriverDTO> driverDTOList = this.driverConverter.convertModelsToDTOsList(drivers);

            return new PagingResponse<>(
                    (long) driverDTOList.size(),
                    0L,
                    0L,
                    0L,
                    0L,
                    driverDTOList);
        }
    }

    @Override
    public Driver findDriverByCnp(String cnp) {
        return this.driverRepository.findDriverByCnp(cnp);
    }
}
