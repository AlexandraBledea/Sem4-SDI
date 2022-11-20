package core.repository;

import core.domain.City;
import core.dto.CityDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ICityRepository extends IRepository<City, Long>{

//    @Query(value = "SELECT c FROM City c WHERE c.name=: name")
    City findCityByName(@Param("name") String name);
}
