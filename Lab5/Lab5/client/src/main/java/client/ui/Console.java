package client.ui;

import client.ui.controller.async.*;
import core.domain.Bus;
import core.domain.BusStation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Component
public class Console {

    public static final Logger logger = LoggerFactory.getLogger(Console.class);
    private final AsyncBusController busController;
    private final AsyncBusStopController busStopController;
    private final AsyncBusStationController busStationController;
    private final AsyncCityController cityController;
    private final AsyncJoinController joinController;
    private final Map<Integer, Runnable> commandDict;


    public Console(AsyncJoinController joinController, AsyncBusController busController, AsyncBusStopController busStopController, AsyncBusStationController busStationController, AsyncCityController cityController, Map<Integer, Runnable> commandDict) {
        this.busController = busController;
        this.busStopController = busStopController;
        this.busStationController = busStationController;
        this.cityController = cityController;
        this.joinController = joinController;
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
        commandDict.put(18, this::printFilteredBusStations);
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
        System.out.println("18. Filter bus stations by the population of the city they exist into");

    }

    private void printFilteredBusStations(){
        logger.trace("show filtered bus stations - method entered");
        Scanner stdin = new Scanner(System.in);

        System.out.println("Population lower than: ");
        int population = stdin.nextInt();

        this.joinController.filterBusStationsByPopulation(population).whenComplete((status, exception) -> {
            if (exception == null){
                status.forEach(System.out::println);
            }
            else {
                System.out.println(exception.getMessage());
            }
        });

        logger.trace("show filtered bus stations - method finished");

    }

    static void writeConsole(String message){
        System.out.println(message);
    }

    private void printAllBuses() {
        logger.trace("show buses - method entered");
        this.busController.findAll().whenComplete((result, exception) -> {
            if(exception == null)
                StreamSupport.stream(result.spliterator(), false).forEach(System.out::println);
            else
                System.out.println(exception.toString());
        });
        logger.trace("show buses - method finished");
    }

    private void printAllBusStations(){
        logger.trace("show stations - method entered");
        this.busStationController.findAll().whenComplete((result, exception) -> {
            if(exception == null)
                StreamSupport.stream(result.spliterator(), false).forEach(System.out::println);
            else
                System.out.println(exception.toString());
        });
        logger.trace("show stations - method finished");
    }

    private void printAllBusStops() {
        logger.trace("show stops - method entered");
        this.busStopController.findAll().whenComplete((result, exception) -> {
            if(exception == null)
                StreamSupport.stream(result.spliterator(), false).forEach(System.out::println);
            else
                System.out.println(exception.toString());
        });
        logger.trace("show stops - method finished");
    }

    private void printAllCities() {
        logger.trace("show cities - method entered");
        this.cityController.findAll().whenComplete((result, exception) -> {
            if(exception == null)
                StreamSupport.stream(result.spliterator(), false).forEach(System.out::println);
            else
                System.out.println(exception.toString());
        });
        logger.trace("show cities - method finished");
    }

    private void addBus(){
        logger.trace("addBus - method entered");
        Scanner scanner = new Scanner(System.in);

        System.out.println("Model: ");
        String model = scanner.next();

        System.out.println("Fuel: " );
        String fuel = scanner.next();

        System.out.println("Capacity: ");
        int capacity = scanner.nextInt();


        busController.save(model, fuel, capacity).whenComplete((status, exception) -> {
            if(exception == null){
                System.out.println(status);
            }
            else {
                System.out.println(exception.getMessage());
            }
        });

        logger.trace("addBus - method finished");
    }

    private void addBusStation(){
        logger.trace("addBusStation - method entered");
        Scanner scanner = new Scanner(System.in);

        System.out.println("City ID: ");
        Long cityId = scanner.nextLong();
        scanner.nextLine();

        System.out.println("Name of the station: ");
        String name = scanner.next();

        busStationController.save(name, cityId).whenComplete((status, exception) -> {
            if(exception == null){
                System.out.println(status);
            }
            else {
                System.out.println(exception.getMessage());
            }
        });

        logger.trace("addBusStation - method finished");
    }

