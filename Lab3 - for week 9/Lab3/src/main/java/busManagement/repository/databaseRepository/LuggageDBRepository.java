package busManagement.repository.databaseRepository;

import busManagement.domain.Luggage;
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

public class LuggageDBRepository implements Repository<Long, Luggage> {

    private final Validator<Luggage> validator;
    private final String url;
    private final String username;
    private final String password;

    public LuggageDBRepository(Validator<Luggage> validator_, String url_, String username_, String password_){
        validator = validator_;
        url = url_;
        username = username_;
        password = password_;

        try{
            Connection connection = getSQLConnection();
            connection.close();
        }catch(SQLException exception){
            throw new BusManagementException("SQL Exception: " + exception);
        }
    }

    private Connection getSQLConnection() throws SQLException{
        return DriverManager.getConnection(url, username, password);
    }

    private Luggage retrieveCurrentResultAsLuggage(ResultSet results) throws SQLException{

        Long luggageId = results.getLong("luggageid");
        Long passengerId = results.getLong("passengerId");
        Integer weight = results.getInt("weight");

        Luggage luggage = new Luggage(passengerId, weight);
        luggage.setId(luggageId);

        return luggage;
    }

    @Override
    public Optional<Luggage> findOne(Long id){
        AtomicReference<Optional<Luggage>> luggageToReturn = new AtomicReference<>();

        Stream.ofNullable(id).findAny()
                .ifPresentOrElse(
                        (val) -> {
                            String sqlQuery = "SELECT * FROM luggage WHERE luggageid = " + val.toString();

                            try (Connection connection = getSQLConnection();
                                PreparedStatement statement = connection.prepareStatement(sqlQuery);
                                ResultSet resultSet = statement.executeQuery()){
                                if (resultSet.next()){
                                    Luggage luggage = retrieveCurrentResultAsLuggage(resultSet);

                                    luggageToReturn.set(Optional.of(luggage));
                                }
                            }catch (SQLException exception){
                                throw new BusManagementException("SQL Exception: " + exception.getMessage());
                            }
                        },
                        () -> {throw new IllegalArgumentException("ID must not be null!");}
                );
        return luggageToReturn.get();
    }

    @Override
    public Iterable<Luggage> findAll(){

        Set<Luggage> luggages = new HashSet<>();
        String sqlQuery = "SELECT * FROM luggage";

        try(Connection connection = getSQLConnection();
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()){
                Luggage luggage = retrieveCurrentResultAsLuggage(resultSet);

                luggages.add(luggage);
            }
        }catch (SQLException exception){
            throw new BusManagementException("SQL Exception: " + exception.getMessage());
        }
        return luggages;
    }

    @Override
    public Optional<Luggage> save(Luggage luggage) throws ValidatorException{
        validator.validate(luggage);

        String sqlQuery = "INSERT INTO luggage(luggageid, passengerid, weight) VALUES (?, ?, ?)";

        try(Connection connection = getSQLConnection();
            PreparedStatement statement = connection.prepareStatement(sqlQuery)){

            statement.setLong(1, luggage.getId());
            statement.setLong(2, luggage.getPassengerId());
            statement.setInt(3, luggage.getWeight());

            statement.executeUpdate();

            return Optional.of(luggage);
        }catch (SQLException exception){
            throw new BusManagementException("SQL Exception: " + exception.getMessage());
        }
    }

    @Override
    public Optional<Luggage> delete(Long id){
        Optional<Luggage> luggageToDelete = findOne(id);
        AtomicReference<Optional<Luggage>> returnedOptional = new AtomicReference<>();

        String sqlQuery = "DELETE FROM luggage WHERE luggageid = " + id.toString();

        luggageToDelete.ifPresentOrElse(

                (val) -> {

                    try(Connection connection = getSQLConnection();
                        PreparedStatement statement = connection.prepareStatement(sqlQuery)){

                        statement.executeUpdate();
                        returnedOptional.set(Optional.of(val));
                    } catch (SQLException exception){
                        throw new BusManagementException("SQL Exception: " + exception.getMessage());
                    }
                },

                () -> {
                    returnedOptional.set(Optional.empty());
                }
        );

        return returnedOptional.get();
    }

    @Override
    public Optional<Luggage> update(Luggage luggage) throws ValidatorException {

        validator.validate(luggage);

        Optional<Luggage> luggageToUpdate = findOne(luggage.getId());

        AtomicReference<Optional<Luggage>> returnedOptional = new AtomicReference<>();

        String sqlQuery = "UPDATE luggage SET weight = ? WHERE luggageid = ?";

        luggageToUpdate.ifPresentOrElse(

                (val) -> {

                    try(Connection connection = getSQLConnection();
                        PreparedStatement statement = connection.prepareStatement(sqlQuery)){

                        statement.setInt(1, luggage.getWeight());
                        statement.setLong(2, luggage.getId());

                        statement.executeUpdate();

                        returnedOptional.set(Optional.of(luggage));

                    } catch (SQLException exception){
                        throw new BusManagementException("SQL Exception: " + exception.getMessage());
                    }
                },

                () -> {
                    returnedOptional.set(Optional.empty());
                }
        );

        return returnedOptional.get();
    }
}
