package busManagement.service;
import busManagement.domain.*;
import busManagement.domain.exceptions.BusManagementException;
import busManagement.domain.validators.*;
import busManagement.repository.InMemoryRepository;
import busManagement.repository.Repository;
import java.time.LocalTime;

import busManagement.repository.xmlRepository.*;
import busManagement.repository.xmlRepository.BusXMLRepository;

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
    public static final Integer weight = 10;
    public static final Pair<Long,Long> feedbackId = new Pair<>(id,id);
    public static final Pair<Long,Long> busStopId = new Pair<>(id,id);
    public static final Pair<Long,Long> newFeedbackId = new Pair<>(newID,newID);
    public static final String review = "r1";
    public static final String newReview = "r2";
    public static final Long passengerId = 1L;
    public static final Long newPassengerId = 2L;
    public static final Long busId = 1L;
    public static final Long newBusId = 2L;
    public static final LocalTime boardingTime = LocalTime.of(15, 0);
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
        busStation.setId(id);

        driver = new Driver(fName, lName, date, months);
        driver.setId(id);

        busCompany = new BusCompany(name);
        busCompany.setId(id);

        bus = new Bus(busCompany.getId(), driver.getId(), "MAN");
        bus.setId(id);

        busStop = new BusStop(LocalTime.NOON.minusHours(1));
        busStop.setId(new Pair<>(bus.getId(), busStation.getId()));

        luggage = new Luggage(passengerId,weight);
        luggage.setId(id);

        passenger = new Passenger(fName, lName, date, timesTravelled);
        passenger.setId(id);

        feedback = new Feedback(review);
        feedback.setId(feedbackId);
      
        ticket = new Ticket(passenger.getId(), bus.getId(), boardingTime, price);
        ticket.setId(id);
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
    public void testValidAddLuggage() throws Exception{
        service.addPassenger(passenger);
        service.addLuggage(luggage);
        assertEquals(service.getLuggages().size(), 1);
    }

    @Test
    public void testNotValidAddLuggage() throws Exception{
        Throwable ex = assertThrowsExactly(BusManagementException.class, ()->service.addLuggage(luggage));
        assertEquals(ex.getMessage(), "Passenger doesn't exist!");
        assertEquals(service.getLuggages().size(), 0);
    }

    @Test
    public void testValidDeleteLuggage() throws Exception{
        service.addPassenger(passenger);
        service.addLuggage(luggage);
        assertEquals(service.getLuggages().size(), 1);
        service.deleteLuggage(id);
        assertEquals(service.getLuggages().size(), 0);
    }

    @Test
    public void testNotValidDeleteLuggage() throws Exception{
        Throwable ex1 = assertThrowsExactly(BusManagementException.class, ()->service.deleteLuggage(id));
        assertEquals(ex1.getMessage(), "This luggage does not exist!");
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
    public void testNotValidUpdateBusStop() throws Exception{
        Throwable ex = assertThrowsExactly(BusManagementException.class, ()->service.updateBusStop(new Pair<Long, Long> (10L, 12L), LocalTime.MIDNIGHT));
        assertEquals(ex.getMessage(),"Bus stop does not exist");

    }
    @Test
    public void testValidUpdateBusStop() throws Exception{
        service.addCity(city);
        service.addBusCompany(busCompany);
        service.addDriver(driver);
        service.addBus(bus);
        service.addBusStation(busStation);
        service.addBusStop(busStop);
        service.updateBusStop(busStop.getId(), LocalTime.MIDNIGHT.minusMinutes(23));
        service.getBusStops()
                .stream()
                .filter(item -> item.getId().equals(busStop.getId()))
                .findFirst()
                .ifPresentOrElse((o) -> {
                    assertEquals(o.getStopTime(), LocalTime.MIDNIGHT.minusMinutes(23));
        }, () -> {
                    assertFalse(true);
                });
    }
    @Test
    public void testValidAddBusCompanyDriver() {

    }

    @Test
    public void testValidAddTicket() throws Exception {
        service.addPassenger(passenger);
        service.addDriver(driver);
        service.addBusCompany(busCompany);
        service.addBus(bus);
        service.addTicket(ticket);
        assertEquals(service.getTickets().size(), 1);
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
    public void testDeleteTicket() throws Exception{

    }

    @Test
    public void testValidAddCity() throws Exception{
        service.addCity(city);
        assertEquals(service.getCities().size(), 1);
    }

    @Test
    public void testNotValidDeleteCity() throws Exception{
        Throwable ex = assertThrowsExactly(BusManagementException.class, () -> service.deleteCity(cityId));
        assertEquals(ex.getMessage(), "City does not exist");

        service.addCity(city);
        service.addBusStation(busStation);

        ex = assertThrowsExactly(BusManagementException.class, ()->service.deleteCity(cityId));
        assertEquals(ex.getMessage(), "There are bus stations in the city!");

    }

    @Test
    public void testValidDeleteCity() throws Exception{
        service.addCity(city);
        assertEquals(service.getCities().size(), 1);

        service.deleteCity(city.getId());
        assertEquals(service.getCities().size(), 0);

    }

    @Test
    public void testValidAddBusStation() throws Exception{
        service.addCity(city);
        service.addBusStation(busStation);
        assertEquals(service.getBusStations().size(), 1);
    }

    @Test
    public void testNotValidAddBusStation() throws Exception{
        Throwable ex = assertThrowsExactly(BusManagementException.class, ()->service.addBusStation(busStation));
        assertEquals(ex.getMessage(), "City does not exist!");
    }


    @Test
    public void testNotValidDeleteBusStation() throws Exception{
        Throwable ex = assertThrowsExactly(BusManagementException.class, () -> service.deleteBusStation(busStation.getId()));
        assertEquals(ex.getMessage(), "BusStation does not exist!");
        service.addCity(city);
        service.addBusStation(busStation);
        service.addBusCompany(busCompany);
        service.addDriver(driver);
        service.addBus(bus);
        service.addBusStop(busStop);

        ex = assertThrowsExactly(BusManagementException.class, ()->service.deleteBusStation(busStation.getId()));
        assertEquals(ex.getMessage(), "There are bus stops in the bus station!");

    }

    @Test
    public void testValidDeleteBusStation() throws Exception{
        service.addCity(city);
        service.addBusStation(busStation);
        assertEquals(service.getBusStations().size(), 1);

        service.deleteBusStation(busStation.getId());
        assertEquals(service.getBusStations().size(), 0);
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

    @Test
    public void testNotValidUpdateCity(){
        Throwable ex = assertThrowsExactly(BusManagementException.class, () -> service.updateCity(30L, "b3", 10));
        assertEquals(ex.getMessage(), "City not found!");
    }

    @Test
    public void testUpdateCity(){
        String newName = "Cluj";
        int newPopulation = 3000;

        service.addCity(city);
        service.updateCity(city.getId(), newName, newPopulation);
        assertEquals(city.getName(), newName);
        assertEquals(city.getPopulation(), newPopulation);

    }

    @Test
    public void testNotValidUpdateBusStation(){
        Throwable ex = assertThrowsExactly(BusManagementException.class, ()->service.updateBusStation(40L, 10L, "c4"));
        assertEquals(ex.getMessage(), "BusStation not found!");

        service.addCity(city);
        service.addBusStation(busStation);
        ex = assertThrowsExactly(BusManagementException.class, ()->service.updateBusStation(busStation.getCityId(), 50L, "br10"));
        assertEquals(ex.getMessage(), "There is no City with the given id!");
    }

    @Test
    public void testUpdateBusStation(){
        City city2 = new City("Sighet", 4000);
        city2.setId(id);
        String newName = "Cluj";

        service.addCity(city);
        service.addCity(city2);
        service.addBusStation(busStation);
        service.updateBusStation(bus.getId(), city2.getId(), newName);
        assertEquals(busStation.getCityId(), city2.getId());
        assertEquals(busStation.getName(), newName);

    }

    @Test
    public void testBusXMLRepo() {

        Repository<Long, Bus> busXMLRepo = new BusXMLRepository(new BusValidator(), "./data/xmlData/buses.xml");

        service = new Service(cityRepo, busStationRepo, busXMLRepo, bussCompanyRepo, busStopRepo, luggageRepo, driverRepository, passengerRepository,
                feedbackRepo, ticketRepo);

        service.addDriver(driver);
        service.addBusCompany(busCompany);

        assertEquals(service.getBuses().size(), 0);

        service.addBus(bus);
        assertEquals(service.getBuses().size(), 1);

        service.deleteBus(bus.getId());
        assertEquals(service.getBuses().size(), 0);
    }

    @Test
    public void testCityXMLRepo(){
        Repository<Long, City> cityXMLRepo = new CityXMLRepository(new CityValidator(), "./data/xmlData/cities.xml");
        service = new Service(cityXMLRepo, busStationRepo, busRepo, bussCompanyRepo, busStopRepo, luggageRepo, driverRepository, passengerRepository,
                feedbackRepo, ticketRepo);

        service.addCity(city);
        assertEquals(service.getCities().size(), 1);

        service.deleteCity(city.getId());
        assertEquals(service.getCities().size(), 0);
    }

    @Test
    public void testBusStationXMLRepo(){
        Repository<Long, BusStation> busStationXMLRepo = new BusStationXMLRepository(new BusStationValidator(), "./data/xmlData/busStations.xml");
        service = new Service(cityRepo, busStationXMLRepo, busRepo, bussCompanyRepo, busStopRepo, luggageRepo, driverRepository, passengerRepository,
                feedbackRepo, ticketRepo);

        service.addCity(city);
        service.addBusStation(busStation);
        assertEquals(service.getBusStations().size(), 1);

        service.deleteBusStation(busStation.getId());
        assertEquals(service.getBusStations().size(), 0);

    }

    @Test
    public void testBusStopXMLRepo() {

        Repository<Pair<Long, Long>, BusStop> busStopXMLRepo = new BusStopXMLRepository(new BusStopValidator(), "./data/xmlData/busStops.xml");

        service = new Service(cityRepo, busStationRepo, busRepo, bussCompanyRepo, busStopXMLRepo, luggageRepo, driverRepository, passengerRepository,
                feedbackRepo, ticketRepo);

        service.addCity(city);
        service.addBusCompany(busCompany);
        service.addDriver(driver);
        service.addBus(bus);
        service.addBusStation(busStation);

        assertEquals(service.getBusStops().size(), 0);

        service.addBusStop(busStop);

        assertEquals(service.getBusStops().size(), 1);

        service.deleteBusStop(busStop.getId());
        assertEquals(service.getBusStops().size(), 0);
    }


    @Test
    public void testBusCompanyXMLRepo() {
        Repository<Long, BusCompany> busCompanyXMLRepo = new BusCompanyXMLRepository(new BusCompanyValidator(), "./data/xmlData/busCompanies.xml");

        service = new Service(cityRepo, busStationRepo, busRepo, busCompanyXMLRepo, busStopRepo, luggageRepo, driverRepository, passengerRepository,
                feedbackRepo, ticketRepo);

        assertEquals(service.getBusCompanies().size(), 0);

        service.addBusCompany(busCompany);

        assertEquals(service.getBusCompanies().size(), 1);

        service.deleteBusCompany(busCompany.getId());

        assertEquals(service.getBusCompanies().size(), 0);
    }

  
    @Test
    public void testFeedbackXMLRepo() {

        Repository<Pair<Long,Long>, Feedback> feedbackXMLRepo = new FeedbackXMLRepository(new FeedbackValidator(), "./data/xmlData/feedbacks.xml");

        service = new Service(cityRepo, busStationRepo, busRepo, bussCompanyRepo, busStopRepo, luggageRepo, driverRepository, passengerRepository,
                feedbackXMLRepo, ticketRepo);

        service.addPassenger(passenger);
        service.addBusCompany(busCompany);

        assertEquals(service.getFeedbacks().size(), 0);

        service.addFeedback(feedback);
        assertEquals(service.getFeedbacks().size(), 1);

        service.deleteFeedback(feedback.getId());
        assertEquals(service.getFeedbacks().size(), 0);
      
     }

    @Test
    public void testDriverXMLRepo(){
        Repository<Long, Driver> driverXMLRepo = new DriverXMLRepository(new DriverValidator(),
                "./data/xmlData/drivers.xml");

        service = new Service(cityRepo, busStationRepo, busRepo, bussCompanyRepo, busStopRepo, luggageRepo, driverXMLRepo, passengerRepository,
                feedbackRepo, ticketRepo);

        assertEquals(service.getDrivers().size(), 0);

        service.addDriver(driver);
        assertEquals(service.getDrivers().size(), 1);

        service.deleteDriver(driver.getId());
        assertEquals(service.getDrivers().size(), 0);
    }

    @Test
    public void testPassengerXMLRepo() {
        Repository<Long, Passenger> passengerXMLRepo = new PassengerXMLRepository(new PassengerValidator(),
                "./data/xmlData/passengers.xml");

        service = new Service(cityRepo, busStationRepo, busRepo, bussCompanyRepo, busStopRepo, luggageRepo, driverRepository, passengerXMLRepo,
                feedbackRepo, ticketRepo);

        assertEquals(service.getPassengers().size(), 0);

        service.addPassenger(passenger);
        assertEquals(service.getPassengers().size(), 1);

        service.deletePassenger(passenger.getId());
        assertEquals(service.getPassengers().size(), 0);
    }

    @Test
    public void testTicketXMLRepo() {

        Repository<Long, Ticket> ticketXMLRepo = new TicketXMLRepository(new TicketValidator(), "./data/xmlData/tickets.xml");

        service = new Service(cityRepo, busStationRepo, busRepo, bussCompanyRepo, busStopRepo, luggageRepo, driverRepository, passengerRepository,
                feedbackRepo, ticketXMLRepo);

        service.addPassenger(passenger);
        service.addBusCompany(busCompany);
        service.addDriver(driver);
        service.addBus(bus);

        assertEquals(service.getTickets().size(), 0);

        service.addTicket(ticket);
        assertEquals(service.getTickets().size(), 1);

        service.deleteTicket(bus.getId());
        assertEquals(service.getTickets().size(), 0);
    }

    @Test
    public void testLuggageRepo() {

        Repository<Long, Luggage> luggageXMLRepo = new LuggageXMLRepository(new LuggageValidator(), "./data/xmlData/luggages.xml");

        service = new Service(cityRepo, busStationRepo, busRepo, bussCompanyRepo, busStopRepo, luggageXMLRepo, driverRepository, passengerRepository,
                feedbackRepo, ticketRepo);

        service.addPassenger(passenger);

        assertEquals(service.getLuggages().size(), 0);

        service.addLuggage(luggage);
        assertEquals(service.getLuggages().size(), 1);

        service.deleteLuggage(luggage.getId());
        assertEquals(service.getLuggages().size(), 0);
    }
}
