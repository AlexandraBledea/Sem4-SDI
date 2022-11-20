package ro.ubb.catalog.core.repository.bus.SQL;

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
import ro.ubb.catalog.core.repository.bus.BusRepositoryCustom;

import java.util.List;

@Component("BusRepositorySQLImpl")
public class BusRepositoryImpl extends CustomRepositorySupport
        implements BusRepositoryCustom {

    private static final Logger log = LoggerFactory.getLogger(BusRepository.class);


    @Override
    @Transactional
    public List<Bus> findAllWithDriver() {
        log.trace("> Native SQL: findAllWithDriver - method was entered. ");

        HibernateEntityManager hibernateEntityManager = getEntityManager().unwrap(HibernateEntityManager.class);
        Session session = hibernateEntityManager.getSession();

        org.hibernate.Query query = session.createNativeQuery("select distinct {b.*}, {d.*} " +
                "from bus b " +
                "left join driver d on d.id = b.driverid")
                .addEntity("b", Bus.class)
                .addJoin("d", "b.driver")
                .addEntity("b", Bus.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        List<Bus> buses = query.getResultList();

        log.trace("> Native SQL: findAllWithDriver - method was finished. ");

        return buses;
    }

    @Override
    @Transactional
    public List<Bus> findAllWithDriverAndStops() {
        log.trace("> Native SQL: findAllWithDriverAndStops - method was entered. ");

        HibernateEntityManager hibernateEntityManager = getEntityManager().unwrap(HibernateEntityManager.class);
        Session session = hibernateEntityManager.getSession();

        org.hibernate.Query query= session.createNativeQuery("select distinct {b.*}, {d.*}, {s.*}, {st.*}, {c.*} " +
                "from bus b " +
                "left join driver d on d.id = b.driverid " +
                "left join busstop s on b.id = s.busid " +
                "left join busstation st on st.id = s.stationid " +
                "left join city c on c.id = st.cityid")
                .addEntity("b", Bus.class)
                .addJoin("d", "b.driver")
                .addEntity("b", Bus.class)
                .addJoin("s", "b.stops")
                .addEntity("b", Bus.class)
                .addJoin("st", "s.station")
                .addEntity("b", Bus.class)
                .addJoin("c", "st.city")
                .addEntity("b", Bus.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        List<Bus> buses = query.getResultList();

        log.trace("> Native SQL: findAllWithDriverAndStops - method was finished. ");

        return buses;
    }
}
