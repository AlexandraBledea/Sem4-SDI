package ro.ubb.catalog.core.repository.busStation;


import ro.ubb.catalog.core.model.BusStation;

import java.util.List;

public interface BusStationRepositoryCustom {

    List<BusStation> findAllWithCityAndStops();
    List<BusStation> findAllWithCities();
}
