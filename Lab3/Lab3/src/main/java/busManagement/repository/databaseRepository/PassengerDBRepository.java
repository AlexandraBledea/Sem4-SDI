package busManagement.repository.databaseRepository;

import java.sql.*;

import busManagement.domain.Bus;
import busManagement.domain.Passenger;
import busManagement.domain.Person;
import busManagement.domain.exceptions.BusManagementException;
import busManagement.domain.exceptions.ValidatorException;
import busManagement.domain.validators.Validator;
import busManagement.repository.Repository;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

public class PassengerDBRepository implements Repository<Long, Passenger> {

    private final Validator<Passenger> validator;
    private final String url;
    private final String username;
    private final String password;


    public PassengerDBRepository(Validator<Passenger> validator_, String url_, String username_, String password_) {

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

    private Passenger retrieveCurrentResultAsPassenger(ResultSet results) throws SQLException {

        Long pid = results.getLong("passengerId");
        Integer timesTravelled = results.getInt("timesTravelled");

        AtomicReference<Optional<Person>> personToReturn = new AtomicReference<>();
        try (Connection connect = getSQLConnection();
             PreparedStatement statement = connect.prepareStatement("SELECT * FROM person WHERE personId = " + pid);
             ResultSet result = statement.executeQuery()) {

            if (result.next()) {

                String firstName = result.getString("firstName");
                String lastName = result.getString("lastName");
                Date dateOfBirth = result.getDate("dateOfBirth");

                Person person = new Person(firstName, lastName, dateOfBirth.toLocalDate());

                personToReturn.set(Optional.of(person));
            }

        } catch (SQLException throwable) {
            throw new BusManagementException("SQL Exception: " + throwable.getMessage());
        }

        Passenger passenger = new Passenger(personToReturn.get().get().getFirstName(),
                                            personToReturn.get().get().getLastName(),
                                            personToReturn.get().get().getDateOfBirth(),
                                            timesTravelled);
        passenger.setId(pid);

        return passenger;
    }

    @Override
    public Optional<Passenger> findOne(Long aLong) {

        AtomicReference<Optional<Passenger>> passengerToReturn = new AtomicReference<>();

        Stream.ofNullable(aLong)
                .findAny()
                .ifPresentOrElse(

                        (val) -> {

                            String sqlQuery = "SELECT * FROM passenger WHERE passengerid = " + val.toString();

                            try (Connection connect = getSQLConnection();
                                 PreparedStatement statement = connect.prepareStatement(sqlQuery);
                                 ResultSet results = statement.executeQuery()) {

                                if (results.next()) {

                                    Passenger passenger = retrieveCurrentResultAsPassenger(results);

                                    passengerToReturn.set(Optional.of(passenger));
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
    public Iterable<Passenger> findAll() {

        Set<Passenger> entities = new HashSet<>();
        String sqlQuery = "SELECT * FROM passenger";

        try (Connection connect = getSQLConnection();
             PreparedStatement statement = connect.prepareStatement(sqlQuery);
             ResultSet results = statement.executeQuery()) {

            while (results.next()) {

                Passenger passenger = retrieveCurrentResultAsPassenger(results);

                entities.add(passenger);
            }

        }
        catch (SQLException throwable) {
            throw new BusManagementException("SQL Exception: " + throwable.getMessage());
        }

        return entities;
    }

    @Override
    public Optional<Passenger> save(Passenger entity) throws ValidatorException {

        validator.validate(entity);

        Optional<Passenger> passengerOptional = Optional.ofNullable(entity);
        AtomicReference<Optional<Passenger>> returnedOptional = new AtomicReference<>();

        passengerOptional.ifPresentOrElse(
                (o) -> {

                    String sqlCommandPerson = "INSERT INTO person(personId, firstName, lastName, dateOfBirth) VALUES (?, ?, ?, ?)";
                    String sqlCommandPassenger = "INSERT INTO passenger(passengerId, timesTravelled) VALUES (?, ?)";

                    try (Connection connect = getSQLConnection();
                         PreparedStatement statement1 = connect.prepareStatement(sqlCommandPerson);
                         PreparedStatement statement2 = connect.prepareStatement(sqlCommandPassenger)) {

                        statement1.setLong(1, entity.getId());
                        statement1.setString(2, entity.getFirstName());
                        statement1.setString(3, entity.getLastName());
                        statement1.setDate(4, Date.valueOf(entity.getDateOfBirth()));
                        statement2.setLong(1, entity.getId());
                        statement2.setInt(2, entity.getTimesTravelled());

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

                    throw new IllegalArgumentException("Bus entity must not be null!");
                }
        );

        return returnedOptional.get();
    }

    @Override
    public Optional<Passenger> delete(Long aLong) {

        Optional<Long> idOptional = Optional.ofNullable(aLong);

        idOptional.ifPresentOrElse(

                (o) -> {},
                () -> {

                    throw new IllegalArgumentException("Id must not be null!");
                }
        );

        Optional<Passenger> passengerToDelete = findOne(aLong);
        AtomicReference<Optional<Passenger>> returnedOptional = new AtomicReference<>();

        String sqlCommand1 = "DELETE FROM passenger WHERE passengerid = " + aLong;
        String sqlCommand2 = "DELETE FROM person WHERE personId = " + aLong;

        passengerToDelete.ifPresentOrElse(

                (o) -> {

                    try (Connection connect = getSQLConnection();
                         PreparedStatement statement1 = connect.prepareStatement(sqlCommand1);
                         PreparedStatement statement2 = connect.prepareStatement(sqlCommand2)) {

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
    public Optional<Passenger> update(Passenger entity) throws ValidatorException {

        validator.validate(entity);
        Optional<Passenger> passengerOptional = Optional.ofNullable(entity);

        passengerOptional.ifPresentOrElse(

                (o) -> {},
                () -> {

                    throw new IllegalArgumentException("Id must not be null!");
                }
        );

        Optional<Passenger> passengerToUpdate = findOne(entity.getId());
        AtomicReference<Optional<Passenger>> returnedOptional = new AtomicReference<>();

        String sqlCommand1 = "UPDATE person SET firstName = ?, lastName = ?, dateOfBirth = ? WHERE personId = ?";
        String sqlCommand2 = "UPDATE passenger SET timesTravelled = ? WHERE passengerid = ?";

        passengerToUpdate.ifPresentOrElse(

                (o) -> {

                    try (Connection connect = getSQLConnection();
                         PreparedStatement statement1 = connect.prepareStatement(sqlCommand1);
                         PreparedStatement statement2 = connect.prepareStatement(sqlCommand2)) {

                        statement1.setString(1, entity.getFirstName());
                        statement1.setString(2, entity.getLastName());
                        statement1.setDate(3, Date.valueOf(entity.getDateOfBirth()));
                        statement1.setLong(4, entity.getId());
                        statement2.setInt(1, entity.getTimesTravelled());
                        statement2.setLong(2, entity.getId());

                        statement1.executeUpdate();
                        statement2.executeUpdate();

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

                    returnedOptional.set(Optional.empty());
                }
        );

        return returnedOptional.get();
    }
}
