package server.repository.databaseRepository;

import common.domain.City;
import common.exceptions.ValidatorException;
import common.validators.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import server.repository.IRepository;
//import repository.Repository;

import java.util.HashSet;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

@Repository
public class CityDBRepository implements IRepository<Long, City> {

    private final Validator<City> validator;
    private final JdbcOperations jdbcOperations;


    public CityDBRepository(Validator<City> validator, JdbcOperations jdbcOperations) {
        this.validator = validator;
        this.jdbcOperations = jdbcOperations;

    }

    /**
     * Find the entity with the given {@code id}.
     *
     * @param id
     *            must be not null.
     * @return an {@code Optional} encapsulating the entity with the given id.
     * @throws IllegalArgumentException
     *             if the given id is null.
     */
    @Override
    public Optional<City> findOne(Long aLong) {

        AtomicReference<Optional<City>> cityToReturn = new AtomicReference<>();

        Stream.ofNullable(aLong)
                .findAny()
                .ifPresentOrElse(
                        (val) -> {
                            String sqlQuery = "SELECT * FROM city WHERE cityid = " + val.toString();

                            cityToReturn.set(jdbcOperations.query(sqlQuery, (rs, i) ->
                                            new City(
                                                    rs.getLong("cityid"),
                                                    rs.getString("name"),
                                                    rs.getInt("population")))
                                    .stream().findFirst());
                        },
                        () -> {
                            throw new IllegalArgumentException("ID must not be null");
                        }
                );
        return cityToReturn.get();
    }

    /**
     *
     * @return all entities.
     */
    @Override
    public Iterable<City> findAll() {
        String sqlQuery = "SELECT * FROM city";

        return new HashSet<>(jdbcOperations.query(sqlQuery, (rs, i) ->
                new City(
                        rs.getLong("cityid"),
                        rs.getString("name"),
                        rs.getInt("population"))
        ));
    }

    /**
     * Saves the given entity.
     *
     * @param entity
     *            must not be null.
     * @return an {@code Optional} - null if the entity was saved otherwise (e.g. id already exists) returns the entity.
     * @throws IllegalArgumentException
     *             if the given entity is null.
     * @throws ValidatorException
     *             if the entity is not valid.
     */
    @Override
    public Optional<City> save(City entity) throws ValidatorException {

        validator.validate(entity);

        AtomicReference<Integer> rowsAffected = new AtomicReference<>();
        Optional<City> cityOptional = Optional.ofNullable(entity);
        AtomicReference<Optional<City>> returnedOptional = new AtomicReference<>();

        cityOptional.ifPresentOrElse(
                (val) -> {
                    String sqlCommand = "INSERT INTO city(cityid, name, population) VALUES(?, ?, ?)";
                    rowsAffected.set(jdbcOperations.update(sqlCommand,
                            entity.getId(),
                            entity.getName(),
                            entity.getPopulation()));

                },
                () -> {
                    throw new IllegalArgumentException("City entity must not be null!");
                }
        );

        if(rowsAffected.get().equals(1)){
            returnedOptional.set(Optional.empty());
        }
        else{
            returnedOptional.set(Optional.of(entity));
        }

        return returnedOptional.get();
    }


    /**
     * Removes the entity with the given id.
     *
     * @param id
     *            must not be null.
     * @return an {@code Optional} - null if there is no entity with the given id, otherwise the removed entity.
     * @throws IllegalArgumentException
     *             if the given id is null.
     */
    @Override
    public Optional<City> delete(Long aLong) {

        Optional<Long>  idOptional = Optional.ofNullable(aLong);

        idOptional.ifPresentOrElse(

                (val) -> {},
                () -> {
                    throw new IllegalArgumentException("Id must not be null!");
                }

        );

        Optional<City> cityToDelete = findOne(aLong);
        AtomicReference<Optional<City>> cityToReturn = new AtomicReference<>();

        String sqlCommand = "DELETE FROM city WHERE cityid = ?";

        cityToDelete.ifPresentOrElse(

                (val) -> {
                    Integer rowsAffected = jdbcOperations.update(sqlCommand, aLong);
                    if (rowsAffected.equals(1)){
                        cityToReturn.set(Optional.of(val));
                    }
                },
                () ->{
                    cityToReturn.set(Optional.empty());
                }
        );

        return cityToReturn.get();
    }

    /**
     * Updates the given entity.
     *
     * @param entity
     *            must not be null.
     * @return an {@code Optional} - null if the entity was updated otherwise (e.g. id does not exist) returns the
     *         entity.
     * @throws IllegalArgumentException
     *             if the given entity is null.
     * @throws ValidatorException
     *             if the entity is not valid.
     */
    @Override
    public Optional<City> update(City entity) throws ValidatorException {

        AtomicReference<Integer> rowsAffected = new AtomicReference<>();
        validator.validate(entity);
        Optional<City> cityOptional = Optional.ofNullable(entity);

        cityOptional.ifPresentOrElse(
                (val) -> {},
                () -> {
                    throw new IllegalArgumentException("Id must not be null!");
                }
        );


        Optional<City> cityToUpdate = findOne(entity.getId());
        AtomicReference<Optional<City>> cityToReturn = new AtomicReference<>();

        String sqlCommand = "UPDATE city SET name = ?, population = ? WHERE cityid = ?";

        cityToUpdate.ifPresentOrElse(
                (val) -> {
                    rowsAffected.set(jdbcOperations.update(sqlCommand,
                            entity.getName(),
                            entity.getPopulation(),
                            entity.getId()));
                },
                () -> {
                    throw new IllegalArgumentException("City entity must not be null!");
                }
        );

        if(rowsAffected.get().equals(1)){
            cityToReturn.set(Optional.empty());
        }
        else{
            cityToReturn.set(Optional.of(entity));
        }

        return cityToReturn.get();
    }
}


