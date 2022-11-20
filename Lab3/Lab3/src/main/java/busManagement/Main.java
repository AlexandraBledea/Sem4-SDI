package busManagement;

import busManagement.domain.*;
import busManagement.domain.validators.*;
import busManagement.repository.InMemoryRepository;
import busManagement.repository.Repository;
import busManagement.service.Service;
import busManagement.ui.Console;
import busManagement.utils.Pair;

public class Main {

    public static void main(String[] args) {

        Repository<Long, City> cityRepo = new InMemoryRepository<>(new CityValidator());
        Repository<Long, BusStation> busStationRepo = new InMemoryRepository<>(new BusStationValidator());
        Repository<Long, Bus> busRepo = new InMemoryRepository<>(new BusValidator());
        Repository<Pair<Long, Long>, BusStop> busStopRepo = new InMemoryRepository<>(new BusStopValidator());
        Repository<Long, Driver> driverRepo = new InMemoryRepository<>(new DriverValidator());
        Repository<Long, Luggage> luggageRepo = new InMemoryRepository<>(new LuggageValidator());
        Repository<Long, Passenger> passengerRepo = new InMemoryRepository<>(new PassengerValidator());
        Repository<Long, BusCompany> busCompanyRepo = new InMemoryRepository<>(new BusCompanyValidator());
        Repository<Pair<Long, Long>, Feedback> feedbackRepo = new InMemoryRepository<>(new FeedbackValidator());
        Repository<Long, Ticket> ticketRepo = new InMemoryRepository<>(new TicketValidator());

        Service service = new Service(cityRepo, busStationRepo, busRepo, busCompanyRepo, busStopRepo, luggageRepo,
                driverRepo, passengerRepo, feedbackRepo, ticketRepo);

        Console ui = new Console(service);

        ui.start();
    }
}
