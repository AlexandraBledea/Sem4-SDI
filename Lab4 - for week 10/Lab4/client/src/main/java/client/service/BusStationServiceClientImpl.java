package client.service;

import client.tcp.TcpClient;
import common.Message;
import common.domain.BusStation;
import common.encoders.BusStationEncoder;
import common.exceptions.ConnectionException;
import common.service.IBusStationService;
import org.json.JSONArray;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Service
public class BusStationServiceClientImpl implements IBusStationService {

    private final TcpClient client;
    private final ExecutorService executorService;

    public BusStationServiceClientImpl(TcpClient client, ExecutorService executorService) {
        this.client = client;
        this.executorService = executorService;
    }

    @Override
    public Future<String> save(BusStation busStation) {
        Message message = new Message(IBusStationService.SAVE_ONE, BusStationEncoder.encodeBusStation(busStation).toString());
        try {
            Message response = client.sendAndReceive(message);

            return executorService.submit(response::getHeader);
        }
        catch (ConnectionException ex){
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Future<String> update(BusStation busStation) {
        Message message = new Message(IBusStationService.UPDATE_ONE, BusStationEncoder.encodeBusStation(busStation).toString());
        try {
            Message response = client.sendAndReceive(message);

            return executorService.submit(response::getHeader);
        }
        catch (ConnectionException ex){
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Future<String> delete(Long id) {
        Message message = new Message(IBusStationService.DELETE_ONE, id.toString());
        try {
            Message response = client.sendAndReceive(message);

            return executorService.submit(response::getHeader);
        }
        catch (ConnectionException ex){
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Future<Set<BusStation>> findAll() {
        Message message = new Message(IBusStationService.GET_ALL, "");

        try{
            Message response = client.sendAndReceive(message);
            String jsonText = response.getBody();
            Set<BusStation> stations = BusStationEncoder.decodeBusStations(new JSONArray(jsonText));

            return executorService.submit(() -> stations);
        }
        catch (ConnectionException ex){
            return null;
        }
    }
}
