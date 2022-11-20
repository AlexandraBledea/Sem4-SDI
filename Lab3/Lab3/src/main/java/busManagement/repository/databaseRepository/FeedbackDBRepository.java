package busManagement.repository.databaseRepository;

import java.sql.*;

import busManagement.domain.*;
import busManagement.domain.exceptions.BusManagementException;
import busManagement.domain.exceptions.ValidatorException;
import busManagement.domain.validators.Validator;
import busManagement.repository.Repository;
import busManagement.utils.Pair;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

public class FeedbackDBRepository implements Repository<Pair<Long,Long>,Feedback> {

    private final Validator<Feedback> validator;
    private final String url;
    private final String username;
    private final String password;

    public FeedbackDBRepository(Validator<Feedback> validator_, String url_, String username_, String password_) {
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

    private Feedback retrieveCurrentResultAsFeedback(ResultSet result) throws SQLException {

        Long passengerId = result.getLong("PassengerId");
        Long companyId = result.getLong("CompanyId");
        String review = result.getString("Review");

        Feedback feedback = new Feedback(review);
        feedback.setId(new Pair<>(passengerId,companyId));

        return feedback;
    }



    @Override
    public Optional<Feedback> findOne(Pair<Long, Long> id) {
        AtomicReference<Optional<Feedback>> feedbackToReturn = new AtomicReference<>();

        Stream.ofNullable(id)
                .findAny()
                .ifPresentOrElse(
                        (val) -> {
                            String sqlQuery = "SELECT * FROM Feedback WHERE PassengerId = "
                                    + val.getFirst().toString()
                                    + "AND CompanyId = " +val.getSecond();
                            try (Connection connect = getSQLConnection();
                                 PreparedStatement statement = connect.prepareStatement(sqlQuery);
                                 ResultSet results = statement.executeQuery()) {

                                if (results.next()) {

                                    Feedback feedback = retrieveCurrentResultAsFeedback(results);

                                    feedbackToReturn.set(Optional.of(feedback));
                                }

                            } catch (SQLException throwable) {
                                throw new BusManagementException("SQL Exception: " + throwable.getMessage());
                            }
                        },
                        () -> {
                            throw new IllegalArgumentException("ID must not be null");
                        }
                );

        return feedbackToReturn.get();
    }


    @Override
    public Iterable<Feedback> findAll() {
        Set<Feedback> entities = new HashSet<>();
        String sqlQuery = "SELECT * FROM Feedback";

        try (Connection connect = getSQLConnection();
             PreparedStatement statement = connect.prepareStatement(sqlQuery);
             ResultSet results = statement.executeQuery()) {

            while (results.next()) {

                Feedback feedback = retrieveCurrentResultAsFeedback(results);

                entities.add(feedback);
            }

        }
        catch (SQLException throwable) {
            throw new BusManagementException("SQL Exception: " + throwable.getMessage());
        }

        return entities;

    }

    @Override
    public Optional<Feedback> save(Feedback entity) throws ValidatorException {
        validator.validate(entity);

        Optional<Feedback> feedbackOptional = Optional.ofNullable(entity);
        AtomicReference<Optional<Feedback>> returnedOptional = new AtomicReference<>();

        feedbackOptional.ifPresentOrElse(

                (o)-> {

                    String sqlCommand = "INSERT INTO Feedback(PassengerId, CompanyId , Review) VALUES (?, ?, ?)";

                    try (Connection connect = getSQLConnection();
                         PreparedStatement statement = connect.prepareStatement(sqlCommand)) {

                        statement.setLong(1, entity.getId().getFirst());
                        statement.setLong(2, entity.getId().getSecond());
                        statement.setString(3, entity.getReview());

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
    public Optional<Feedback> delete(Pair<Long, Long> id) {

        Optional<Pair<Long,Long>> idOptional = Optional.ofNullable(id);

        idOptional.ifPresentOrElse(

                (o) -> {},
                () -> {

                    throw new IllegalArgumentException("Id must not be null!");
                }
        );

        Optional<Feedback> feedbackToDelete = findOne(id);
        AtomicReference<Optional<Feedback>> returnedOptional = new AtomicReference<>();

        String sqlCommand = "DELETE FROM Feedback WHERE PassengerId = "
                + id.getFirst().toString()
                + "AND CompanyId = " +id.getSecond();

        feedbackToDelete.ifPresentOrElse(

                (o) ->{
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
    public Optional<Feedback> update(Feedback entity) throws ValidatorException {

        validator.validate(entity);

        Optional<Feedback> feedbackOptional = Optional.ofNullable(entity);

        feedbackOptional.ifPresentOrElse(
                (o) -> {},
                () -> {

                    throw new IllegalArgumentException("Id must not be null!");
                }
        );

        Optional<Feedback> feedbackToUpdate = findOne(entity.getId());
        AtomicReference<Optional<Feedback>> returnedOptional = new AtomicReference<>();

        String sqlCommand = "UPDATE Feedback SET Review = ? WHERE PassengerId = ?"
                + " AND CompanyId = ?";

        feedbackToUpdate.ifPresentOrElse(

                (o) ->{
                    try (Connection connect = getSQLConnection();
                         PreparedStatement statement = connect.prepareStatement(sqlCommand)) {

                        statement.setString(1,entity.getReview());
                        statement.setLong(2,entity.getId().getFirst());
                        statement.setLong(3,entity.getId().getFirst());

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

                    returnedOptional.set(Optional.empty());
                }
        );

        return returnedOptional.get();
    }


}




