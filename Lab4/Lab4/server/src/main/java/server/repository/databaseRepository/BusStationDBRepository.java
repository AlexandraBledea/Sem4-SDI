package server.repository.databaseRepository;


import common.domain.BusStation;
import common.exceptions.ValidatorException;
import common.validators.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import server.repository.IRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

@Repository
public class BusStationDBRepository implements IRepository<Long, BusStation> {

    private final Validator<BusStation> validator;
    private final JdbcOperations jdbcOperations;

    public BusStationDBRepository(Validator<BusStation> validator, JdbcOperations jdbcOperations){
        this.validator = validator;
        this.jdbcOperations = jdbcOperations;

    }


    /**
     * Find the entity with the given {@code id}.
     *
     * @param aLong
     *            must be not null.
     * @return an {@code Optional} encapsulating the entity with the given id.
     * @throws IllegalArgumentException
     *             if the given id is null.
     */
    @Override
    public Optional<BusStation> findOne(Long aLong) {

        AtomicReference<Optional<BusStation>> stationToReturn = new AtomicReference<>();

        Stream.ofNullable(aLong)
                .findAny()
                .ifPresentOrElse(

                        (val) -> {
                            String sqlQuery = "SELECT * FROM busstation where stationid = " + val.toString();

                            stationToReturn.set(jdbcOperations.query(sqlQuery, (rs, i)->
                                    new BusStation(
                                            rs.getLong("stationid"),
                                            rs.getLong("cityid"),
                                            rs.getString("name")))
                                    .stream().findFirst());

                        },

                        () -> {
                            throw new IllegalArgumentException("ID must not be null");
                        }
                );

        return stationToReturn.get();
    }

    /**
     *
     * @return all entities.
     */
    @Override
    public Iterable<BusStation> findAll() {
        String sqlQuery = "SELECT * FROM busstation";

        return new HashSet<>(jdbcOperations.query(sqlQuery, (rs, i)->
                new BusStation(
                        rs.getLong("stationid"),
                        rs.getLong("cityid"),
                        rs.getString("name"))
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
    public Optional<BusStation> save(BusStation entity) throws ValidatorException {
        validator.validate(entity);

        AtomicReference<Integer> rowsAffected = new AtomicReference<>();
        Optional<BusStation> busStationOptional = Optional.ofNullable(entity);
        AtomicReference<Optional<BusStation>> returnedOptional = new AtomicReference<>();

        busStationOptional.ifPresentOrElse(

                (val) -> {

                    String sqlCommand = "INSERT INTO busstation(stationid, cityid, name) VALUES (?, ?, ?)";
                    rowsAffected.set(jdbcOperations.update(sqlCommand,
                            entity.getId(),
                            entity.getCityId(),
                            entity.getName()));

                },

                () -> {
                    throw new IllegalArgumentException("BusStation entity must not be null!");
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
     * @param aLong
     *            must not be null.
     * @return an {@code Optional} - null if there is no entity with the given id, otherwise the removed entity.
     * @throws IllegalArgumentException
     *             if the given id is null.
     */
    @Override
    public Optional<BusStation> delete(Long aLong) {

        Optional<Long> idOptional = Optional.ofNullable(aLong);

        idOptional.ifPresentOrElse(

                (val) -> {},
                () -> {
                    throw new IllegalArgumentException("Id must not be null!");
                }

        );

        Optional<BusStation> stationToDelete = findOne(aLong);
        AtomicReference<Optional<BusStation>> stationToReturn = new AtomicReference<>();

        String sqlCommand = "DELETE FROM busstation WHERE stationid = ?";

        stationToDelete.ifPresentOrElse(

                (o) -> {
                    Integer rowsAffected = jdbcOperations.update(sqlCommand, aLong);
                    if(rowsAffected.equals(1)){
                        stationToReturn.set(Optional.of(o));
                    }
                },

                () -> {

                    stationToReturn.set(Optional.empty());
                }
        );

        return stationToReturn.get();
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
    public Optional<BusStation> update(BusStation entity) throws ValidatorException {

        AtomicReference<Integer> rowsAffected = new AtomicReference<>();
        validator.validate(entity);
        Optional<BusStation> busStationOptional = Optional.ofNullable(entity);

        busStationOptional.ifPresentOrElse(

                (val) -> {},
                () -> {
                    throw new IllegalArgumentException("Id must not be null!");
                }

        );


        Optional<BusStation> stationToUpdate = findOne(entity.getId());
        AtomicReference<Optional<BusStation>> stationToReturn = new AtomicReference<>();

        String sqlCommand = "UPDATE busstation SET name = ? WHERE stationid = ?";

        stationToUpdate.ifPresentOrElse(

                (o) -> {
                    rowsAffected.set(jdbcOperations.update(sqlCommand,
                            entity.getName(),
                            entity.getId()));
                },
                () -> {

                    throw new IllegalArgumentException("BusStation entity must not be null!");
                }
        );

        if(rowsAffected.get().equals(1)){
            stationToReturn.set(Optional.empty());
        }
        else{
            stationToReturn.set(Optional.of(entity));
        }

        return stationToReturn.get();
    }
}
