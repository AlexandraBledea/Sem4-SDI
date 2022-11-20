package ro.ubb.catalog.core.repository.bus;


import ro.ubb.catalog.core.model.Bus;

import java.util.List;

public interface BusRepositoryCustom {

    List<Bus> findAllWithDriver();
    List<Bus> findAllWithDriverAndStops();
}
