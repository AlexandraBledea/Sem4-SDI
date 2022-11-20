package server.tcp;

import common.Message;
import common.domain.Bus;
import common.domain.BusStation;
import common.domain.BusStop;
import common.domain.City;
import common.encoders.BusEncoder;
import common.encoders.BusStationEncoder;
import common.encoders.BusStopEncoder;
import common.encoders.CityEncoder;
import common.exceptions.BusManagementException;
import common.exceptions.ValidatorException;
import common.service.IBusService;
import common.service.IBusStationService;
import common.service.IBusStopService;
import common.service.ICityService;
import common.utils.Pair;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import server.service.BusServiceServerImpl;
import server.service.BusStationServiceServerImpl;
import server.service.BusStopServiceServerImpl;
import server.service.CityServiceServerImpl;
import server.tcp.TcpServerImpl;
import common.Constants;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

@Component
public class ServerApp extends TcpServerImpl {
    private final IBusService busService;
    private final ICityService cityService;
    private final IBusStationService busStationService;
    private final IBusStopService busStopService;

    public ServerApp(ExecutorService executorService, IBusService busService, ICityService cityService, IBusStationService busStationService, IBusStopService busStopService) {
        super(executorService, Constants.PORT);
        this.busService = busService;
        this.cityService = cityService;
        this.busStationService = busStationService;
        this.busStopService = busStopService;

        addAllHandlers();
    }

    private void addAllHandlers(){

        addBusGetAllHandler();
        addBusSaveHandler();
        addBusUpdateHandler();
        addBusDeleteHandler();

        addBusStationGetAllHandler();
        addBusStationSaveHandler();
        addBusStationUpdateHandler();
        addBusStationDeleteHandler();

        addBusStopGetAllHandler();
        addBusStopSaveHandler();
        addBusStopUpdateHandler();
        addBusStopDeleteHandler();

        addCityGetAllHandler();
        addCitySaveHandler();
        addCityUpdateHandler();
        addCityDeleteHandler();

    }

    private void addBusGetAllHandler(){
        this.addHandler(IBusService.GET_ALL, request -> {
            try {
                return new Message(Message.OK,
                        BusEncoder.encodeBuses(busService.findAll().get()).toString());
            } catch (ExecutionException | InterruptedException e){
                e.printStackTrace();
                return new Message(Message.ERROR, e.getMessage());
            }
        });
    }

    private void addBusSaveHandler(){
        this.addHandler(IBusService.SAVE_ONE, request -> {
            try {
                Bus bus = BusEncoder.decodeBus(new JSONObject(request.getBody()));
                busService.save(bus).get();
                return new Message(Message.OK, "Bus added successfully!");
            } catch (ExecutionException | InterruptedException e){
                e.printStackTrace();
                return new Message(Message.ERROR, e.getMessage());
            }
        });
    }

    private void addBusUpdateHandler(){
        this.addHandler(IBusService.UPDATE_ONE, request -> {
            try {
                Bus bus = BusEncoder.decodeBus(new JSONObject(request.getBody()));
                busService.update(bus).get();
                return new Message(Message.OK, "Bus updated successfully!");
            } catch (ExecutionException | InterruptedException e){
                e.printStackTrace();
                return new Message(Message.ERROR, e.getMessage());
            }
        });
    }

    private void addBusDeleteHandler(){
        this.addHandler(IBusService.DELETE_ONE, request -> {
            try {
                Long id = Long.parseLong(request.getBody());
                busService.delete(id).get();
                return new Message(Message.OK, "Bus removed successfully!");
            } catch (ExecutionException | InterruptedException e){
                e.printStackTrace();
                return new Message(Message.ERROR, e.getMessage());
            }
        });
    }

    private void addBusStationGetAllHandler(){
        this.addHandler(IBusStationService.GET_ALL, request -> {
            try {
                return new Message(Message.OK,
                        BusStationEncoder.encodeBusStations(busStationService.findAll().get()).toString());
            } catch (ExecutionException | InterruptedException e){
                e.printStackTrace();
                return new Message(Message.ERROR, e.getMessage());
            }
        });
    }

    private void addBusStationSaveHandler(){
        this.addHandler(IBusStationService.SAVE_ONE, request -> {
            try {
                BusStation station = BusStationEncoder.decodeBusStation(new JSONObject(request.getBody()));
                busStationService.save(station).get();
                return new Message(Message.OK, "Bus station added successfully!");
            } catch (ExecutionException | InterruptedException e){
                e.printStackTrace();
                return new Message(Message.ERROR, e.getMessage());
            }
        });
    }

