package busManagement.repository.databaseRepository;

import busManagement.domain.Bus;
import busManagement.domain.BusStop;
import busManagement.domain.exceptions.BusManagementException;
import busManagement.domain.exceptions.ValidatorException;
import busManagement.domain.validators.Validator;
import busManagement.repository.Repository;
import busManagement.utils.Pair;

import javax.swing.text.html.Option;
import java.sql.*;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

public class BusStopDBRepository implements Repository<Pair<Long, Long>, BusStop> {

    private final Validator<BusStop> validator;
    private final String url;
    private final String username;
    private final String password;


    public BusStopDBRepository(Validator<BusStop> validator_, String url_, String username_, String password_) {

        validator = validator_;
        url = url_;
        username = username_;
        password = password_;

        try {

            Connection connect = getSQLConnection();
            connect.close();
        }
        catch (SQLException exception) {
            throw new BusManagementException("SQL Exception: " + exception);
        }
    }

    private Connection getSQLConnection() throws SQLException {

        return DriverManager.getConnection(url, username, password);
    }

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
                            try (Connection connect = getSQLConnection();
                                 PreparedStatement statement = connect.prepareStatement(sqlQuery);
                                 ResultSet results = statement.executeQuery()) {

                                if (results.next()) {

                                    BusStop busStop = retrieveCurrentResultAsBusStop(results);

                                    busStopToReturn.set(Optional.of(busStop));
                                }

                            } catch (SQLException throwable) {
                                throw new BusManagementException("SQL Exception: " + throwable.getMessage());
                            }
                        },
                        () -> {
                            throw new IllegalArgumentException("ID must not be null");
                        }
                );
        return busStopToReturn.get();
    }

    private BusStop retrieveCurrentResultAsBusStop(ResultSet results) throws SQLException {
        Long bid = results.getLong("BusId");
        Long sid = results.getLong("StationId");
        LocalTime stopTime = LocalTime.parse(results.getString("StopTime"));
        BusStop busStop = new BusStop(stopTime);
        busStop.setId(new Pair<>(bid, sid));

        return busStop;
    }

    @Override
    public Iterable<BusStop> findAll() {
        Set<BusStop> entities = new HashSet<>();
        String sqlQuery = "SELECT * FROM BusStop";
        try (Connection connect = getSQLConnection();
             PreparedStatement statement = connect.prepareStatement(sqlQuery);
             ResultSet results = statement.executeQuery()) {

            while (results.next()) {

                BusStop busStop = retrieveCurrentResultAsBusStop(results);

                entities.add(busStop);
            }

        }
        catch (SQLException throwable) {
            throw new BusManagementException("SQL Exception: " + throwable.getMessage());
        }

        return entities;
    }

    @Override
    public Optional<BusStop> save(BusStop entity) throws ValidatorException {
        validator.validate(entity);

        Optional<BusStop> busStopOptional = Optional.ofNullable(entity);
        AtomicReference<Optional<BusStop>> returnedOptional = new AtomicReference<>();

        busStopOptional.ifPresentOrElse(
                (o) -> {
                    String sqlCommand = "INSERT INTO BusStop(BusId, StationId, StopTime) VALUES (?, ?, ?)";

                    try (Connection connect = getSQLConnection();
                         PreparedStatement statement = connect.prepareStatement(sqlCommand)) {

                        statement.setLong(1, entity.getId().getFirst());
                        statement.setLong(2, entity.getId().getSecond());
                        statement.setTime(3, Time.valueOf(entity.getStopTime()));

                        statement.executeUpdate();

                        returnedOptional.set(Optional.empty());
                    }
                    catch (SQLIntegrityConstraintViolationException throwable) {

                        returnedOptional.set(Optional.ofNullable(entity));
                    }
                    catch (SQLException throwable) {
                        throw new BusManagementException("SQL Exception: " + throwable.getMessage());
                    }
                },
                () -> {
                    throw new IllegalArgumentException("BusStop entity must not be null!");

                }
        );
        return returnedOptional.get();

    }

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

                    try (Connection connect = getSQLConnection();
                         PreparedStatement statement = connect.prepareStatement(sqlCommand)) {

                        statement.executeUpdate();
                        returnedOptional.set(Optional.of(o));
                    }
                    catch (SQLException throwable) {

                        throw new BusManagementException("SQL Exception: " + throwable.getMessage());
                    }
                },

                () -> {

                    returnedOptional.set(Optional.empty());
                }
        );

        return returnedOptional.get();
    }

    @Override
    public Optional<BusStop> update(BusStop entity) throws ValidatorException {
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

                    try (Connection connect = getSQLConnection();
                         PreparedStatement statement = connect.prepareStatement(sqlCommand)) {

                        statement.setTime(1, Time.valueOf(entity.getStopTime()));
                        statement.setLong(2, entity.getId().getFirst());
                        statement.setLong(2, entity.getId().getSecond());

                        statement.executeUpdate();

                        returnedOptional.set(Optional.of(entity));
                    }
                    catch (SQLException throwable) {

                        throw new BusManagementException("SQL Exception: " + throwable.getMessage());
                    }
                },
                () -> {

                    returnedOptional.set(Optional.empty());
                }
        );

        return returnedOptional.get();
    }
}
