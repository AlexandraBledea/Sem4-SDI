package ro.ubb.catalog.core.service;

import ro.ubb.catalog.core.model.Driver;

import java.util.List;

public interface IDriverService {

    void save(Driver driver);

    void update(Long id, String name);

    void delete(Long id);

    List<Driver> findAll();

    Driver findDriverByCnp(String cnp);
}
