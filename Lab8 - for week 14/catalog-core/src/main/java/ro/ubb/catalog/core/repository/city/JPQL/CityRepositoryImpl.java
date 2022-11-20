package ro.ubb.catalog.core.repository.city.JPQL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.City;
import ro.ubb.catalog.core.repository.CustomRepositorySupport;
import ro.ubb.catalog.core.repository.bus.BusRepository;
import ro.ubb.catalog.core.repository.city.CityRepository;
import ro.ubb.catalog.core.repository.city.CityRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Component("CityRepositoryJPQLImpl")
public class CityRepositoryImpl extends CustomRepositorySupport
        implements CityRepositoryCustom {

    private static final Logger log = LoggerFactory.getLogger(CityRepository.class);

    @Override
    public List<City> findAllWithStations() {

        log.trace("> JPQL: findAllWithStations - method was entered.");

        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery("" +
                "select distinct c from City c " +
                "left join fetch c.stations s");
        List<City> cities = query.getResultList();

        log.trace("> JPQL: findAllWithStations - method was finished.");
        return cities;
    }

    @Override
    public List<City> findAllWithStationsAndStops() {

        log.trace("> JPQL: findAllWithStationsAndStops - method was entered.");

        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery(
                "select distinct c from City c " +
                        "left join fetch c.stations s " +
                        "left join fetch s.stops st " +
                        "left join fetch st.bus b " +
                        "left join fetch b.driver d"
                );
        List<City> cities = query.getResultList();

        log.trace("> JPQL: findAllWithStationsAndStops - method was finished.");

        return cities;
    }
}
