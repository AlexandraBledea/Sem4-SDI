package busManagement.repository.databaseRepository;

import java.sql.*;

import busManagement.domain.Bus;
import busManagement.domain.exceptions.BusManagementException;
import busManagement.domain.exceptions.ValidatorException;
import busManagement.domain.validators.Validator;
import busManagement.repository.Repository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

public class BusDBRepository implements Repository<Long, Bus> {

    private final Validator<Bus> validator;
    private final String url;
    private final String username;
    private final String password;


    public BusDBRepository(Validator<Bus> validator_, String url_, String username_, String password_) {

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

    private Bus retrieveCurrentResultAsBus(ResultSet results) throws SQLException {

        Long bid = results.getLong("busid");
        Long cid = results.getLong("companyid");
        Long did = results.getLong("driverid");
        String modelName = results.getString("modelname");
        Bus bus = new Bus(cid, did, modelName);
        bus.setId(bid);

        return bus;
    }

    @Override
    public Optional<Bus> findOne(Long aLong) {

        AtomicReference<Optional<Bus>> busToReturn = new AtomicReference<>();

        Stream.ofNullable(aLong)
                .findAny()
                .ifPresentOrElse(

                        (val) -> {

                            String sqlQuery = "SELECT * FROM bus WHERE busid = " + val.toString();

                            try (Connection connect = getSQLConnection();
                                 PreparedStatement statement = connect.prepareStatement(sqlQuery);
                                 ResultSet results = statement.executeQuery()) {

                                if (results.next()) {

                                    Bus bus = retrieveCurrentResultAsBus(results);

                                    busToReturn.set(Optional.of(bus));
                                }

                            } catch (SQLException throwable) {
                                throw new BusManagementException("SQL Exception: " + throwable.getMessage());
                            }

                        },
                        () -> {
                            throw new IllegalArgumentException("ID must not be null");
                        }
                );

        return busToReturn.get();
    }

    @Override
    public Iterable<Bus> findAll() {

        Set<Bus> entities = new HashSet<>();
        String sqlQuery = "SELECT * FROM bus";

        try (Connection connect = getSQLConnection();
             PreparedStatement statement = connect.prepareStatement(sqlQuery);
             ResultSet results = statement.executeQuery()) {

            while (results.next()) {

                Bus bus = retrieveCurrentResultAsBus(results);

                entities.add(bus);
            }

        }
        catch (SQLException throwable) {
            throw new BusManagementException("SQL Exception: " + throwable.getMessage());
        }

        return entities;
    }

    @Override
    public Optional<Bus> save(Bus entity) throws ValidatorException {

        validator.validate(entity);

        Optional<Bus> busOptional = Optional.ofNullable(entity);
        AtomicReference<Optional<Bus>> returnedOptional = new AtomicReference<>();

        busOptional.ifPresentOrElse(

                (o) -> {

                    String sqlCommand = "INSERT INTO bus(busid, companyid, driverid, modelname) VALUES (?, ?, ?, ?)";

                    try (Connection connect = getSQLConnection();
                         PreparedStatement statement = connect.prepareStatement(sqlCommand)) {

                        statement.setLong(1, entity.getId());
                        statement.setLong(2, entity.getCompanyId());
                        statement.setLong(3, entity.getDriverId());
                        statement.setString(4, entity.getModelName());

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

                    throw new IllegalArgumentException("Bus entity must not be null!");
                }
        );

        return returnedOptional.get();
    }

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

        String sqlCommand = "DELETE FROM bus WHERE busid = " + aLong.toString();

        busToDelete.ifPresentOrElse(

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
    public Optional<Bus> update(Bus entity) throws ValidatorException {

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

        String sqlCommand = "UPDATE bus SET modelname = ? WHERE busid = ?";

        busToUpdate.ifPresentOrElse(

                (o) -> {

                    try (Connection connect = getSQLConnection();
                         PreparedStatement statement = connect.prepareStatement(sqlCommand)) {

                        statement.setString(1, entity.getModelName());
                        statement.setLong(2, entity.getId());

                        statement.executeUpdate();

                        returnedOptional.set(Optional.empty());
                    }
                    catch (SQLIntegrityConstraintViolationException throwable) {

                        returnedOptional.set(Optional.of(entity));
                    }
                    catch (SQLException throwable) {

                        throw new BusManagementException("SQL Exception: " + throwable.getMessage());
                    }
                },
                () -> {

                    throw new IllegalArgumentException("Bus entity must not be null!");
                }
        );

        return returnedOptional.get();
    }
}