    private void addBusStationUpdateHandler(){
        this.addHandler(IBusStationService.UPDATE_ONE, request -> {
            try {
                BusStation station = BusStationEncoder.decodeBusStation(new JSONObject(request.getBody()));
                busStationService.update(station).get();
                return new Message(Message.OK, "Bus station updated successfully!");
            } catch (ExecutionException | InterruptedException e){
                e.printStackTrace();
                return new Message(Message.ERROR, e.getMessage());
            }
        });
    }

    private void addBusStationDeleteHandler() {
        this.addHandler(IBusStationService.DELETE_ONE, request -> {
            try {
                Long id = Long.parseLong(request.getBody());
                busStationService.delete(id).get();
                return new Message(Message.OK, "Bus station removed successfully!");
            } catch (ExecutionException | InterruptedException e){
                e.printStackTrace();
                return new Message(Message.ERROR, e.getMessage());
            }

        });
    }

    private void addBusStopGetAllHandler(){
        this.addHandler(IBusStopService.GET_ALL, request -> {
            try {
                return new Message(Message.OK, BusStopEncoder.encodeBusStops(busStopService.findAll().get()).toString());
            } catch (ExecutionException | InterruptedException e){
                e.printStackTrace();
                return new Message(Message.ERROR, e.getMessage());
            }
        });
    }

    private void addBusStopSaveHandler(){
        this.addHandler(IBusStopService.SAVE_ONE, request -> {
            try {
                BusStop stop = BusStopEncoder.decodeBusStop(new JSONObject(request.getBody()));
                busStopService.save(stop).get();
                return new Message(Message.OK, "Stop added successfully!");
            } catch (ExecutionException | InterruptedException e){
                e.printStackTrace();
                return new Message(Message.ERROR, e.getMessage());
            }
        });
    }

    private void addBusStopUpdateHandler(){
        this.addHandler(IBusStopService.UPDATE_ONE, request -> {
            try{
                BusStop stop = BusStopEncoder.decodeBusStop(new JSONObject(request.getBody()));
                busStopService.update(stop).get();
                return new Message(Message.OK, "Stop updated successfully!");
            } catch (ExecutionException | InterruptedException e){
                e.printStackTrace();
                return new Message(Message.ERROR, e.getMessage());
            }
        });
    }

    private void addBusStopDeleteHandler(){
        this.addHandler(IBusStopService.DELETE_ONE, request -> {
            try {
                String pairString = request.getBody();
                String[] ids = pairString.split(",");
                Long busId = Long.parseLong(ids[0].replace("[", ""));
                Long stationId = Long.parseLong(ids[1].replace("]", ""));
                Pair<Long, Long> id = new Pair<>(busId, stationId);
                busStopService.delete(id).get();
                return new Message(Message.OK, "Stop removed successfully!");
            } catch (ExecutionException | InterruptedException e){
                e.printStackTrace();
                return new Message(Message.ERROR, e.getMessage());
            }
        });
    }

    private void addCityGetAllHandler(){
        this.addHandler(ICityService.GET_ALL, request -> {
            try{
                return new Message(Message.OK,
                        CityEncoder.encodeCities(cityService.findAll().get()).toString());
            } catch (ExecutionException | InterruptedException e){
                e.printStackTrace();
                return new Message(Message.ERROR, e.getMessage());
            }
        });
    }

    private void addCitySaveHandler(){
        this.addHandler(ICityService.SAVE_ONE, request -> {
            try{
                City city = CityEncoder.decodeCity(new JSONObject(request.getBody()));
                cityService.save(city).get();
                return new Message(Message.OK, "City added successfully!");
            } catch (ExecutionException | InterruptedException e){
                e.printStackTrace();
                return new Message(Message.ERROR, e.getMessage());
            }
        });
    }

    private void addCityUpdateHandler(){
        this.addHandler(ICityService.UPDATE_ONE, request -> {
            try {
                City city = CityEncoder.decodeCity(new JSONObject(request.getBody()));
                cityService.update(city).get();
                return new Message(Message.OK, "City updated successfully!");
            } catch (ExecutionException | InterruptedException e){
                e.printStackTrace();
                return new Message(Message.ERROR, e.getMessage());
            }

        });
    }

    private void addCityDeleteHandler(){
        this.addHandler(ICityService.DELETE_ONE, request -> {
            try {
                Long id = Long.parseLong(request.getBody());
                cityService.delete(id).get();
                return new Message(Message.OK, "City removed successfully!");
            } catch (ExecutionException | InterruptedException e){
                e.printStackTrace();
                return new Message(Message.ERROR, e.getMessage());
            }
        });
    }

}
