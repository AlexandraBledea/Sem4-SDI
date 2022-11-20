package busManagement.repository.databaseRepository;

import busManagement.domain.Bus;
import busManagement.domain.BusStation;
import busManagement.domain.City;
import busManagement.domain.exceptions.BusManagementException;
import busManagement.domain.exceptions.ValidatorException;
import busManagement.domain.validators.Validator;
import busManagement.repository.Repository;

import java.sql.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

public class BusStationDBRepository implements Repository<Long, BusStation> {

    private final Validator<BusStation> validator;
    private final String url;
    private final String username;
    private final String password;

    public BusStationDBRepository(Validator<BusStation> validator, String url, String username, String password){
        this.validator = validator;
        this.url = url;
        this.username = username;
        this.password = password;

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

    private BusStation retrieveCurrentResultAsBus(ResultSet results) throws SQLException {

        Long sid = results.getLong("stationid");
        Long cid = results.getLong("cityid");
        String name = results.getString("name");

        BusStation station = new BusStation(cid, name);
        station.setId(sid);

        return station;
    }

    @Override
    public Optional<BusStation> findOne(Long aLong) {

        AtomicReference<Optional<BusStation>> stationToReturn = new AtomicReference<>();

        Stream.ofNullable(aLong)
                .findAny()
                .ifPresentOrElse(

                        (val) -> {
                            String sqlQuery = "SELECT * FROM busstation where stationid = " + val.toString();

                            try (Connection connect = getSQLConnection();
                                 PreparedStatement statement = connect.prepareStatement(sqlQuery);
                                 ResultSet results = statement.executeQuery()) {

                                if (results.next()) {

                                    BusStation station = retrieveCurrentResultAsBus(results);

                                    stationToReturn.set(Optional.of(station));
                                }

                            } catch (SQLException throwable) {
                                throw new BusManagementException("SQL Exception: " + throwable.getMessage());
                            }

                        },

                        () -> {
                            throw new IllegalArgumentException("ID must not be null");
                        }
                );

        return stationToReturn.get();
    }

    @Override
    public Iterable<BusStation> findAll() {
        Set<BusStation> entities = new HashSet<>();
        String sqlQuery = "SELECT * FROM busstation";

        try (Connection connect = getSQLConnection();
             PreparedStatement statement = connect.prepareStatement(sqlQuery);
             ResultSet results = statement.executeQuery()) {

            while (results.next()) {

                BusStation station = retrieveCurrentResultAsBus(results);

                entities.add(station);
            }

        }
        catch (SQLException throwable) {
            throw new BusManagementException("SQL Exception: " + throwable.getMessage());
        }

        return entities;
    }

    @Override
    public Optional<BusStation> save(BusStation entity) throws ValidatorException {
        validator.validate(entity);

        Optional<BusStation> busStationOptional = Optional.ofNullable(entity);
        AtomicReference<Optional<BusStation>> returnedOptional = new AtomicReference<>();

        busStationOptional.ifPresentOrElse(

                (val) -> {

                    String sqlCommand = "INSERT INTO busstation(stationid, cityid, name) VALUES (?, ?, ?)";

                    try (Connection connect = getSQLConnection();
                         PreparedStatement statement = connect.prepareStatement(sqlCommand)) {

                        statement.setLong(1, entity.getId());
                        statement.setLong(2, entity.getCityId());
                        statement.setString(3, entity.getName());

                        statement.executeUpdate();

                        returnedOptional.set(Optional.empty());
                    }
                    catch(SQLIntegrityConstraintViolationException throwable){
                        returnedOptional.set(Optional.ofNullable(entity));
                    }
                    catch (SQLException throwable) {
                        throw new BusManagementException("SQL Exception: " + throwable.getMessage());
                    }
                },

                () -> {
                    throw new IllegalArgumentException("BusStation entity must not be null!");
                }

        );

        return returnedOptional.get();
    }

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

        String sqlCommand = "DELETE FROM busstation WHERE stationid = " + aLong.toString();

        stationToDelete.ifPresentOrElse(

                (o) -> {

                    try (Connection connect = getSQLConnection();
                         PreparedStatement statement = connect.prepareStatement(sqlCommand)) {

                        statement.executeUpdate();
                        stationToReturn.set(Optional.of(o));
                    }
                    catch (SQLException throwable) {

                        throw new BusManagementException("SQL Exception: " + throwable.getMessage());
                    }
                },

                () -> {

                    stationToReturn.set(Optional.empty());
                }
        );

        return stationToReturn.get();
    }

    @Override
    public Optional<BusStation> update(BusStation entity) throws ValidatorException {
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

                    try (Connection connect = getSQLConnection();
                         PreparedStatement statement = connect.prepareStatement(sqlCommand)) {

                        statement.setString(1, entity.getName());
                        statement.setLong(2, entity.getId());

                        statement.executeUpdate();

                        stationToReturn.set(Optional.empty());
                    }
                    catch(SQLIntegrityConstraintViolationException throwable){
                        stationToReturn.set(Optional.of(entity));
                    }
                    catch (SQLException throwable) {

                        throw new BusManagementException("SQL Exception: " + throwable.getMessage());
                    }
                },
                () -> {

                    throw new IllegalArgumentException("BusStation entity must not be null!");
                }
        );

        return stationToReturn.get();
    }
}
