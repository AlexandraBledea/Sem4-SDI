package core.repository;

import core.domain.Bus;
import core.dto.BusDTO;
import core.dto.BusFilterDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface IBusRepository extends IRepository<Bus, Long>{

    @Query(value = "SELECT b FROM Bus b WHERE b.modelName=:modelName")
    List<Bus> filterBusesByModelName(@Param("modelName") String modelName);

//    @Query(value = "SELECT new core.dto.BusFilterDTO(b.id, b.modelName, b.fuel, b.capacity) FROM Bus b ORDER BY b.capacity ASC")

    @Query(value = "SELECT b FROM Bus b order by b.capacity asc")
    List<Bus> sortBusesByCapacityInAscendingOrder();

    List<Bus> findAllByOrderByCapacityAsc();
}
