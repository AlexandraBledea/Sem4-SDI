package busManagement.repository.databaseRepository;

import busManagement.domain.Driver;
import busManagement.domain.Passenger;
import busManagement.domain.Person;
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

public class DriverDBRepository implements Repository<Long, Driver> {

    private final Validator<Driver> validator;
    private final String url;
    private final String username;
    private final String password;

    public DriverDBRepository(Validator<Driver> validator, String url, String username, String password) {
        this.validator = validator;
        this.url = url;
        this.username = username;
        this.password = password;

        try {
            var connection = getSqlConnection();
            connection.close();
        }
        catch (SQLException exception) {
            throw new BusManagementException("SQL Exception: " + exception);
        }
    }

    private Connection getSqlConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    private Driver retrieveCurrent(ResultSet results) throws SQLException {

        Long pid = results.getLong("driverid");
        Integer monthsActive = results.getInt("monthsactive");

        AtomicReference<Optional<Person>> personToReturn = new AtomicReference<>();
        try (Connection connect = getSqlConnection();
             PreparedStatement statement = connect.prepareStatement("SELECT * FROM person WHERE personid = " + pid);
             ResultSet result = statement.executeQuery()) {

            if (result.next()) {
                String firstName = result.getString("firstname");
                String lastName = result.getString("lastname");
                Date dateOfBirth = result.getDate("dateofbirth");

                Person person = new Person(firstName, lastName, dateOfBirth.toLocalDate());

                personToReturn.set(Optional.of(person));
            }

        } catch (SQLException throwable) {
            throw new BusManagementException("SQL Exception: " + throwable.getMessage());
        }

        Driver driver = new Driver(personToReturn.get().get().getFirstName(),
                personToReturn.get().get().getLastName(),
                personToReturn.get().get().getDateOfBirth(),
                monthsActive);
        driver.setId(pid);

        return driver;
    }

    @Override
    public Optional<Driver> findOne(Long aLong) {
        AtomicReference<Optional<Driver>> passengerToReturn = new AtomicReference<>();

        Stream.ofNullable(aLong)
                .findAny()
                .ifPresentOrElse(
                        (val) -> {

                            String sqlQuery = "SELECT * FROM driver WHERE driverid = " + val.toString();

                            try (Connection connect = getSqlConnection();
                                 PreparedStatement statement = connect.prepareStatement(sqlQuery);
                                 ResultSet results = statement.executeQuery()) {

                                if (results.next()) {

                                    Driver driver = retrieveCurrent(results);

                                    passengerToReturn.set(Optional.of(driver));
                                }

                            } catch (SQLException throwable) {
                                throw new BusManagementException("SQL Exception: " + throwable.getMessage());
                            }

                        },
                        () -> {
                            throw new IllegalArgumentException("ID must not be null");
                        }
                );

        return passengerToReturn.get();
    }

    @Override
    public Iterable<Driver> findAll() {
        Set<Driver> entities = new HashSet<>();
        String sqlQuery = "SELECT * FROM driver";

        try (Connection connect = getSqlConnection();
             PreparedStatement statement = connect.prepareStatement(sqlQuery);
             ResultSet results = statement.executeQuery()) {

            while (results.next()) {
                Driver driver = retrieveCurrent(results);

                entities.add(driver);
            }

        }
        catch (SQLException throwable) {
            throw new BusManagementException("SQL Exception: " + throwable.getMessage());
        }

        return entities;
    }

    @Override
    public Optional<Driver> save(Driver entity) throws ValidatorException {
        validator.validate(entity);

        Optional<Driver> driverOptional = Optional.ofNullable(entity);
        AtomicReference<Optional<Driver>> returnedOptional = new AtomicReference<>();

        driverOptional.ifPresentOrElse(
                (o) -> {
                    String sqlCommandPerson = "INSERT INTO person(personid, firstname, lastname, dateofbirth) VALUES (?, ?, ?, ?)";
                    String sqlCommandDriver = "INSERT INTO driver(driverid, monthsactive) VALUES (?, ?)";

                    try (Connection connect = getSqlConnection();
                         PreparedStatement statement1 = connect.prepareStatement(sqlCommandPerson);
                         PreparedStatement statement2 = connect.prepareStatement(sqlCommandDriver)) {

                        statement1.setLong(1, entity.getId());
                        statement1.setString(2, entity.getFirstName());
                        statement1.setString(3, entity.getLastName());
                        statement1.setDate(4, Date.valueOf(entity.getDateOfBirth()));
                        statement2.setLong(1, entity.getId());
                        statement2.setInt(2, entity.getMonthsActive());

                        statement1.executeUpdate();
                        statement2.executeUpdate();

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
                    throw new IllegalArgumentException("Driver entity must not be null!");
                }
        );

        return returnedOptional.get();
    }

    @Override
    public Optional<Driver> delete(Long aLong) {
        Optional<Long> idOptional = Optional.ofNullable(aLong);

        idOptional.ifPresentOrElse(
                (o) -> {},
                () -> {
                    throw new IllegalArgumentException("Id must not be null!");
                }
        );

        Optional<Driver> driverToDelete = findOne(aLong);
        AtomicReference<Optional<Driver>> returnedOptional = new AtomicReference<>();

        String sqlCommandDriver = "DELETE FROM driver WHERE driverid = ?";
        String sqlCommandPerson = "DELETE FROM person WHERE personid = ?";

        driverToDelete.ifPresentOrElse(
                (o) -> {
                    try (Connection connection = getSqlConnection();
                         PreparedStatement statement1 = connection.prepareStatement(sqlCommandDriver);
                         PreparedStatement statement2 = connection.prepareStatement(sqlCommandPerson)) {

                        statement1.setLong(1, aLong);
                        statement2.setLong(1, aLong);

                        statement1.executeUpdate();
                        statement2.executeUpdate();
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
    public Optional<Driver> update(Driver entity) throws ValidatorException {
        validator.validate(entity);
        Optional<Driver> driverOptional = Optional.ofNullable(entity);

        driverOptional.ifPresentOrElse(
                (o) -> {},
                () -> {
                    throw new IllegalArgumentException("Id must not be null!");
                }
        );

        Optional<Driver> driverToUpdate = findOne(entity.getId());
        AtomicReference<Optional<Driver>> returnedOptional = new AtomicReference<>();

        String sqlCommand1 = "UPDATE person SET firstname = ?, lastname = ?, dateofbirth = ? WHERE personid = ?";
        String sqlCommand2 = "UPDATE driver SET monthsactive = ? WHERE driverid = ?";

        driverToUpdate.ifPresentOrElse(

                (o) -> {

                    try (Connection connect = getSqlConnection();
                         PreparedStatement statement1 = connect.prepareStatement(sqlCommand1);
                         PreparedStatement statement2 = connect.prepareStatement(sqlCommand2)) {

                        statement1.setString(1, entity.getFirstName());
                        statement1.setString(2, entity.getLastName());
                        statement1.setDate(3, Date.valueOf(entity.getDateOfBirth()));
                        statement1.setLong(4, entity.getId());
                        statement2.setInt(1, entity.getMonthsActive());
                        statement2.setLong(2, entity.getId());

                        statement1.executeUpdate();
                        statement2.executeUpdate();

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
