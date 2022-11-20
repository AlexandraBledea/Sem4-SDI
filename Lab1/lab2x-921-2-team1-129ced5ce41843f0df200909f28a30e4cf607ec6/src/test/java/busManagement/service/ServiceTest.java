package busManagement.service;
import busManagement.domain.*;
import busManagement.domain.exceptions.BusManagementException;
import busManagement.domain.validators.*;
import busManagement.repository.InMemoryRepository;
import busManagement.repository.Repository;
import java.time.LocalTime;
import busManagement.utils.Pair;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


public class ServiceTest {

    private Repository<Long, City> cityRepo;
    private Repository<Long, BusStation> busStationRepo;
    private Repository<Long, Bus> busRepo;
    private Repository<Long, BusCompany> bussCompanyRepo;
    private Repository<Pair<Long, Long>, BusStop> busStopRepo;
    private Repository<Long, Luggage> luggageRepo;
    private Repository<Long, Driver> driverRepository;
    private Repository<Long, Passenger> passengerRepository;
    private Repository<Pair<Long,Long>, Feedback> feedbackRepo;
    private Repository<Long, Ticket> ticketRepo;
    private Service service;
    private City city;
    private BusStation busStation;
    private BusCompany busCompany;
    private Bus bus;
    private BusStop busStop;
    private Luggage luggage;
    private Driver driver;
    private Passenger passenger;
    private Feedback feedback;
    private Ticket ticket;

    public static final Long id = 1L;
    public static final Long newID = 2L;
    public static final String name = "a1";
    public static final String newName = "a2";
    public static final Long cityId = 1L;
    public static final Long newCityId = 2L;
    public static final int population = 10;
    public static final int newPopulation = 20;
    public static final String fName = "Ion";
    public static final String lName = "Popescu";
    public static final LocalDate date = LocalDate.of(2000, 1, 2);
    public static final Integer months = 6;
    public static final Integer timesTravelled = 90;
    public static final Pair<Long,Long> feedbackId = new Pair<>(id,id);
    public static final Pair<Long,Long> busStopId = new Pair<>(id,id);
    public static final Pair<Long,Long> newFeedbackId = new Pair<>(newID,newID);
    public static final String review = "r1";
    public static final String newReview = "r2";
    public static final Long passengerId = 1L;
    public static final Long newPassengerId = 2L;
    public static final Long busId = 1L;
    public static final Long newBusId = 2L;
    public static final Long boardingTime = 15L;
    public static final Long newBoardingTime = 20L;
    public static final Long price = 200L;
    public static final Long newPrice = 240L;


    @Before
    public void setUp() throws Exception{
        cityRepo = new InMemoryRepository<>(new CityValidator());
        busStationRepo = new InMemoryRepository<>(new BusStationValidator());
        busRepo = new InMemoryRepository<>(new BusValidator());
        bussCompanyRepo = new InMemoryRepository<>(new BusCompanyValidator());
        busStopRepo = new InMemoryRepository<>(new BusStopValidator());
        luggageRepo = new InMemoryRepository<>(new LuggageValidator());
        driverRepository = new InMemoryRepository<>(new DriverValidator());
        passengerRepository = new InMemoryRepository<>(new PassengerValidator());
        feedbackRepo = new InMemoryRepository<>(new FeedbackValidator());
        ticketRepo = new InMemoryRepository<>(new TicketValidator());

        service = new Service(cityRepo, busStationRepo, busRepo, bussCompanyRepo, busStopRepo, luggageRepo, driverRepository, passengerRepository, 
                              feedbackRepo, ticketRepo);
        city = new City(name, population);
        city.setId(id);
        busStation = new BusStation(cityId, name);
        busStation.setId(newID);
        driver = new Driver(fName, lName, date, months);
        driver.setId(id);
        busCompany = new BusCompany(name);
        busCompany.setId(id);
        bus = new Bus(busCompany.getId(), driver.getId(), "MAN");
        bus.setId(id);
        busStop = new BusStop(LocalTime.NOON.minusHours(1));
        busStop.setId(new Pair<>(bus.getId(), busStation.getId()));



        passenger = new Passenger(fName, lName, date, timesTravelled);
        passenger.setId(id);

        feedback = new Feedback(review);
        feedback.setId(feedbackId);
      
        ticket = new Ticket(passengerId, busId, boardingTime, price);
        ticket.setId(id);
    }

    @Test
    public void testValidAddCity() throws Exception{
        service.addCity(city);
        assertEquals(service.getCities().size(), 1);
    }

    @Test
    public void testValidAddBusStationCity() throws Exception{
        service.addCity(city);
        service.addBusStation(busStation);
        assertEquals(service.getBusStations().size(), 1);
    }

