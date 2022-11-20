package server.repository.databaseRepository;

import common.domain.Bus;
import common.exceptions.ValidatorException;
import common.validators.Validator;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import server.repository.IRepository;


import java.util.HashSet;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

@Repository
public class BusDBRepository implements IRepository<Long, Bus> {

    private final Validator<Bus> validator;
    private final JdbcOperations jdbcOperations;

    public BusDBRepository(Validator<Bus> validator, JdbcOperations jdbcOperations) {

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
    public Optional<Bus> findOne(Long aLong) {

        AtomicReference<Optional<Bus>> busToReturn = new AtomicReference<>();

        Stream.ofNullable(aLong)
                .findAny()
                .ifPresentOrElse(

                        (val) -> {

                            String sqlQuery = "SELECT * FROM bus WHERE busid = " + val.toString();

                            busToReturn.set(jdbcOperations.query(sqlQuery, (rs, i)->
                                    new Bus(
                                            rs.getLong("busid"),
                                            rs.getString("modelname"),
                                            rs.getString("fuel"),
                                            rs.getInt("capacity")))
                                    .stream().findFirst());

                        },
                        () -> {
                            throw new IllegalArgumentException("ID must not be null");
                        }
                );

        return busToReturn.get();
    }


    /**
     *
     * @return all entities.
     */
    @Override
    public Iterable<Bus> findAll() {

        String sqlQuery = "SELECT * FROM bus";

        return new HashSet<>(jdbcOperations.query(sqlQuery, (rs, i)->
                        new Bus(
                                rs.getLong("busid"),
                                rs.getString("modelname"),
                                rs.getString("fuel"),
                                rs.getInt("capacity"))
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
    public Optional<Bus> save(Bus entity) throws ValidatorException {

        validator.validate(entity);

        AtomicReference<Integer> rowsAffected = new AtomicReference<>();
        Optional<Bus> busOptional = Optional.ofNullable(entity);
        AtomicReference<Optional<Bus>> returnedOptional = new AtomicReference<>();

        busOptional.ifPresentOrElse(

                (o) -> {

                    String sqlCommand = "INSERT INTO bus(busid, modelname, fuel, capacity) VALUES (?, ?, ?, ?)";
                    rowsAffected.set(jdbcOperations.update(sqlCommand,
                            entity.getId(),
                            entity.getModelName(),
                            entity.getFuel(),
                            entity.getCapacity()));

                },
                () -> {

                    throw new IllegalArgumentException("Bus entity must not be null!");
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
    public Optional<Bus> delete(Long aLong) {

        Optional<Long> idOptional = Optional.ofNullable(aLong);

        idOptional.ifPresentOrElse(

                (o) -> {},
                () -> {

                    throw new IllegalArgumentException("Id must not be null!");
                }
        );

        Optional<Bus> busToDelete = findOne(aLong);
        AtomicReference<Optional<Bus>> returnedOptional = new AtomicReference<>();

        String sqlCommand = "DELETE FROM bus WHERE busid = ?";

        busToDelete.ifPresentOrElse(

                (o) -> {

                        Integer rowsAffected = jdbcOperations.update(sqlCommand, aLong);
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
    public Optional<Bus> update(Bus entity) throws ValidatorException {

        AtomicReference<Integer> rowsAffected = new AtomicReference<>();
        validator.validate(entity);
        Optional<Bus> busOptional = Optional.ofNullable(entity);

        busOptional.ifPresentOrElse(

                (o) -> {},
                () -> {

                    throw new IllegalArgumentException("Id must not be null!");
                }
        );

        Optional<Bus> busToUpdate = findOne(entity.getId());
        AtomicReference<Optional<Bus>> returnedOptional = new AtomicReference<>();

        String sqlCommand = "UPDATE bus SET modelname = ?, fuel = ?, capacity = ? WHERE busid = ?";

        busToUpdate.ifPresentOrElse(

                (o) -> {
                        rowsAffected.set(jdbcOperations.update(sqlCommand,
                                entity.getModelName(),
                                entity.getFuel(),
                                entity.getCapacity(),
                                entity.getId()));

                },
                () -> {

                    throw new IllegalArgumentException("Bus entity must not be null!");
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
