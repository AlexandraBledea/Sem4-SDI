package ro.ubb.catalog.core.repository.busStation.SQL;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.jpa.HibernateEntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.catalog.core.model.Bus;
import ro.ubb.catalog.core.model.BusStation;
import ro.ubb.catalog.core.repository.CustomRepositorySupport;
import ro.ubb.catalog.core.repository.bus.BusRepository;
import ro.ubb.catalog.core.repository.busStation.BusStationRepositoryCustom;

import java.util.List;

@Component("BusStationRepositorySQLImpl")
public class BusStationRepositoryImpl extends CustomRepositorySupport
        implements BusStationRepositoryCustom {

    private static final Logger log = LoggerFactory.getLogger(BusRepository.class);

    @Override
    @Transactional
    public List<BusStation> findAllWithCityAndStops() {

        log.trace("> Native SQL: findAllWithCityAndStops - method was entered.");

        HibernateEntityManager hibernateEntityManager = getEntityManager().unwrap(HibernateEntityManager.class);
        Session session = hibernateEntityManager.getSession();

        org.hibernate.Query query = session.createNativeQuery("select distinct {s.*}, {c.*}, {st.*}, {b.*}, {d.*} " +
                "from busstation s " +
                "left join city c on c.id = s.cityid " +
                "left join busstop st on st.stationid = s.id " +
                "left join bus b on b.id = st.busid " +
                "left join driver d on d.id = b.driverid ")
                        .addEntity("s", BusStation.class)
                        .addJoin("c", "s.city")
                        .addEntity("s", BusStation.class)
                        .addJoin("st", "s.stops")
                        .addEntity("s", BusStation.class)
                        .addJoin("b", "st.bus")
                        .addEntity("s", BusStation.class)
                        .addJoin("d", "b.driver")
                        .addEntity("s", BusStation.class)
                        .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        List<BusStation> stations = query.getResultList();

        log.trace("> Native SQL: findAllWithCityAndStops - method was finished.");

        return stations;
    }

    @Override
    @Transactional
    public List<BusStation> findAllWithCities() {

        log.trace("> Native SQL: findAllWithCities - method was entered.");

        HibernateEntityManager hibernateEntityManager = getEntityManager().unwrap(HibernateEntityManager.class);
        Session session = hibernateEntityManager.getSession();

        org.hibernate.Query query = session.createNativeQuery("select distinct {s.*}, {c.*} " +
                        "from busstation s " +
                        "left join city c on c.id = s.cityid ")
                .addEntity("s", BusStation.class)
                .addJoin("c", "s.city")
                .addEntity("s", BusStation.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        List<BusStation> stations = query.getResultList();

        log.trace("> Native SQL: findAllWithCities - method was finished.");
        return stations;
    }
}
