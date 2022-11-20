package client.ui;

import common.domain.Bus;
import common.domain.BusStation;
import common.domain.BusStop;
import common.domain.City;
import common.service.IBusService;
import common.service.IBusStationService;
import common.service.IBusStopService;
import common.service.ICityService;
import common.utils.Pair;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Component
public class Console {
    private final IBusService busService;
    private final IBusStopService busStopService;
    private final IBusStationService busStationService;
    private final ICityService cityService;
    private final Map<Integer, Runnable> commandDict;


    public Console(IBusService busService, IBusStopService busStopService, IBusStationService busStationService, ICityService cityService, Map<Integer, Runnable> commandDict) {
        this.busService = busService;
        this.busStopService = busStopService;
        this.busStationService = busStationService;
        this.cityService = cityService;
        this.commandDict = commandDict;

        setUpMenu();
        printMenu();
    }

    private void setUpMenu(){
        commandDict.put(1, this::printAllBuses);
        commandDict.put(2, this::printAllBusStations);
        commandDict.put(3, this::printAllBusStops);
        commandDict.put(4, this::printAllCities);
        commandDict.put(5, this::addBus);
        commandDict.put(6, this::addBusStation);
        commandDict.put(7, this::addBusStop);
        commandDict.put(8, this::addCity);
        commandDict.put(9, this::updateBus);
        commandDict.put(10, this::updateBusStation);
        commandDict.put(11, this::updateBusStop);
        commandDict.put(12, this::updateCity);
        commandDict.put(13, this::deleteBus);
        commandDict.put(14, this::deleteBusStation);
        commandDict.put(15, this::deleteBusStop);
        commandDict.put(16, this::deleteCity);
        commandDict.put(17, this::printMenu);
    }

    private void printMenu() {
        System.out.println("1. Print all buses");
        System.out.println("2. Print all bus stations");
        System.out.println("3. Print all bus stops");
        System.out.println("4. Print all cities");
        System.out.println("5. Add a bus");
        System.out.println("6. Add a bus station");
        System.out.println("7. Add a bus stop");
        System.out.println("8. Add a city");
        System.out.println("9. Update a bus");
        System.out.println("10. Update a bus station");
        System.out.println("11. Update a bus stop");
        System.out.println("12. Update a city");
        System.out.println("13. Delete a bus");
        System.out.println("14. Delete a bus station");
        System.out.println("15. Delete a bus stop");
        System.out.println("16. Delete a city");
        System.out.println("17. Print menu");

    }
    private void printAllBuses() {
        try {
            Set<Bus> buses = busService.findAll().get();
            buses.forEach(System.out::println);
        } catch (InterruptedException | ExecutionException e){
            e.printStackTrace();
        }
    }

    private void printAllBusStations(){
        try {
            Set<BusStation> stations = busStationService.findAll().get();
            stations.forEach(System.out::println);
        } catch (InterruptedException | ExecutionException e){
            e.printStackTrace();
        }
    }

    private void printAllBusStops() {
        try {
            Set<BusStop> stops = busStopService.findAll().get();
            stops.forEach(System.out::println);
        } catch (InterruptedException | ExecutionException e){
            e.printStackTrace();
        }
    }

    private void printAllCities() {
        try {
            Set<City> cities =  cityService.findAll().get();
            cities.forEach(System.out::println);
        } catch (InterruptedException | ExecutionException e){
            e.printStackTrace();
        }
    }

    private void addBus(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("ID: ");
        Long busId = scanner.nextLong();
        scanner.nextLine();

        System.out.println("Model: ");
        String model = scanner.next();

        System.out.println("Fuel: " );
        String fuel = scanner.next();

        System.out.println("Capacity: ");
        int capacity = scanner.nextInt();

        Bus bus = new Bus(busId, model, fuel, capacity);
        busService.save(bus);
    }

