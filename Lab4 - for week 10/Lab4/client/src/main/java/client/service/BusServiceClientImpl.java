package client.service;

import client.tcp.TcpClient;
import common.Message;
import common.domain.Bus;
import common.encoders.BusEncoder;
import common.exceptions.ConnectionException;
import common.service.IBusService;
import org.json.JSONArray;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Service
public class BusServiceClientImpl implements IBusService {

    private final TcpClient client;
    private final ExecutorService executorService;

    public BusServiceClientImpl(TcpClient client, ExecutorService executorService) {
        this.client = client;
        this.executorService = executorService;
    }

    @Override
    public Future<String> save(Bus bus) {
        Message request = new Message(IBusService.SAVE_ONE, BusEncoder.encodeBus(bus).toString());

        try {
            Message response = client.sendAndReceive(request);

            return executorService.submit(response::getHeader);
        }
        catch (ConnectionException ex){
            return null;
        }
    }

    @Override
    public Future<String> update(Bus bus) {
        Message request = new Message(IBusService.UPDATE_ONE, BusEncoder.encodeBus(bus).toString());

        try {
            Message response = client.sendAndReceive(request);

            return executorService.submit(response::getHeader);
        }
        catch (ConnectionException ex){
            return null;
        }
    }

    @Override
    public Future<String> delete(Long id) {
        Message request = new Message(IBusService.DELETE_ONE, id.toString());

        try {
            Message response = client.sendAndReceive(request);

            return executorService.submit(response::getHeader);
        }
        catch (ConnectionException ex){
            return null;
        }
    }

    @Override
    public Future<Set<Bus>> findAll() {
        Message request = new Message(IBusService.GET_ALL, "");
        try {
            Message response = client.sendAndReceive(request);
            String jsonText = response.getBody();

            Set<Bus> buses = BusEncoder.decodeBuses(new JSONArray(jsonText));

            return executorService.submit(() -> buses);
        } catch (ConnectionException ex){
            return null;
        }
    }
}
