package busManagement.ui;

import busManagement.domain.*;
import busManagement.service.Service;
import busManagement.utils.Pair;

import java.time.LocalDate;
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

        Scanner stdin = new Scanner(System.in);

        System.out.print("BusId: ");
        Long bid = stdin.nextLong();

        System.out.print("BusStationId: ");
        Long sid = stdin.nextLong();

        System.out.print("StopTime (HH:MM): ");
        String rawTime = stdin.next();

        String[] splitTime = rawTime.split(":");

        BusStop stop = new BusStop(LocalTime.of(Integer.parseInt(splitTime[0]), Integer.parseInt(splitTime[1])));
        stop.setId(new Pair<>(bid, sid));

        service.addBusStop(stop);
    }

    private void addDriverUi() {

        Scanner stdin = new Scanner(System.in);
        AtomicReference<Long> maxDid = new AtomicReference<>(0L);
        AtomicReference<Long> maxPid = new AtomicReference<>(0L);

        service.getDrivers()
                .stream()
                .max(Comparator.comparingLong(BaseEntity::getId)).ifPresent(s -> maxDid.set(s.getId()));

        service.getPassengers()
                .stream()
                .max(Comparator.comparingLong(BaseEntity::getId)).ifPresent(s -> maxPid.set(s.getId()));

        Long id = (maxPid.get() > maxDid.get()) ? maxDid.get()+1 : maxPid.get()+1;

        System.out.print("First Name: ");
        String firstName = stdin.next();
        System.out.print("Last Name: ");
        String lastName = stdin.next();

        System.out.print("Date Of Birth (DD-MM-YYYY): ");
        String rawDate = stdin.next();

        String[] splitDate = rawDate.split("-");

        System.out.print("Months Active: ");
        int months = stdin.nextInt();

        Driver driver = new Driver(firstName, lastName,
                LocalDate.of(Integer.parseInt(splitDate[2]),
                        Integer.parseInt(splitDate[1]),
                        Integer.parseInt(splitDate[0])),
                months);

        driver.setId(id);

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

        Scanner stdin = new Scanner(System.in);

        AtomicReference<Long> id = new AtomicReference<>(0L);
        service.getLuggages()
                .stream()
                .max(Comparator.comparingLong(BaseEntity::getId)).ifPresent(l -> id.set(l.getId()+1));

        System.out.print("passengerId: ");
        Long pid = stdin.nextLong();

        System.out.print("weight: ");
        int weight = stdin.nextInt();

        Luggage luggage = new Luggage(pid, weight);
        luggage.setId(id.get());

        service.addLuggage(luggage);
    }

    private void addPassengerUi() {

        Scanner stdin = new Scanner(System.in);
        AtomicReference<Long> maxDid = new AtomicReference<>(0L);
        AtomicReference<Long> maxPid = new AtomicReference<>(0L);

        service.getDrivers()
                .stream()
                .max(Comparator.comparingLong(BaseEntity::getId)).ifPresent(s -> maxDid.set(s.getId()));

        service.getPassengers()
                .stream()
                .max(Comparator.comparingLong(BaseEntity::getId)).ifPresent(s -> maxPid.set(s.getId()));

        Long id = (maxPid.get() > maxDid.get()) ? maxDid.get()+1 : maxPid.get()+1;

        System.out.print("First Name: ");
        String firstName = stdin.next();
        System.out.print("Last Name: ");
        String lastName = stdin.next();

        System.out.print("Date Of Birth (DD-MM-YYYY): ");
        String rawDate = stdin.next();

        String[] splitDate = rawDate.split("-");

        System.out.print("Times Travelled: ");
        int times = stdin.nextInt();

        Passenger passenger = new Passenger(firstName, lastName,
                LocalDate.of(Integer.parseInt(splitDate[2]), Integer.parseInt(splitDate[1]), Integer.parseInt(splitDate[0])), times);

        passenger.setId(id);

        service.addPassenger(passenger);
    }

    private void addTicketUi() {

        Scanner stdin = new Scanner(System.in);
        AtomicReference<Long> id = new AtomicReference<>(0L);
        service.getTickets()
                .stream()
                .max(Comparator.comparingLong(BaseEntity::getId)).ifPresent(t -> id.set(t.getId()+1));

        System.out.print("PassengerId: ");
        Long pid = stdin.nextLong();

        System.out.print("BusId: ");
        Long bid = stdin.nextLong();

        System.out.print("Boarding Time (HH:MM): ");
        String rawTime = stdin.next();
        String[] splitTime = rawTime.split(":");

        System.out.print("Price: ");
        Long price = stdin.nextLong();

        Ticket ticket = new Ticket(pid, bid,
                LocalTime.of(Integer.parseInt(splitTime[0]), Integer.parseInt(splitTime[1])),
                price);

        ticket.setId(id.get());

        service.addTicket(ticket);
    }

    private void addFeedbackUi() {

        Scanner stdin = new Scanner(System.in);

        System.out.print("PassengerId: ");
        Long pid = stdin.nextLong();

        System.out.print("CompanyId: ");
        Long cid = stdin.nextLong();

        System.out.print("Review: ");
        String review = stdin.next();

        Feedback fb = new Feedback(review);
        fb.setId(new Pair<>(pid, cid));

        service.addFeedback(fb);
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

    private void deleteBusStopUi() {

        Scanner stdin = new Scanner(System.in);
        System.out.print("Bus ID: ");
        Long bid = stdin.nextLong();
        System.out.print("Station ID: ");
        Long sid = stdin.nextLong();

        service.deleteBusStop(new Pair<>(bid, sid));
    }

    private void deleteDriverUi() {

        Scanner stdin = new Scanner(System.in);
        System.out.print("Driver ID: ");
        Long id = stdin.nextLong();

        service.deleteDriver(id);
    }

    private void deleteBusCompanyUi() {

        Scanner stdin = new Scanner(System.in);
        System.out.print("Company ID: ");
        Long id = stdin.nextLong();

        service.deleteBusCompany(id);
    }

    private void deleteLuggageUi() {

        Scanner stdin = new Scanner(System.in);
        System.out.print("Luggage ID: ");
        Long id = stdin.nextLong();

        service.deleteLuggage(id);
    }

    private void deletePassengerUi() {

        Scanner stdin = new Scanner(System.in);
        System.out.print("Passenger ID: ");
        Long id = stdin.nextLong();

        service.deletePassenger(id);
    }

    private void deleteTicketUi() {

        Scanner stdin = new Scanner(System.in);
        System.out.print("Ticket ID");
        Long id = stdin.nextLong();

        service.deleteTicket(id);
    }

    private void deleteFeedbackUi() {

        Scanner stdin = new Scanner(System.in);
        System.out.print("Passenger ID: ");
        Long pid = stdin.nextLong();
        System.out.print("Company ID: ");
        Long cid = stdin.nextLong();

        service.deleteFeedback(new Pair<>(pid, cid));
    }

    private void updateCityUi(){
        Scanner stdin = new Scanner(System.in);

        System.out.println("City ID: ");
        Long id = stdin.nextLong();

        System.out.print("Name: ");
        String name = stdin.next();

        System.out.print("Population: ");
        int population = stdin.nextInt();

        service.updateCity(id, name, population);


    }

    private void updateBusStationUi(){
        Scanner stdin = new Scanner(System.in);

        System.out.println("BusStation ID: ");
        Long id = stdin.nextLong();

        System.out.print("CityId: ");
        Long cid = (long) stdin.nextInt();

        System.out.print("Name: ");
        String name = stdin.next();

        service.updateBusStation(id, cid, name);
    }

    private void updateBusUi() {

        Scanner stdin = new Scanner(System.in);

        System.out.print("Bus ID: ");
        Long id = stdin.nextLong();

        System.out.print("New Model Name: ");
        String model = stdin.next();

        service.updateBus(id, model);
    }

    private void updateBusStopUi() {

    }

    private void updateDriverUi() {

        Scanner stdin = new Scanner(System.in);

        System.out.print("Driver ID: ");
        Long id = stdin.nextLong();

        System.out.print("First Name: ");
        String firstName = stdin.next();

        System.out.print("Last Name: ");
        String lastName = stdin.next();

        System.out.print("Date Of Birth (DD-MM-YYYY): ");
        String rawDate = stdin.next();

        String[] splitDate = rawDate.split("-");

        System.out.print("Months Active: ");
        int months = stdin.nextInt();

        service.updateDriver(id, firstName, lastName,
                LocalDate.of(Integer.parseInt(splitDate[2]), Integer.parseInt(splitDate[1]), Integer.parseInt(splitDate[0])),
                months);
    }

    private void updateBusCompanyUi() {

    }

    private void updateLuggageUi() {

    }

    private void updatePassengerUi() {

    }

    private void updateTicketUi() {

    }

    private void updateFeedbackUi() {

        Scanner stdin = new Scanner(System.in);

        System.out.print("PassengerId: ");
        Long pid = stdin.nextLong();

        System.out.print("CompanyId: ");
        Long cid = stdin.nextLong();

        System.out.print("Review: ");
        String review = stdin.next();

        service.updateFeedback(new Pair<>(pid, cid), review);
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
        System.out.println("14. Add a bus stop");
        System.out.println("15. Add a driver");
        System.out.println("16. Add a bus company");
        System.out.println("17. Add a luggage");
        System.out.println("18. Add a passenger");
        System.out.println("19. Add a ticket");
        System.out.println("20. Add a feedback");
        System.out.println("21. Delete a city");
        System.out.println("22. Delete a bus station");
        System.out.println("23. Delete a bus");
        System.out.println("24. Delete a bus stop");
        System.out.println("25. Delete a driver");
        System.out.println("26. Delete a bus company");
        System.out.println("27. Delete a luggage");
        System.out.println("28. Delete a passenger");
        System.out.println("29. Delete a ticket");
        System.out.println("30. Delete a feedback");
        System.out.println("31. Update a city");
        System.out.println("32. Update a bus station");
        System.out.println("33. Update a bus");
        System.out.println("34. Update a bus stop");
        System.out.println("35. Update a driver");
        System.out.println("36. Update a bus company");
        System.out.println("37. Update a luggage");
        System.out.println("38. Update a passenger");
        System.out.println("39. Update a ticket");
        System.out.println("40. Update a feedback");
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
        commandDict.put(14, this::addBusStopUi);
        commandDict.put(15, this::addDriverUi);
        commandDict.put(16, this::addBusCompanyUi);
        commandDict.put(17, this::addLuggageUi);
        commandDict.put(18, this::addPassengerUi);
        commandDict.put(19, this::addTicketUi);
        commandDict.put(20, this::addFeedbackUi);

        commandDict.put(21, this::deleteCityUi);
        commandDict.put(22, this::deleteBusStationUi);
        commandDict.put(23, this::deleteBusUi);
        commandDict.put(24, this::deleteBusStopUi);
        commandDict.put(25, this::deleteDriverUi);
        commandDict.put(26, this::deleteBusCompanyUi);
        commandDict.put(27, this::deleteLuggageUi);
        commandDict.put(28, this::deletePassengerUi);
        commandDict.put(29, this::deleteTicketUi);
        commandDict.put(30, this::deleteFeedbackUi);

        commandDict.put(31, this::updateCityUi);
        commandDict.put(32, this::updateBusStationUi);
        commandDict.put(33, this::updateBusUi);
        commandDict.put(34, this::updateBusStopUi);
        commandDict.put(35, this::updateDriverUi);
        commandDict.put(36, this::updateBusCompanyUi);
        commandDict.put(37, this::updateLuggageUi);
        commandDict.put(38, this::updatePassengerUi);
        commandDict.put(39, this::updateTicketUi);
        commandDict.put(40, this::updateFeedbackUi);
        

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
