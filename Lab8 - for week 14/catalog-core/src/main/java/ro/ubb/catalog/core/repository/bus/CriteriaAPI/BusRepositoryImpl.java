package ro.ubb.catalog.core.repository.bus.CriteriaAPI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.*;
import ro.ubb.catalog.core.repository.CustomRepositorySupport;
import ro.ubb.catalog.core.repository.bus.BusRepository;
import ro.ubb.catalog.core.repository.bus.BusRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.List;

@Component("BusRepositoryCriteriaAPIImpl")
public class BusRepositoryImpl extends CustomRepositorySupport
        implements BusRepositoryCustom {

    private static final Logger log = LoggerFactory.getLogger(BusRepository.class);

    @Override
    public List<Bus> findAllWithDriver() {

        log.trace("> CriteriaAPI: findAllWithDriver - method was entered. ");

        EntityManager entityManager = getEntityManager();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Bus> query = criteriaBuilder.createQuery(Bus.class);
        query.distinct(Boolean.TRUE);

        Root<Bus> root = query.from(Bus.class);

        Fetch<Bus, Driver> busDriverFetch = root.fetch(Bus_.driver, JoinType.LEFT);

        log.trace("> CriteriaAPI: findAllWithDriver - method was finished. ");

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<Bus> findAllWithDriverAndStops() {
        log.trace("> CriteriaAPI: findAllWithDriverAndStops - method was entered. ");

        EntityManager entityManager = getEntityManager();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Bus> query = criteriaBuilder.createQuery(Bus.class);
        query.distinct(Boolean.TRUE);

        Root<Bus> root = query.from(Bus.class);
        Fetch<Bus, Driver> busDriverFetch = root.fetch(Bus_.driver, JoinType.LEFT);
        Fetch<Bus, BusStop> busBusStopFetch = root.fetch(Bus_.stops, JoinType.LEFT);
        busBusStopFetch.fetch(BusStop_.station, JoinType.LEFT).fetch(BusStation_.city, JoinType.LEFT);

        List<Bus> buses = entityManager.createQuery(query).getResultList();

        log.trace("> CriteriaAPI: findAllWithDriverAndStops - method was finished. ");

        return buses;
    }
}
