package busManagement.repository.databaseRepository;

import busManagement.domain.Bus;
import busManagement.domain.BusCompany;
import busManagement.domain.exceptions.BusManagementException;
import busManagement.domain.exceptions.ValidatorException;
import busManagement.domain.validators.Validator;
import busManagement.repository.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;
import java.sql.*;

public class BusCompanyDBRepository implements Repository<Long, BusCompany> {

    private final Validator<BusCompany> validator;
    private final String url;
    private final String username;
    private final String password;

    public BusCompanyDBRepository(Validator<BusCompany> _validator, String _url, String _username, String _password) {
        validator = _validator;
        url = _url;
        username = _username;
        password = _password;

        try {
            Connection connection = getSQLConnection();
            connection.close();
        }
        catch (SQLException exception) {
            throw new BusManagementException("SQL Exception: " + exception);
        }
    }

    private Connection getSQLConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    private BusCompany retrieveCurrentResultAsBusCompany(ResultSet results) throws SQLException {
        Long companyid = results.getLong("companyid");
        String name = results.getString("name");
        BusCompany busCompany = new BusCompany(name);
        busCompany.setId(companyid);

        return busCompany;

    }

    @Override
    public Optional<BusCompany> findOne(Long aLong) {
        AtomicReference<Optional<BusCompany>> busCompanyToReturn = new AtomicReference<>();

        Stream.ofNullable(aLong)
                .findAny()
                .ifPresentOrElse(
                        (val) -> {
                            String sqlQuery = "SELECT * FROM buscompany WHERE companyid = " + val.toString();

                            try (Connection connection = getSQLConnection();
                                 PreparedStatement statement = connection.prepareStatement(sqlQuery);
                                 ResultSet results = statement.executeQuery()) {

                                if (results.next()) {

                                    BusCompany bus = retrieveCurrentResultAsBusCompany(results);

                                    busCompanyToReturn.set(Optional.of(bus));
                                }

                            } catch (SQLException throwable) {
                                throw new BusManagementException("SQL Exception: " + throwable.getMessage());
                            }
                        },
                        () -> {
                            throw new IllegalArgumentException("ID must not be null.");
                        }
                );

        return busCompanyToReturn.get();
    }

    @Override
    public Iterable<BusCompany> findAll() {
        Set<BusCompany> entities = new HashSet<>();
        String sqlQuery = "SELECT * FROM buscompany";

        try (Connection connection = getSQLConnection();
             PreparedStatement statement = connection.prepareStatement(sqlQuery);
             ResultSet results = statement.executeQuery()) {

            while (results.next()) {

                BusCompany busCompany = retrieveCurrentResultAsBusCompany(results);

                entities.add(busCompany);
            }

        }
        catch (SQLException throwable) {
            throw new BusManagementException("SQL Exception: " + throwable.getMessage());
        }

        return entities;

    }

    @Override
    public Optional<BusCompany> save(BusCompany entity) throws ValidatorException {
        validator.validate(entity);

        Optional<BusCompany> busCompanyOptional = Optional.ofNullable(entity);
        AtomicReference<Optional<BusCompany>> returnedOptional = new AtomicReference<>();

        busCompanyOptional.ifPresentOrElse(
                (o) -> {
                    String sqlCommand = "INSERT INTO buscompany(companyid, name) VALUES (?, ?)";

                    try (Connection connection = getSQLConnection();
                         PreparedStatement statement = connection.prepareStatement(sqlCommand)) {

                        statement.setLong(1, entity.getId());
                        statement.setString(2, entity.getName());

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
                    throw new IllegalArgumentException("BusCompany entity must not be null!");
                }
        );

        return returnedOptional.get();
    }

    @Override
    public Optional<BusCompany> delete(Long aLong) {
        Optional<Long> idOptional = Optional.ofNullable(aLong);

        idOptional.ifPresentOrElse(

                (o) -> {},
                () -> {

                    throw new IllegalArgumentException("Id must not be null!");
                }
        );

        Optional<BusCompany> busCompanyToDelete = findOne(aLong);
        AtomicReference<Optional<BusCompany>> returnedOptional = new AtomicReference<>();

        String sqlCommand = "DELETE FROM buscompany WHERE companyid = " + aLong.toString();

        busCompanyToDelete.ifPresentOrElse(

                (o) -> {

                    try (Connection connection = getSQLConnection();
                         PreparedStatement statement = connection.prepareStatement(sqlCommand)) {

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
    public Optional<BusCompany> update(BusCompany entity) throws ValidatorException {
        validator.validate(entity);

        Optional<BusCompany> busCompanyOptional = Optional.ofNullable(entity);

        busCompanyOptional.ifPresentOrElse(

                (o) -> {},
                () -> {

                    throw new IllegalArgumentException("Id must not be null!");
                }
        );

        Optional<BusCompany> busCompanyToUpdate = findOne(entity.getId());
        AtomicReference<Optional<BusCompany>> returnedOptional = new AtomicReference<>();

        String sqlCommand = "UPDATE buscompany SET name = ? WHERE companyid = ?";

        busCompanyToUpdate.ifPresentOrElse(

                (o) -> {

                    try (Connection connection = getSQLConnection();
                         PreparedStatement statement = connection.prepareStatement(sqlCommand)) {

                        statement.setString(1, entity.getName());
                        statement.setLong(2, entity.getId());

                        statement.executeUpdate();

                        returnedOptional.set(Optional.of(entity));
                    }
                    catch (SQLIntegrityConstraintViolationException throwable) {

                        returnedOptional.set(Optional.of(entity));
                    }
                    catch (SQLException throwable) {

                        throw new BusManagementException("SQL Exception: " + throwable.getMessage());
                    }
                },
                () -> {

                    throw new IllegalArgumentException("BusCompany entity must not be null!");
                }
        );

        return returnedOptional.get();
    }
}
