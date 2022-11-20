package busManagement.ui;

import busManagement.domain.*;
import busManagement.service.Service;
import busManagement.utils.Pair;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Console {

    private Service service;
    private Map<Integer, Runnable> commandDict;

    public Console(Service service){
        this.service = service;
        this.commandDict = new HashMap<>();
    }

    private void printAllCities() {
        Set<City> cities = service.getCities();
        cities.stream().forEach(System.out::println);
    }

    private void printAllCompanies() {
        Set<BusCompany> busCompanies = service.getBusCompanies();
        busCompanies.stream().forEach(System.out::println);
    }

    private void printAllBusStations(){
        Set<BusStation> busStations = service.getBusStations();
        busStations.stream().forEach(System.out::println);
    }

    private void printAllBuses() {
        Set<Bus> buses = service.getBuses();
        buses.stream().forEach(System.out::println);
    }

    private void printAllBusStops() {
        Set<BusStop> busStops = service.getBusStops();
        busStops.stream().forEach(System.out::println);
    }

    private void printAllPassengers() {
        Set<Passenger> passengers = service.getPassengers();
        passengers.stream().forEach(System.out::println);
    }

    private void printAllFeedbacks() {
        Set<Feedback> feedbacks = service.getFeedbacks();
        feedbacks.stream().forEach(System.out::println);
    }

    private void printAllTickets() {
        Set<Ticket> tickets = service.getTickets();
        tickets.stream().forEach(System.out::println);
    }

    private void printAllLuggages() {
        Set<Luggage> luggages = service.getLuggages();
        luggages.stream().forEach(System.out::println);
    }

    private void printAllDrivers() {
        Set<Driver> drivers = service.getDrivers();
        drivers.stream().forEach(System.out::println);
    }

    private void addCityUi() {
        Scanner stdin = new Scanner(System.in);
        AtomicReference<Long> id = new AtomicReference<>(0L);
        service.getCities()
                .stream()
                .max(Comparator.comparingLong(BaseEntity::getId)).ifPresent(c -> id.set(c.getId()+1));

        System.out.print("Name: ");
        String name = stdin.next();
        System.out.print("Population: ");
        int population = stdin.nextInt();

        City city = new City(name, population);
        city.setId(id.get());

        service.addCity(city);
    }

    private void addBusStationUi() {

        Scanner stdin = new Scanner(System.in);
        AtomicReference<Long> id = new AtomicReference<>(0L);
        service.getBusStations()
                .stream()
                .max(Comparator.comparingLong(BaseEntity::getId)).ifPresent(s -> id.set(s.getId()+1));

        System.out.print("CityId: ");
        Long cid = (long) stdin.nextInt();
        System.out.print("Name: ");
        String name = stdin.next();

        BusStation station = new BusStation(cid, name);
        station.setId(id.get());

        service.addBusStation(station);
    }

    private void addBusUi() {

        Scanner stdin = new Scanner(System.in);
        AtomicReference<Long> id = new AtomicReference<>(0L);
        service.getBuses()
                .stream()
                .max(Comparator.comparingLong(BaseEntity::getId)).ifPresent(s -> id.set(s.getId()+1));

        System.out.print("CompanyId: ");
        Long cid = stdin.nextLong();
        System.out.print("DriverId: ");
        Long did = stdin.nextLong();
        System.out.print("ModelName: ");
        String name = stdin.next();

        Bus bus = new Bus(cid, did, name);
        bus.setId(id.get());

        service.addBus(bus);
    }

    private void addBusStopUi() {

    }

    private void addDriverUi() {

        // TODO: Check in driverRepo AND passengerRepo for the max ID
        Scanner stdin = new Scanner(System.in);
        AtomicReference<Long> id = new AtomicReference<>(0L);
        service.getDrivers()
                .stream()
                .max(Comparator.comparingLong(BaseEntity::getId)).ifPresent(s -> id.set(s.getId()+1));

        System.out.print("First Name: ");
        String firstName = stdin.next();
        System.out.print("Last Name: ");
        String lastName = stdin.next();
        System.out.print("Months Active");
        int months = stdin.nextInt();

        // TODO: read a date of birth
        Driver driver = new Driver(firstName, lastName, LocalDate.now(), months);
        driver.setId(id.get());

        service.addDriver(driver);
    }

    private void addBusCompanyUi() {

        Scanner stdin = new Scanner(System.in);
        AtomicReference<Long> id = new AtomicReference<>(0L);
        service.getBusCompanies()
                .stream()
                .max(Comparator.comparingLong(BaseEntity::getId)).ifPresent(c -> id.set(c.getId()+1));

        System.out.print("Name: ");
        String name = stdin.next();

        BusCompany company = new BusCompany(name);
        company.setId(id.get());
        
        service.addBusCompany(company);
    }

    private void addLuggageUi() {

    }

    private void addPassengerUi() {

    }

    private void addTicketUi() {

    }

    private void addFeedbackUi() {

    }

    private void deleteCityUi() {

        Scanner stdin = new Scanner(System.in);
        System.out.print("City ID: ");
        Long id = stdin.nextLong();

        service.deleteCity(id);

    }

    private void deleteBusStationUi() {

        Scanner stdin = new Scanner(System.in);
        System.out.print("BusStation ID: ");
        Long id = stdin.nextLong();

        service.deleteBusStation(id);
    }

    private void deleteBusUi() {

        Scanner stdin = new Scanner(System.in);
        System.out.print("Bus ID: ");
        Long id = stdin.nextLong();

        service.deleteBus(id);
    }

    private void deleteDriverUi() {

        Scanner stdin = new Scanner(System.in);
        System.out.print("Driver ID: ");
        Long id = stdin.nextLong();

        service.deleteDriver(id);
    }

    private void deleteBusStop() {

        Scanner stdin = new Scanner(System.in);
        System.out.print("Bus ID: ");
        Long bid = stdin.nextLong();
        System.out.print("Station ID: ");
        Long sid = stdin.nextLong();

        service.deleteBusStop(new Pair<>(bid, sid));
    }

    private static void printMenu() {
        System.out.println("1. Print all cities");
        System.out.println("2. Print all bus stations");
        System.out.println("3. Print all buses");
        System.out.println("4. Print all bus stops");
        System.out.println("5. Print all drivers");
        System.out.println("6. Print all bus companies");
        System.out.println("7. Print all luggages");
        System.out.println("8. Print all passengers");
        System.out.println("9. Print all tickets");
        System.out.println("10. Print all feedbacks");
        System.out.println("11. Add a city");
        System.out.println("12. Add a bus station");
        System.out.println("13. Add a bus");
        System.out.println("16. Add a bus company");
        System.out.println("21. Delete a city");
        System.out.println("22. Delete a bus station");
        System.out.println("23. Delete a bus");
    }

    private static int readNumberFromConsole() {
        Scanner stdin = new Scanner(System.in);
        return stdin.nextInt();
    }

    public void start() {

        commandDict.put(1, this::printAllCities);
        commandDict.put(2, this::printAllBusStations);
        commandDict.put(3, this::printAllBuses);
        commandDict.put(4, this::printAllBusStops);
        commandDict.put(5, this::printAllDrivers);
        commandDict.put(6, this::printAllCompanies);
        commandDict.put(7, this::printAllLuggages);
        commandDict.put(8, this::printAllPassengers);
        commandDict.put(9, this::printAllTickets);
        commandDict.put(10, this::printAllFeedbacks);

        commandDict.put(11, this::addCityUi);
        commandDict.put(12, this::addBusStationUi);
        commandDict.put(13, this::addBusUi);
        commandDict.put(16, this::addBusCompanyUi);

        commandDict.put(21, this::deleteCityUi);
        commandDict.put(22, this::deleteBusStationUi);
        commandDict.put(23, this::deleteBusUi);
        

        IntStream.generate(() -> 0)
                .forEach(i -> {
                    printMenu();
                    try {
                        int choice = readNumberFromConsole();
                        Stream.of(commandDict.get(choice))
                                .filter(Objects::nonNull)
                                .findAny()
                                .ifPresentOrElse(Runnable::run, () -> System.out.println("Bad choice"));
                    } catch (InputMismatchException inputMismatchException) {
                        System.out.println("Invalid integer");
                    } catch (Exception exception) {
                        System.out.println(exception.getMessage());
                    }
                });
    }
}
