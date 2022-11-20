package busManagement.service;

import busManagement.domain.*;
import busManagement.domain.exceptions.ValidatorException;
import busManagement.domain.exceptions.BusManagementException;
import busManagement.repository.Repository;
import busManagement.utils.Pair;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Service {

    private Repository<Long, City> cityRepo;
    private Repository<Long, BusStation> busStationRepo;
    private Repository<Long, Bus> busRepo;
    private Repository<Long, BusCompany> busCompanyRepo;
    private Repository<Pair<Long, Long>, BusStop> busStopRepo;
    private Repository<Long, Luggage> luggageRepo;
    private Repository<Long, Driver> driverRepo;
    private Repository<Long, Passenger> passengerRepo;
    private Repository<Pair<Long,Long>, Feedback> feedbackRepo;
    private Repository<Long, Ticket> ticketRepo;

    public Service(
            Repository<Long, City> cityRepo1,
            Repository<Long, BusStation> busStationRepo1,
            Repository<Long, Bus> busRepo1,
            Repository<Long, BusCompany> busCompanyRepo1,
            Repository<Pair<Long, Long>, BusStop> busStopRepo1,
            Repository<Long, Luggage> luggageRepo1,
            Repository<Long, Driver> driverRepo,
            Repository<Long, Passenger> passengerRepo1,
            Repository<Pair<Long,Long>, Feedback> feedbackRepo1,
            Repository<Long, Ticket> ticketRepo1) {

        cityRepo = cityRepo1;
        busStationRepo = busStationRepo1;
        busRepo = busRepo1;
        busCompanyRepo = busCompanyRepo1;
        busStopRepo = busStopRepo1;
        luggageRepo = luggageRepo1;
        this.driverRepo = driverRepo;
        passengerRepo = passengerRepo1;
        feedbackRepo = feedbackRepo1;
        ticketRepo = ticketRepo1;
    }

    /**
     * Adds the given city to the repository.
     *
     * @param city - given city
     * @throws ValidatorException if city is not valid
     */
    public void addCity(City city) throws ValidatorException {
        cityRepo.save(city);
    }

    /**
     * Adds the given bus company to the repository
     *
     * @param busCompany - given bus company
     * @throws ValidatorException if bus company is not valid
     */
    public void addBusCompany(BusCompany busCompany) throws ValidatorException {
        busCompanyRepo.save(busCompany);
    }

    /**
     * Adds the given Bus Station to the repository
     *
     * @param busStation - given bus station
     * @throws ValidatorException     if the bus station is not valid
     * @throws BusManagementException if the city specified for the bus station does not exist
     */
    public void addBusStation(BusStation busStation) throws ValidatorException {
        Optional<City> cityOptional = cityRepo.findOne(busStation.getCityId());
        cityOptional.ifPresentOrElse(
                (City c) -> {
                    busStationRepo.save(busStation);
                },
                () -> {
                    throw new BusManagementException("City does not exist!");
                }
        );
    }

    /**
     * Adds the given Bus to the repository
     *
     * @param bus - given bus
     * @throws ValidatorException     if the bus is not valid
     * @throws BusManagementException if the company and the driver specified for the bus does not exist
     */
    public void addBus(Bus bus) throws ValidatorException {

        Optional<BusCompany> companyOptional = busCompanyRepo.findOne(bus.getCompanyId());
        Optional<Driver> driverOptional = driverRepo.findOne(bus.getDriverId());

        Predicate<Optional<BusCompany>> companyNull = Optional::isEmpty;
        Predicate<Optional<Driver>> driverNull = Optional::isEmpty;

        final Pair<Boolean, String> resultCompany =
                new Pair<>(companyNull.test(companyOptional), "No company with the given ID has been found!\n");

        final Pair<Boolean, String> resultDriver =
                new Pair<>(driverNull.test(driverOptional), "No driver with the given ID has been found!\n");

        List<Pair<Boolean, String>> testList = new ArrayList<>();

        testList.add(resultCompany);
        testList.add(resultDriver);


        testList.stream()
                .filter((p) -> p.getFirst().equals(true))
                .map(Pair::getSecond)
                .reduce(String::concat)
                .ifPresentOrElse(
                        (msg) -> {
                            throw new BusManagementException(msg);
                        },
                        () -> busRepo.save(bus)
                );
    }

    public void addTicket(Ticket ticket) throws ValidatorException{
        Optional<Passenger> passagerOptional = passengerRepo.findOne(ticket.getPassengerId());
        Optional<Bus> busOptional = busRepo.findOne(ticket.getBusId());

        Predicate<Optional<Passenger>> passagerNull = Optional::isEmpty;
        Predicate<Optional<Bus>> busNull = Optional::isEmpty;

        final Pair<Boolean, String> resultPassager =
                new Pair<>(passagerNull.test(passagerOptional), "No passenger with the given ID has been found!\n");

        final Pair<Boolean, String> resultBuss =
                new Pair<>(busNull.test(busOptional), "No bus with the given ID has been found!\n");

        List<Pair<Boolean, String>> testList = new ArrayList<>();

        testList.add(resultPassager);
        testList.add(resultBuss);


        testList.stream()
                .filter((p) -> p.getFirst().equals(true))
                .map(Pair::getSecond)
                .reduce(String::concat)
                .ifPresentOrElse(
                        (msg) -> {throw new BusManagementException(msg);},
                        () -> ticketRepo.save(ticket)
                );
    }

    /**
     * Adds a given Driver to the repository
     * @param driver - the given driver
     * @throws ValidatorException if the driver has invalid data
     */
    public void addDriver(Driver driver) throws ValidatorException {
        driverRepo.save(driver);
    }

    /**
     * Adds a given Passenger to the repository
     * @param passenger - the given passenger
     * @throws ValidatorException if the passenger has invalid data
     */
    public void addPassenger(Passenger passenger) throws ValidatorException {
        passengerRepo.save(passenger);
    }

    /**
     * Adds the given feedback to the repository
     * @param feedback - given feedback
     * @throws ValidatorException if the feedback is not valid
     * @throws BusManagementException if the buscompany and the passeger specified for the feedback does not exist
     */

    public void addFeedback(Feedback feedback) throws ValidatorException {

        Optional<Passenger> passengerOptional = passengerRepo.findOne(feedback.getId().getFirst());
        Optional<BusCompany> companyOptional = busCompanyRepo.findOne(feedback.getId().getSecond());

        Predicate<Optional<Passenger>> passengerNull = Optional::isEmpty;
        Predicate<Optional<BusCompany>> companyNull = Optional::isEmpty;

        final Pair<Boolean, String> resultPassenger =
                new Pair<>(passengerNull.test(passengerOptional), "No passenger with the given ID has been found! ");

        final Pair<Boolean, String> resultCompany =
                new Pair<>(companyNull.test(companyOptional), "No company with the given ID has been found! ");

        List<Pair<Boolean, String>> testList2 = new ArrayList<>();

        testList2.add(resultPassenger);
        testList2.add(resultCompany);

        testList2.stream()
                .filter((p) -> p.getFirst().equals(true))
                .map(Pair::getSecond)
                .reduce(String::concat)
                .ifPresentOrElse(
                        (msg) -> {throw new BusManagementException(msg);},
                        () -> feedbackRepo.save(feedback)
                );
    }

    /**
     * Adds the given luggage to the Repository.
     * @param luggage - the new luggage that needs be added to the Repository.
     * @throws BusManagementException - if the passenger that claims this luggage doesn't exist.
     */
    public void addLuggage(Luggage luggage) throws ValidatorException{
        Optional<Passenger> optionalPassenger = passengerRepo.findOne(luggage.getPassengerId());
        optionalPassenger.ifPresentOrElse(
                (Passenger passenger) -> { luggageRepo.save(luggage);},
                () -> {throw  new BusManagementException("Passenger doesn't exist!");}
        );
    }

    /**
     * Adds the given Bus Stop to the repository
     *
     * @param busStop - given bus
     * @throws ValidatorException     if the bus stop is not valid
     * @throws BusManagementException if the bus or the bus station specified for the bus stop does not exist
     */
    public void addBusStop(BusStop busStop) throws ValidatorException {
        Optional<Bus> busOptional = busRepo.findOne(busStop.getBusId());
        Optional<BusStation> busStationOptional = busStationRepo.findOne(busStop.getBusStationId());

        Predicate<Optional<Bus>> busNull = Optional::isEmpty;
        Predicate<Optional<BusStation>> busStationNull = Optional::isEmpty;

        final Pair<Boolean, String> resultBus =
                new Pair<>(busNull.test(busOptional), "No Bus with the given ID has been found!\n");

        final Pair<Boolean, String> resultBusStation =
                new Pair<>(busStationNull.test(busStationOptional), "No Bus Station with the given ID has been found!\n");

        List<Pair<Boolean, String>> testList = new ArrayList<>();

        testList.add(resultBus);
        testList.add(resultBusStation);


        testList.stream()
                .filter((p) -> p.getFirst().equals(true))
                .map(Pair::getSecond)
                .reduce(String::concat)
                .ifPresentOrElse(
                        (msg) -> {
                            throw new BusManagementException(msg);
                        },
                        () -> busStopRepo.save(busStop)
                );
    }

    /**
     * Deletes a city based on it's id
     *
     * @param id - id of the city to be deleted
     * @throws IllegalArgumentException if the given id is null
     * @throws BusManagementException   if the city does not exist
     */
    public void deleteCity(Long id) throws ValidatorException {
        StreamSupport.stream(busStationRepo.findAll().spliterator(), false)
                .filter(station -> station.getId().equals(id))
                .findAny()
                .ifPresent((busStation) -> {
                    throw new BusManagementException("There are bus stations in the city!");
                });
        cityRepo.delete(id).orElseThrow(() -> {
            throw new BusManagementException("City does not exist");
        });
    }

    /**
     * Deletes a bus company based on its id
     *
     * @param id - id of the bus company to be deleted
     * @throws IllegalArgumentException if the given id is null
     * @throws BusManagementException   if the bus company does not exist
     */
    public void deleteBusCompany(Long id) throws ValidatorException {
        StreamSupport.stream(busRepo.findAll().spliterator(), false)
                        .filter(bus -> bus.getCompanyId().equals(id))
                        .findAny()
                        .ifPresent((bus) ->{
                            throw new BusManagementException("There are busses with the company id.");
                        });

        StreamSupport.stream(feedbackRepo.findAll().spliterator(), false)
                        .filter(feedback -> feedback.getId().getSecond().equals(id))
                        .findAny()
                        .ifPresent((feedback -> {
                            throw new BusManagementException("There are feedbacks with the company id.");
                        }));

        busCompanyRepo.delete(id).orElseThrow(() -> {
            throw new BusManagementException("Bus company does not exist");
        });
    }

    //TODO
    public void deleteBusStation(Long id) throws ValidatorException {

    }

    /**
     * Deletes a bus based on its id
     *
     * @param id - id of the bus company to be deleted
     * @throws IllegalArgumentException if the given id is null
     * @throws BusManagementException if the bus company does not exist or if there are tickets or stops with the given ID.
     */
    public void deleteBus(Long id) {

        StreamSupport.stream(ticketRepo.findAll().spliterator(), false)
                .filter(ticket -> ticket.getId().equals(id))
                .findAny()
                .ifPresent((obj) -> {throw new BusManagementException("Ticket(s) for this bus exist!");});

        StreamSupport.stream(busStopRepo.findAll().spliterator(), false)
                .filter(stop -> stop.getId().getFirst().equals(id))
                .findAny()
                .ifPresent((obj) -> {throw new BusManagementException("Stop(s) for this bus exist!");});

        busRepo.delete(id).orElseThrow(() -> {
           throw new BusManagementException("The bus with the given ID does not exist.");});

    }

    public void deleteTicket(Long id) throws ValidatorException{
        ticketRepo.delete(id).orElseThrow(()->{
            throw new BusManagementException("Ticket does not exist");
        });
    }

    /**
     * Deletes a driver based on its id
     * @param id - id of the driver to be deleted
     * @throws ValidatorException if the given id is null
     * @throws BusManagementException if the driver does not exist
     */
    public void deleteDriver(Long id) throws ValidatorException {
        StreamSupport.stream(busRepo.findAll().spliterator(), false)
                .filter(bus -> bus.getId().equals(id))
                .findAny()
                .ifPresent((bus) -> {
                    throw new BusManagementException("There are buses that have this driver!");
                });
        driverRepo.delete(id).orElseThrow(() -> {
            throw new BusManagementException("Driver does not exist");
        });
    }
    /**
     * Deletes a passenger based on its id
     * @param id - id of the passenger to be deleted
     * @throws ValidatorException if the given id is null
     * @throws BusManagementException if the passenger does not exist
     */
    public void deletePassenger(Long id) throws ValidatorException {
        StreamSupport.stream(luggageRepo.findAll().spliterator(), false)
                .filter(luggage -> luggage.getPassengerId().equals(id))
                .findAny()
                .ifPresent((obj) -> {throw new BusManagementException("Luggage for this passenger exists!");});
        StreamSupport.stream(feedbackRepo.findAll().spliterator(), false)
                .filter(feedback -> feedback.getId().getFirst().equals(id))
                .findAny()
                .ifPresent((obj) -> {throw new BusManagementException("Feedback for this passenger exists!");});
        StreamSupport.stream(ticketRepo.findAll().spliterator(), false)
                .filter(ticket -> ticket.getPassengerId().equals(id))
                .findAny()
                .ifPresent((obj) -> {throw new BusManagementException("Ticket for this passenger exists!");});

        passengerRepo.delete(id).orElseThrow(() -> {
            throw new BusManagementException("Passenger does not exist");
        });
    }

    /**
     * Deletes a feedback based on it's id
     * @param id - id of the feedback to be deleted
     * @throws IllegalArgumentException if the given id is null
     * @throws BusManagementException   if the feedback does not exist
     */
    public  void deleteFeedback(Pair<Long,Long> id) throws ValidatorException{
        feedbackRepo.delete(id).orElseThrow(()->{
            throw new BusManagementException("Feedback does not exist");
        });
    }

    /**
     * Deletes a luggage based on its ID.
     * @param id - the id of the luggage that needs to be deleted.
     * @throws BusManagementException - if the luggage doesn't exist.
     */
    public void deleteLuggage(Long id) throws ValidatorException{
        Optional<Luggage> luggageOptional = luggageRepo.findOne(id);
        luggageOptional.ifPresentOrElse(
                (Luggage luggage) -> {
                    Optional<Passenger> passengerOptional = passengerRepo.findOne(luggage.getPassengerId());
                    passengerOptional.ifPresentOrElse(
                            (Passenger passenger) -> {
                                luggageRepo.delete(id);
                            },
                            () -> {
                                throw new BusManagementException("The Passenger that has this luggage does not exist!");}
                    );
                }, ()->{throw new BusManagementException("This luggage does not exist!");}
        );
    }

    /**
     * Deletes a bus stop based on its id
     *
     * @param id - id of the bus stop to be deleted
     * @throws IllegalArgumentException if the given id is null
     * @throws BusManagementException   if the bus stop does not exist
     */
    public void deleteBusStop(Pair<Long, Long> id) throws ValidatorException
    {
        busStopRepo.delete(id).orElseThrow(() -> {
            throw new BusManagementException("Bus stop does not exist");
        });
    }
    /**
     * Update a feedback
     *
     * @param id - id of the feedback
     * @param newReview - updated review
     * @throws BusManagementException   if the feedback does not exist
     */
    public void updateFeedback(Pair<Long,Long> id, String newReview){
        var optional = feedbackRepo.findOne(id);

        optional.ifPresentOrElse(
                (o) ->{},
                ()  ->{throw new BusManagementException("Feedback does not exist");}
    );
        Feedback copy = optional.get();

        copy.setReview(newReview);

        feedbackRepo.update(copy);
    }

    /**
     * Updates attributes of a given driver
     * @param id - the Driver ID
     * @param newFName - the new first name to be given
     * @param newLName - the new last name to be given
     * @param newDOB - the new date of birth to be given
     * @param newMO - the new months active to be given
     */
    public void updateDriver(Long id, String newFName, String newLName, LocalDate newDOB, Integer newMO) {
        var optional = driverRepo.findOne(id);

        optional.ifPresentOrElse(
                (o) -> {},
                () -> {throw new BusManagementException("Driver not found!");}
        );

        Driver copy = optional.get();

        copy.setFirstName(newFName);
        copy.setLastName(newLName);
        copy.setDateOfBirth(newDOB);
        copy.setMonthsActive(newMO);

        driverRepo.update(copy);
    }

    /**
     * @return all the cities from the repository.
     */
    public Set<City> getCities() {
        return (Set<City>) cityRepo.findAll();
    }

    /**
     * @return all the bus companies from the repository
     */
    public Set<BusCompany> getBusCompanies() { return (Set<BusCompany>) busCompanyRepo.findAll(); }

    /**
     * @return all the bus stations from the repository
     */
    public Set<BusStation> getBusStations() {
        return (Set<BusStation>) busStationRepo.findAll();
    }

    /**
     * @return all the buses from the repository
     */
    public Set<Bus> getBuses() {
        return (Set<Bus>) busRepo.findAll();
    }

    /**
     * @return all the bus stops from the repository
     */
    public Set<BusStop> getBusStops() {
        return (Set<BusStop>) busStopRepo.findAll();
    }
    /**
    * @return all the luggages from the repository
     */
    public Set<Luggage> getLuggages(){ return (Set<Luggage>) luggageRepo.findAll();}
    
    /**
     * @return all the drivers from the repository
     */
    public Set<Driver> getDrivers() {
        return (Set<Driver>) driverRepo.findAll();
    }

    /**
     * @return all the passengers from the repository
     */
    public Set<Passenger> getPassengers() {
        return (Set<Passenger>) passengerRepo.findAll();
    }

    /**
     * @return all the feedbacks from the repository
     */
    public Set<Feedback> getFeedbacks() { return (Set<Feedback>) feedbackRepo.findAll(); }
    public Set<Ticket> getTickets() { return (Set<Ticket>) ticketRepo.findAll(); }
}
