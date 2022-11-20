package ro.ubb.catalog.core.repository.bus.JPQL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.Bus;
import ro.ubb.catalog.core.repository.CustomRepositorySupport;
import ro.ubb.catalog.core.repository.bus.BusRepository;
import ro.ubb.catalog.core.repository.bus.BusRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Component("BusRepositoryJPQLImpl")
public class BusRepositoryImpl extends CustomRepositorySupport
        implements BusRepositoryCustom {

    private static final Logger log = LoggerFactory.getLogger(BusRepository.class);

    @Override
    public List<Bus> findAllWithDriver() {
        log.trace("> JPQL: findAllWithDriver - method was entered. ");

        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery(
                "select distinct b from Bus b " +
                        "left join b.driver ");

        List<Bus> buses = query.getResultList();

        log.trace("> JPQL: findAllWithDriver - method was finished. ");

        return buses;
    }

    @Override
    public List<Bus> findAllWithDriverAndStops() {

        log.trace("> JPQL: findAllWithDriverAndStops - method was entered. ");

        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery(
                "select distinct b from Bus b " +
                        "left join fetch b.driver " +
                        "left join fetch b.stops s " +
                        "left join fetch s.station st "+
                        "left join fetch st.city c");

        List<Bus> buses = query.getResultList();

        log.trace("> JPQL: findAllWithDriverAndStops - method was finished. ");

        return buses;
    }
}
