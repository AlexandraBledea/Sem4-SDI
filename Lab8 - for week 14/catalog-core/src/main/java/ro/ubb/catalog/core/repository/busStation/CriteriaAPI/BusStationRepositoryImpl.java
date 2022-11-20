package ro.ubb.catalog.core.repository.busStation.CriteriaAPI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.*;
import ro.ubb.catalog.core.repository.CustomRepositorySupport;
import ro.ubb.catalog.core.repository.bus.BusRepository;
import ro.ubb.catalog.core.repository.busStation.BusStationRepository;
import ro.ubb.catalog.core.repository.busStation.BusStationRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.List;

@Component("BusStationRepositoryCriteriaAPIImpl")
public class BusStationRepositoryImpl extends CustomRepositorySupport
        implements BusStationRepositoryCustom {

    private static final Logger log = LoggerFactory.getLogger(BusRepository.class);

    @Override
    public List<BusStation> findAllWithCityAndStops() {
        log.trace("> CriteriaAPI: findAllWithCityAndStops - method was entered. ");

        EntityManager entityManager = getEntityManager();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<BusStation> query = criteriaBuilder.createQuery(BusStation.class);
        query.distinct(Boolean.TRUE);

        Root<BusStation> root = query.from(BusStation.class);

        Fetch<BusStation, City> stationCityFetch = root.fetch(BusStation_.city, JoinType.LEFT);
        Fetch<BusStation, BusStop> stationBusStopFetch = root.fetch(BusStation_.stops, JoinType.LEFT);
        stationBusStopFetch.fetch(BusStop_.bus, JoinType.LEFT).fetch(Bus_.driver, JoinType.LEFT);

        List<BusStation> stations = entityManager.createQuery(query).getResultList();

        log.trace("> CriteriaAPI: findAllWithCityAndStops - method was finished. ");

        return stations;
    }

    @Override
    public List<BusStation> findAllWithCities() {
        log.trace("> CriteriaAPI: findAllWithCities - method was entered. ");

        EntityManager entityManager = getEntityManager();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<BusStation> query = criteriaBuilder.createQuery(BusStation.class);
        query.distinct(Boolean.TRUE);

        Root<BusStation> root = query.from(BusStation.class);


        Fetch<BusStation, City> stationCityFetch = root.fetch(BusStation_.city, JoinType.LEFT);

        List<BusStation> stations = entityManager.createQuery(query).getResultList();

        log.trace("> CriteriaAPI: findAllWithCities - method was finished. ");

        return stations;
    }
}
