package busManagement.repository.databaseRepository;

import java.sql.*;


import busManagement.domain.Bus;
import busManagement.domain.Ticket;
import busManagement.domain.exceptions.BusManagementException;
import busManagement.domain.exceptions.ValidatorException;
import busManagement.domain.validators.Validator;
import busManagement.repository.Repository;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

public class TicketDBRepository implements Repository<Long, Ticket> {

    private final Validator<Ticket> validator;
    private final String url;
    private final String username;
    private final String password;


    public TicketDBRepository(Validator<Ticket> validator_, String url_, String username_, String password_) {

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

    private Ticket retrieveCurrentResultAsTicket(ResultSet results) throws SQLException {

        Long tid = results.getLong("ticketid");
        Long pid = results.getLong("passengerid");
        Long bid = results.getLong("busid");
        LocalTime bt = results.getObject("boardingtime", LocalTime.class);
        Long price = results.getLong("price");
        Ticket ticket = new Ticket(pid, bid, bt, price);
        ticket.setId(tid);

        return ticket;
    }

    @Override
    public Optional<Ticket> findOne(Long aLong) {

        AtomicReference<Optional<Ticket>> ticketToReturn = new AtomicReference<>();

        Stream.ofNullable(aLong)
                .findAny()
                .ifPresentOrElse(

                        (val) -> {

                            String sqlQuery = "SELECT * FROM ticket WHERE ticketid = " + val.toString();

                            try (Connection connect = getSQLConnection();
                                 PreparedStatement statement = connect.prepareStatement(sqlQuery);
                                 ResultSet results = statement.executeQuery()) {

                                if (results.next()) {

                                    Ticket ticket = retrieveCurrentResultAsTicket(results);

                                    ticketToReturn.set(Optional.of(ticket));
                                }

                            } catch (SQLException throwable) {
                                throw new BusManagementException("SQL Exception: " + throwable.getMessage());
                            }

                        },
                        () -> {
                            throw new IllegalArgumentException("ID must not be null");
                        }
                );

        return ticketToReturn.get();
    }

    @Override
    public Iterable<Ticket> findAll() {

        Set<Ticket> entities = new HashSet<>();
        String sqlQuery = "SELECT * FROM ticket";

        try (Connection connect = getSQLConnection();
             PreparedStatement statement = connect.prepareStatement(sqlQuery);
             ResultSet results = statement.executeQuery()) {

            while (results.next()) {

                Ticket ticket = retrieveCurrentResultAsTicket(results);

                entities.add(ticket);
            }

        }
        catch (SQLException throwable) {
            throw new BusManagementException("SQL Exception: " + throwable.getMessage());
        }

        return entities;
    }

    @Override
    public Optional<Ticket> save(Ticket entity) throws ValidatorException {

        validator.validate(entity);

        Optional<Ticket> ticketOptional = Optional.ofNullable(entity);
        AtomicReference<Optional<Ticket>> returnedOptional = new AtomicReference<>();

        ticketOptional.ifPresentOrElse(

                (o) -> {

                    String sqlCommand = "INSERT INTO ticket(ticketid, passengerid, busid, boardingtime, price) VALUES (?, ?, ?, ?, ?)";

                    try (Connection connect = getSQLConnection();
                         PreparedStatement statement = connect.prepareStatement(sqlCommand)) {

                        statement.setLong(1, entity.getId());
                        statement.setLong(2, entity.getPassengerId());
                        statement.setLong(3, entity.getBusId());
                        statement.setObject(4, entity.getBoardingTime());
                        statement.setLong(5, entity.getPrice());

                        statement.executeUpdate();

                        returnedOptional.set(Optional.empty());

                    } catch (SQLIntegrityConstraintViolationException throwable) {

                        returnedOptional.set(Optional.ofNullable(entity));
                    } catch (SQLException throwable) {
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
    public Optional<Ticket> delete(Long aLong) {

        Optional<Long> idOptional = Optional.ofNullable(aLong);

        idOptional.ifPresentOrElse(

                (o) -> {},
                () -> {

                    throw new IllegalArgumentException("Id must not be null!");
                }
        );

        Optional<Ticket> ticketToDelete = findOne(aLong);
        AtomicReference<Optional<Ticket>> returnedOptional = new AtomicReference<>();

        String sqlCommand = "DELETE FROM ticket WHERE ticketid = " + aLong.toString();

        ticketToDelete.ifPresentOrElse(

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
    public Optional<Ticket> update(Ticket entity) throws ValidatorException {

        validator.validate(entity);

        Optional<Ticket> ticketOptional = Optional.ofNullable(entity);

        ticketOptional.ifPresentOrElse(

                (o) -> {},
                () -> {

                    throw new IllegalArgumentException("Id must not be null!");
                }
        );

        Optional<Ticket> ticketToUpdate = findOne(entity.getId());
        AtomicReference<Optional<Ticket>> returnedOptional = new AtomicReference<>();

        String sqlCommand = "UPDATE ticket SET boardingtime = ? AND price = ? WHERE ticketid = ?";

        ticketToUpdate.ifPresentOrElse(

                (o) -> {

                    try (Connection connect = getSQLConnection();
                         PreparedStatement statement = connect.prepareStatement(sqlCommand)) {

                        statement.setObject(1, entity.getBoardingTime());
                        statement.setLong(2, entity.getPrice());
                        statement.setLong(3, entity.getId());

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

                    throw new IllegalArgumentException("Ticket entity must not be null!");
                }
        );

        return returnedOptional.get();
    }
}
