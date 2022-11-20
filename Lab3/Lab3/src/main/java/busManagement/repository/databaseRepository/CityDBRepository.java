package busManagement.repository.databaseRepository;

import busManagement.domain.Bus;
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

public class CityDBRepository implements Repository<Long, City> {

    private final Validator<City> validator;
    private final String url;
    private final String username;
    private final String password;

    public CityDBRepository(Validator<City> validator, String url, String username, String password) {
        this.validator = validator;
        this.url = url;
        this.username = username;
        this.password = password;

        try {
            Connection connect = getSqlConnection();
            connect.close();
        }
        catch (SQLException exception){
            throw new BusManagementException("SQL Exception: " + exception);
        }

    }

    private Connection getSqlConnection() throws SQLException {

        return DriverManager.getConnection(url, username, password);
    }

    private City retrieveCurrentResultAsCity(ResultSet results) throws SQLException{
        Long id = results.getLong("cityid");
        String name = results.getString("name");
        int population = results.getInt("population");
        City city = new City(name, population);
        city.setId(id);

        return city;
    }

    @Override
    public Optional<City> findOne(Long aLong) {
        AtomicReference<Optional<City>> cityToReturn = new AtomicReference<>();

        Stream.ofNullable(aLong)
                .findAny()
                .ifPresentOrElse(
                        (val) -> {
                            String sqlQuery = "SELECT * FROM city WHERE cityid = " + val.toString();

                            try(Connection connect = getSqlConnection();
                                PreparedStatement statement = connect.prepareStatement(sqlQuery);
                                ResultSet results = statement.executeQuery())
                            {

                                if(results.next()){
                                    City city = retrieveCurrentResultAsCity(results);
                                    cityToReturn.set(Optional.of(city));
                                }
                            }
                            catch (SQLException throwable)
                            {
                                throw new BusManagementException("SQL Exception: " + throwable.getMessage());
                            }
                        },
                        () -> {
                            throw new IllegalArgumentException("ID must not be null");
                        }
                );
        return cityToReturn.get();
    }

    @Override
    public Iterable<City> findAll() {
        Set<City> entities = new HashSet<>();
        String sqlQuery = "SELECT * FROM city";

        try(Connection connect = getSqlConnection();
            PreparedStatement statement = connect.prepareStatement(sqlQuery);
            ResultSet results = statement.executeQuery())
        {
            while(results.next()){

                City city = retrieveCurrentResultAsCity(results);

                entities.add(city);
            }
        }
        catch(SQLException throwable){
            throw new BusManagementException("SQL Exception: " + throwable.getMessage());
        }

        return entities;
    }

    @Override
    public Optional<City> save(City entity) throws ValidatorException {

        validator.validate(entity);

        Optional<City> cityOptional = Optional.ofNullable(entity);
        AtomicReference<Optional<City>> returnedOptional = new AtomicReference<>();

        cityOptional.ifPresentOrElse(
                (val) -> {

                    String sqlCommand = "INSERT INTO city(cityid, name, population) VALUES(?, ?, ?)";

                    try(Connection connect = getSqlConnection();
                        PreparedStatement statement = connect.prepareStatement(sqlCommand))
                    {
                        statement.setLong(1, entity.getId());
                        statement.setString(2, entity.getName());
                        statement.setInt(3, entity.getPopulation());

                        statement.executeUpdate();

                        returnedOptional.set(Optional.empty());
                    }
                    catch(SQLIntegrityConstraintViolationException throwable){
                        returnedOptional.set(Optional.ofNullable(entity));
                    }
                    catch(SQLException throwable){
                        throw new BusManagementException("SQL Exception: " + throwable.getMessage());
                    }
                },
                () -> {
                    throw new IllegalArgumentException("City entity must not be null!");
                }
        );

        return returnedOptional.get();
    }

    @Override
    public Optional<City> delete(Long aLong) {

        Optional<Long>  idOptional = Optional.ofNullable(aLong);

        idOptional.ifPresentOrElse(

                (val) -> {},
                () -> {
                    throw new IllegalArgumentException("Id must not be null!");
                }

        );

        Optional<City> cityToDelete = findOne(aLong);
        AtomicReference<Optional<City>> cityToReturn = new AtomicReference<>();

        String sqlCommand = "DELETE FROM city WHERE cityid = " + aLong.toString();

        cityToDelete.ifPresentOrElse(

                (val) -> {
                    try(Connection connect = getSqlConnection();
                        PreparedStatement statement = connect.prepareStatement(sqlCommand))
                    {
                        statement.executeUpdate();
                        cityToReturn.set(Optional.of(val));
                    }
                    catch(SQLException throwable){
                        throw new BusManagementException("SQL Exception: " + throwable.getMessage());
                    }
                },
                () ->{
                    cityToReturn.set(Optional.empty());
                }
        );

        return cityToReturn.get();
    }

    @Override
    public Optional<City> update(City entity) throws ValidatorException {

        validator.validate(entity);
        Optional<City> cityOptional = Optional.ofNullable(entity);

        cityOptional.ifPresentOrElse(
                (val) -> {},
                () -> {
                    throw new IllegalArgumentException("Id must not be null!");
                }
        );


        Optional<City> cityToUpdate = findOne(entity.getId());
        AtomicReference<Optional<City>> cityToReturn = new AtomicReference<>();

        String sqlCommand = "UPDATE city SET name = ?, population = ? WHERE cityid = ?";

        cityToUpdate.ifPresentOrElse(
                (val) -> {
                    try(Connection connect = getSqlConnection();
                        PreparedStatement statement = connect.prepareStatement(sqlCommand))
                    {
                        statement.setString(1, entity.getName());
                        statement.setInt(2, entity.getPopulation());
                        statement.setLong(3, entity.getId());

                        statement.executeUpdate();

                        cityToReturn.set(Optional.empty());
                    }
                    catch(SQLIntegrityConstraintViolationException throwable){
                        cityToReturn.set(Optional.of(entity));
                    }
                    catch(SQLException throwable){
                        throw new BusManagementException("SQL Exception: " + throwable.getMessage());
                    }
                },
                () -> {
                    throw new IllegalArgumentException("City entity must not be null!");
                }
        );

        return cityToReturn.get();
    }
}