    private void addBusStation(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Station ID: ");
        Long stationId = scanner.nextLong();
        scanner.nextLine();

        System.out.println("City ID: ");
        Long cityId = scanner.nextLong();
        scanner.nextLine();

        System.out.println("Name of the station: ");
        String name = scanner.next();

        BusStation busStation = new BusStation(stationId, cityId, name);
        busStationService.save(busStation);
    }

    private void addBusStop(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bus ID: ");
        Long busId = scanner.nextLong();
        scanner.nextLine();

        System.out.println("Station ID: ");
        Long stationId = scanner.nextLong();
        scanner.nextLine();

        System.out.println("Stop time: ");
        String time = scanner.next();

        BusStop busStop = new BusStop(busId, stationId, LocalTime.parse(time));
        busStopService.save(busStop);
    }

    private void addCity(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("ID: ");
        Long cityId = scanner.nextLong();
        scanner.nextLine();

        System.out.println("Name : ");
        String name = scanner.next();

        System.out.println("Population: ");
        int population = scanner.nextInt();

        City city = new City(cityId, name, population);
        cityService.save(city);
    }

    private void updateBus(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("ID: ");
        Long busId = scanner.nextLong();
        scanner.nextLine();

        System.out.println("Model: ");
        String model = scanner.next();

        System.out.println("Fuel: " );
        String fuel = scanner.next();

        System.out.println("Capacity: ");
        int capacity = scanner.nextInt();

        Bus bus = new Bus(busId, model, fuel, capacity);
        busService.update(bus);
    }

    private void updateBusStation(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Station ID: ");
        Long stationId = scanner.nextLong();
        scanner.nextLine();

        System.out.println("City ID: ");
        Long cityId = scanner.nextLong();
        scanner.nextLine();

        System.out.println("Name of the station: ");
        String name = scanner.next();

        BusStation busStation = new BusStation(stationId, cityId, name);
        busStationService.update(busStation);
    }

    private void updateBusStop(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bus ID: ");
        Long busId = scanner.nextLong();
        scanner.nextLine();

        System.out.println("Station ID: ");
        Long stationId = scanner.nextLong();
        scanner.nextLine();

        System.out.println("Stop time: ");
        String time = scanner.next();

        BusStop busStop = new BusStop(busId, stationId, LocalTime.parse(time));
        busStopService.update(busStop);
    }

    private void updateCity(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("ID: ");
        Long cityId = scanner.nextLong();
        scanner.nextLine();

        System.out.println("Name : ");
        String name = scanner.next();

        System.out.println("Population: ");
        int population = scanner.nextInt();

        City city = new City(cityId, name, population);
        cityService.update(city);
    }

    private void deleteBus(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("ID: ");
        Long busId = scanner.nextLong();

        busService.delete(busId);
    }

    private void deleteBusStation(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("ID: ");
        Long stationId = scanner.nextLong();

        busStationService.delete(stationId);
    }

    private void deleteBusStop(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bus ID: ");
        Long busId = scanner.nextLong();
        scanner.nextLine();

        System.out.println("Station ID: ");
        Long stationId = scanner.nextLong();
        scanner.nextLine();

        busStopService.delete(new Pair<>(busId, stationId));
    }

    private void deleteCity(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("ID: ");
        Long cityId = scanner.nextLong();

        cityService.delete(cityId);
    }

    private static int readNumberFromConsole() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    public void start() {
        IntStream.generate(() -> 0)
                .forEach(i -> {
                    try {
                        int choice = readNumberFromConsole();
                        Stream.of(commandDict.get(choice))
                                .filter(Objects::nonNull)
                                .findAny()
                                .ifPresentOrElse(Runnable::run, () -> System.out.println("Bad choice"));
                    } catch (InputMismatchException inputMismatchException){
                        System.out.println("Invalid integer");
                    } catch (Exception exception){
                        System.out.println(exception.getMessage());
                    }
                });
    }
}
