package ro.ubb.catalog.core.repository.city.CriteriaAPI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.*;
import ro.ubb.catalog.core.repository.CustomRepositorySupport;
import ro.ubb.catalog.core.repository.city.CityRepository;
import ro.ubb.catalog.core.repository.city.CityRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.List;

@Component("CityRepositoryCriteriaAPIImpl")
public class CityRepositoryImpl extends CustomRepositorySupport
        implements CityRepositoryCustom {

    private static final Logger log = LoggerFactory.getLogger(CityRepository.class);

    @Override
    public List<City> findAllWithStations() {
        log.trace("> Criteria API: findAllWithStations - method was entered.");

        EntityManager entityManager = getEntityManager();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<City> query = criteriaBuilder.createQuery(City.class);
        query.distinct(Boolean.TRUE);

        Root<City> root = query.from(City.class);

//        Fetch<City, BusStation> cityBusStationFetch = root.fetch(City_.stations, JoinType.LEFT);

        List<City> cities = entityManager.createQuery(query).getResultList();

        log.trace("> Criteria API: findAllWithStations - method was finished.");

        return cities;
    }

    @Override
    public List<City> findAllWithStationsAndStops() {
        log.trace("> Criteria API: findAllWithStationsAndStops - method was entered.");


        EntityManager entityManager = getEntityManager();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<City> query = criteriaBuilder.createQuery(City.class);
        query.distinct(Boolean.TRUE);

        Root<City> root = query.from(City.class);

        Fetch<City, BusStation> cityBusStationFetch = root.fetch(City_.stations, JoinType.LEFT);
        cityBusStationFetch.fetch(BusStation_.stops, JoinType.LEFT)
                        .fetch(BusStop_.bus, JoinType.LEFT)
                                .fetch(Bus_.driver, JoinType.LEFT);

        List<City> cities = entityManager.createQuery(query).getResultList();

        log.trace("> Criteria API: findAllWithStationsAndStops - method was finished.");

        return cities;
    }
}