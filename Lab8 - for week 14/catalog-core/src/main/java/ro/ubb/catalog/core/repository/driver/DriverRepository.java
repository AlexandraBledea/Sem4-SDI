package ro.ubb.catalog.core.repository.driver;

import org.springframework.data.repository.query.Param;
import ro.ubb.catalog.core.model.Driver;
import ro.ubb.catalog.core.repository.IRepository;

public interface DriverRepository extends IRepository<Driver, Long> {

    Driver findDriverByCnp(@Param("cnp") String cnp);
}