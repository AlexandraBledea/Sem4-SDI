package core.repository;

import core.domain.Driver;
import org.springframework.data.repository.query.Param;

public interface IDriverRepository extends IRepository<Driver, Long>{

    Driver findDriverByCnp(@Param("cnp") String cnp);
}
