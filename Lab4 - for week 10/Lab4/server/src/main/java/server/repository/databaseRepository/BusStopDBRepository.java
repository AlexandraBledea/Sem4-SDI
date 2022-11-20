package server.repository.databaseRepository;

import common.domain.BusStop;
import common.exceptions.ValidatorException;
import common.utils.Pair;
import common.validators.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import server.repository.IRepository;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

@Repository
public class BusStopDBRepository implements IRepository<Pair<Long, Long>, BusStop> {

    private final Validator<BusStop> validator;
    private final JdbcOperations jdbcOperations;


    public BusStopDBRepository(Validator<BusStop> validator, JdbcOperations jdbcOperations) {

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
    public Optional<BusStop> findOne(Pair<Long, Long> id) {
        AtomicReference<Optional<BusStop>> busStopToReturn = new AtomicReference<>();

        Stream.ofNullable(id)
                .findAny()
                .ifPresentOrElse(
                        (val) -> {
                            String sqlQuery = "SELECT * FROM BusStop WHERE BusId = "
                                    + id.getFirst().toString()
                                    + " AND StationId = " + id.getSecond().toString();

                            busStopToReturn.set(jdbcOperations.query(sqlQuery, (rs, i)->
                                    new BusStop(
                                            rs.getLong("busid"),
                                            rs.getLong("stationid"),
                                            LocalTime.parse(rs.getString("stoptime"))))
                                    .stream().findFirst());

                        },
                        () -> {
                            throw new IllegalArgumentException("ID must not be null");
                        }
                );
        return busStopToReturn.get();
    }

    /**
     *
     * @return all entities.
     */
    @Override
    public Iterable<BusStop> findAll() {
        String sqlQuery = "SELECT * FROM BusStop";

        return new HashSet<>(jdbcOperations.query(sqlQuery, (rs, i)->
                        new BusStop(
                                rs.getLong("busid"),
                                rs.getLong("stationid"),
                                LocalTime.parse(rs.getString("stoptime")))
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
    public Optional<BusStop> save(BusStop entity) throws ValidatorException {

        validator.validate(entity);

        AtomicReference<Integer> rowsAffected = new AtomicReference<>();
        Optional<BusStop> busStopOptional = Optional.ofNullable(entity);
        AtomicReference<Optional<BusStop>> returnedOptional = new AtomicReference<>();

        busStopOptional.ifPresentOrElse(
                (o) -> {
                    String sqlCommand = "INSERT INTO BusStop(BusId, StationId, StopTime) VALUES (?, ?, ?)";

                    rowsAffected.set(jdbcOperations.update(sqlCommand,
                            entity.getBusId(),
                            entity.getBusStationId(),
                            entity.getStopTime()));

                },
                () -> {
                    throw new IllegalArgumentException("BusStop entity must not be null!");

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
    public Optional<BusStop> delete(Pair<Long, Long> id) {


        Optional<Pair<Long, Long>> idOptional = Optional.ofNullable(id);

        idOptional.ifPresentOrElse(
                (o) -> {},
                () -> {
                    throw new IllegalArgumentException("Id must not be null!");
                }
        );
        Optional<BusStop> busStopToDelete = findOne(id);
        AtomicReference<Optional<BusStop>> returnedOptional = new AtomicReference<>();

        String sqlCommand = "DELETE FROM BusStop WHERE BusId = "
                + id.getFirst().toString()
                + " AND StationId = " + id.getSecond().toString();

        busStopToDelete.ifPresentOrElse(

                (o) -> {
                    Integer rowsAffected = jdbcOperations.update(sqlCommand);
                    if(rowsAffected.equals(1)){
                        returnedOptional.set(Optional.of(o));
                    }

                },

                () -> {

                    returnedOptional.set(Optional.empty());
                }
        );

        return returnedOptional.get();
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
    public Optional<BusStop> update(BusStop entity) throws ValidatorException {

        AtomicReference<Integer> rowsAffected = new AtomicReference<>();
        validator.validate(entity);
        Optional<BusStop> busStopOptional = Optional.ofNullable(entity);

        busStopOptional.ifPresentOrElse(

                (o) -> {},
                () -> {

                    throw new IllegalArgumentException("Id must not be null!");
                }
        );

        Optional<BusStop> busStopToUpdate = findOne(entity.getId());
        AtomicReference<Optional<BusStop>> returnedOptional = new AtomicReference<>();

        String sqlCommand = "UPDATE BusStop SET StopTime = ? WHERE BusId = "
                + entity.getId().getFirst().toString()
                + " AND StationId = " + entity.getId().getSecond().toString();

        busStopToUpdate.ifPresentOrElse(

                (o) -> {
                    rowsAffected.set(jdbcOperations.update(sqlCommand,
                            entity.getBusId(),
                            entity.getBusStationId(),
                            entity.getStopTime()));
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
}