    private void addBusStop(){
        logger.trace("addBusStop - method entered");

        Scanner scanner = new Scanner(System.in);

        System.out.println("Bus ID: ");
        Long busId = scanner.nextLong();
        scanner.nextLine();

        System.out.println("Station ID: ");
        Long stationId = scanner.nextLong();
        scanner.nextLine();

        System.out.println("Stop time: ");
        String time = scanner.next();

        busStopController.save(busId, stationId, time).whenComplete((status, exception) -> {
            if(exception == null){
                System.out.println(status);
            }
            else {
                System.out.println(exception.getMessage());
            }
        });

        logger.trace("addBusStop - method finished");

    }

    private void addCity(){
        logger.trace("addCity - method entered");

        Scanner scanner = new Scanner(System.in);

        System.out.println("Name : ");
        String name = scanner.next();

        System.out.println("Population: ");
        int population = scanner.nextInt();

        cityController.save(name, population).whenComplete((status, exception) -> {
            if(exception == null){
                System.out.println(status);
            }
            else {
                System.out.println(exception.getMessage());
            }
        });

        logger.trace("addCity - method finished");
    }

    private void updateBus(){
        logger.trace("updateBus - method entered");

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

        busController.update(busId, model, fuel, capacity).whenComplete((status, exception) -> {
            if(exception == null)
                System.out.println(status);
            else
                System.out.println(exception.getMessage());
        });

        logger.trace("updateBus - method finished");
    }

    private void updateBusStation(){
        logger.trace("updateBusStation - method entered");

        Scanner scanner = new Scanner(System.in);

        System.out.println("Station ID: ");
        Long stationId = scanner.nextLong();
        scanner.nextLine();

        System.out.println("Name of the station: ");
        String name = scanner.next();

        busStationController.update(stationId, name).whenComplete((status, exception) -> {
            if(exception == null)
                System.out.println(status);
            else
                System.out.println(exception.getMessage());
        });

        logger.trace("updateBusStation - method finished");
    }

    private void updateBusStop(){
        logger.trace("updateBusStop - method entered");

        Scanner scanner = new Scanner(System.in);

        System.out.println("ID: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        System.out.println("Stop time: ");
        String time = scanner.next();

        busStopController.update(id, time).whenComplete((status, exception) -> {
            if(exception == null)
                System.out.println(status);
            else
                System.out.println(exception.getMessage());
        });

        logger.trace("updateBusStop - method finished");
    }

    private void updateCity(){
        logger.trace("updateCity - method entered");
        Scanner scanner = new Scanner(System.in);

        System.out.println("ID: ");
        Long cityId = scanner.nextLong();
        scanner.nextLine();

        System.out.println("Name : ");
        String name = scanner.next();

        System.out.println("Population: ");
        int population = scanner.nextInt();

        cityController.update(cityId, name, population).whenComplete((status, exception) -> {
            if(exception == null)
                System.out.println(status);
            else
                System.out.println(exception.getMessage());
        });
        logger.trace("updateCity - method finished");
    }

    private void deleteBus(){
        logger.trace("deleteBus - method entered");

        Scanner scanner = new Scanner(System.in);
        System.out.println("ID: ");
        Long busId = scanner.nextLong();

        busController.delete(busId).whenComplete((status, exception)-> {
            if(exception == null)
                System.out.println(status);
            else
                System.out.println(exception.getMessage());
        });

        logger.trace("deleteBus - method finished");
    }

    private void deleteBusStation(){
        logger.trace("deleteBusStation - method entered");

        Scanner scanner = new Scanner(System.in);
        System.out.println("ID: ");
        Long stationId = scanner.nextLong();

        busStationController.delete(stationId).whenComplete((status, exception)-> {
            if(exception == null)
                System.out.println(status);
            else
                System.out.println(exception.getMessage());
        });

        logger.trace("deleteBusStation - method finished");
    }

    private void deleteBusStop(){
        logger.trace("deleteBusStop - method entered");

        Scanner scanner = new Scanner(System.in);

        System.out.println("ID: ");
        Long id = scanner.nextLong();
        scanner.nextLine();


        busStopController.delete(id).whenComplete((status, exception)-> {
            if(exception == null)
                System.out.println(status);
            else
                System.out.println(exception.getMessage());
        });

        logger.trace("deleteBusStop - method finished");
    }

    private void deleteCity(){
        logger.trace("deleteCity - method entered");

        Scanner scanner = new Scanner(System.in);
        System.out.println("ID: ");
        Long cityId = scanner.nextLong();

        cityController.delete(cityId).whenComplete((status, exception)-> {
            if(exception == null)
                System.out.println(status);
            else
                System.out.println(exception.getMessage());
        });

        logger.trace("deleteCity - method finished");
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
