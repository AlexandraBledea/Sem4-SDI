package ro.ubb.catalog.core.repository.city.SQL;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.jpa.HibernateEntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.catalog.core.model.City;
import ro.ubb.catalog.core.repository.CustomRepositorySupport;
import ro.ubb.catalog.core.repository.city.CityRepository;
import ro.ubb.catalog.core.repository.city.CityRepositoryCustom;

import java.util.List;

@Component("CityRepositorySQLImpl")
public class CityRepositoryImpl extends CustomRepositorySupport
        implements CityRepositoryCustom {

    private static final Logger log = LoggerFactory.getLogger(CityRepository.class);

    @Override
    @Transactional
    public List<City> findAllWithStations() {
        log.trace("> Native SQL: findAllWithStations - method was entered.");

        HibernateEntityManager hibernateEntityManager = getEntityManager().unwrap(HibernateEntityManager.class);
        Session session = hibernateEntityManager.getSession();

        org.hibernate.Query query = session.createNativeQuery("select distinct {c.*}, {s.*} " +
                "from city c " +
                "left join busstation s on s.cityid = c.id ")
                        .addEntity("c", City.class)
                        .addJoin("s", "c.stations")
                        .addEntity("c", City.class)
                        .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        List<City> cities = query.getResultList();

        log.trace("> Native SQL: findAllWithStations - method was finished.");
        return cities;
    }

    @Override
    @Transactional
    public List<City> findAllWithStationsAndStops() {
        log.trace("> Native SQL: findAllWithStationsAndStops - method was entered.");

        HibernateEntityManager hibernateEntityManager = getEntityManager().unwrap(HibernateEntityManager.class);
        Session session = hibernateEntityManager.getSession();

        org.hibernate.Query query = session.createNativeQuery("select distinct {c.*}, {s.*}, {st.*}, {d.*}, {b.*} " +
                        "from city c " +
                        "left join busstation s on s.cityid = c.id " +
                        "left join busstop st on st.stationid = s.id " +
                        "left join bus b on b.id = st.busid " +
                        "left join driver d on d.id = b.driverid ")
                .addEntity("c", City.class)
                .addJoin("s", "c.stations")
                .addEntity("c", City.class)
                .addJoin("st", "s.stops")
                .addEntity("c", City.class)
                .addJoin("b", "st.bus")
                .addEntity("c", City.class)
                .addJoin("d", "b.driver")
                .addEntity("c", City.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        List<City> cities = query.getResultList();

        log.trace("> Native SQL: findAllWithStationsAndStops - method was finished.");
        return cities;
    }
}
