package ro.ubb.catalog.core.repository.busStation.JPQL;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.BusStation;
import ro.ubb.catalog.core.repository.CustomRepositorySupport;
import ro.ubb.catalog.core.repository.bus.BusRepository;
import ro.ubb.catalog.core.repository.busStation.BusStationRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Component("BusStationRepositoryJPQLImpl")
public class BusStationRepositoryImpl extends CustomRepositorySupport
        implements BusStationRepositoryCustom {

    private static final Logger log = LoggerFactory.getLogger(BusRepository.class);

    @Override
    public List<BusStation> findAllWithCityAndStops() {
        log.trace("> JPQL: findAllWithCityAndStops - method was entered.");

        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery(
                "select distinct s from BusStation s " +
                        "left join fetch s.stops st " +
                        "left join fetch s.city c " +
                        "left join fetch st.bus b " +
                        "left join fetch b.driver d ");
        List<BusStation> stations = query.getResultList();

        log.trace("> JPQL: findAllWithCityAndStops - method was finished.");

        return stations;
    }

    @Override
    public List<BusStation> findAllWithCities() {
        log.trace("> JPQL: findAllWithCities - method was entered.");

        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery(
                "select distinct s from BusStation s " +
                        "left join fetch s.city");
        List<BusStation> stations = query.getResultList();

        log.trace("> JPQL: findAllWithCities - method was finished.");
        return stations;
    }
}
