package ro.ubb.catalog.core.repository.bus;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.Bus;
import ro.ubb.catalog.core.repository.IRepository;

import java.util.List;

@Component("BusRepositoryCriteriaAPI")
public interface BusRepository extends IRepository<Bus, Long>, BusRepositoryCustom {

    @Query("select distinct b from Bus b")
    @EntityGraph(value="busesWithDriver", type = EntityGraph.EntityGraphType.LOAD)
    List<Bus> findAllWithDriverQuery();

    @Query("select distinct b from Bus b")
    @EntityGraph(value="busesWithDriverAndStops", type = EntityGraph.EntityGraphType.LOAD)
    List<Bus> findAllWithDriverAndStopsQuery();

}