    @Test
    public void testNotValidAddBusStationCity() throws Exception{
        Throwable ex = assertThrowsExactly(BusManagementException.class, ()->service.addBusStation(busStation));
        assertEquals(ex.getMessage(), "City does not exist!");
    }

    @Test
    public void testValidAddBusCompany() throws Exception {
        service.addBusCompany(busCompany);
        assertEquals(service.getBusCompanies().size(), 1);
    }

    @Test
    public void testValidAddBusStop() throws Exception{
        service.addCity(city);
        service.addBusCompany(busCompany);
        service.addDriver(driver);
        service.addBus(bus);
        service.addBusStation(busStation);
        service.addBusStop(busStop);
        assertEquals(service.getBusStops().size(), 1);
    }
    @Test
    public void testNotValidAddBusStop() throws Exception{
        Throwable ex = assertThrowsExactly(BusManagementException.class, ()->service.addBusStop(busStop));
        assertEquals(ex.getMessage(), "No Bus with the given ID has been found!\nNo Bus Station with the given ID has been found!\n");
    }

    @Test
    public void testDeleteCity() throws Exception{
    }

    @Test
    public void testNotValidDeleteBusStop() throws Exception{
        Throwable ex = assertThrowsExactly(BusManagementException.class, ()->service.deleteBusStop(new Pair<Long, Long> (10L, 12L)));
        assertEquals(ex.getMessage(),"Bus stop does not exist");

    }

    @Test
    public void testValidDeleteBusStop() throws Exception {
        service.addCity(city);
        service.addBusCompany(busCompany);
        service.addDriver(driver);
        service.addBus(bus);
        service.addBusStation(busStation);
        service.addBusStop(busStop);
        assertEquals(service.getBusStops().size(), 1);
        assertDoesNotThrow(() -> service.deleteBusStop(busStop.getId()));
        assertEquals(service.getBusStops().size(), 0);
    }

    @Test
    public void testValidAddBusCompanyDriver() {

    }

    @Test
    public void testValidAddDriver() {
        service.addDriver(driver);
        assertEquals(service.getDrivers().size(), 1);
    }

    @Test
    public void testValidAddPassenger() {
        service.addPassenger(passenger);
        assertEquals(service.getPassengers().size(), 1);
    }

    @Test
    public void testValidAddFeedback() {
        service.addPassenger(passenger);
        service.addBusCompany(busCompany);
        service.addFeedback(feedback);
        assertEquals(service.getFeedbacks().size(), 1);

    }

    @Test
    public void testNotValidAddFeedback() {
        Throwable ex = assertThrowsExactly(BusManagementException.class, ()->service.addFeedback(feedback));
        assertEquals(ex.getMessage(), "No passenger with the given ID has been found! " + "No company with the given ID has been found! ");

    }


    @Test
    public void testDeleteDriver() {
        Throwable ex = assertThrowsExactly(BusManagementException.class, ()->service.deleteDriver(id));
        assertEquals(ex.getMessage(), "Driver does not exist");

        service.addDriver(driver);
        assertEquals(service.getDrivers().size(), 1);

        service.deleteDriver(id);
        assertEquals(service.getDrivers().size(), 0);
    }

    @Test
    public void testDeletePassenger() {
        Throwable ex = assertThrowsExactly(BusManagementException.class, ()->service.deletePassenger(id));
        assertEquals(ex.getMessage(), "Passenger does not exist");

        service.addPassenger(passenger);
        assertEquals(service.getPassengers().size(), 1);

        service.deletePassenger(id);
        assertEquals(service.getPassengers().size(), 0);
    }

    @Test
    public void testDeleteFeedback() {
        Throwable ex = assertThrowsExactly(BusManagementException.class, () -> service.deleteFeedback(feedbackId));
        assertEquals(ex.getMessage(), "Feedback does not exist");

        service.addPassenger(passenger);
        service.addBusCompany(busCompany);
        service.addFeedback(feedback);
        assertEquals(service.getFeedbacks().size(), 1);

        service.deleteFeedback(feedbackId);
        assertEquals(service.getFeedbacks().size(), 0);

    }

    @Test
    public void testValidAddTicket() throws Exception {
        service.addTicket(ticket);
        assertEquals(service.getTickets().size(), 1);
    }

    @Test
    public void testDeleteTicket() throws Exception{

    }

    @Test
    public void testDeleteBusCompany() throws Exception {
        service.addBusCompany(busCompany);
        assertEquals(service.getBusCompanies().size(), 1);
        service.deleteBusCompany(1L);
        assertEquals(service.getBusCompanies().size(), 0);
        busCompany.setId(2L);
        service.addBusCompany(busCompany);
        assertEquals(service.getBusCompanies().size(), 1);
    }
}
