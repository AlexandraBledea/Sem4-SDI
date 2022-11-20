package client.service;

import client.tcp.TcpClient;
import common.Message;
import common.domain.BusStop;
import common.encoders.BusStopEncoder;
import common.exceptions.ConnectionException;
import common.service.IBusStopService;
import common.utils.Pair;
import org.json.JSONArray;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Service
public class BusStopServiceClientImpl implements IBusStopService {
    private final TcpClient client;
    private final ExecutorService executorService;

    public BusStopServiceClientImpl(TcpClient client, ExecutorService executorService) {
        this.client = client;
        this.executorService = executorService;
    }

    @Override
    public Future<String> save(BusStop busStop) {
        Message request = new Message(IBusStopService.SAVE_ONE, BusStopEncoder.encodeBusStop(busStop).toString());

        try {
            Message response = client.sendAndReceive(request);

            return executorService.submit(response::getHeader);
        }
        catch (ConnectionException ex){
            return null;
        }
    }

    @Override
    public Future<String> update(BusStop busStop) {
        Message request = new Message(IBusStopService.UPDATE_ONE, BusStopEncoder.encodeBusStop(busStop).toString());

        try {
            Message response = client.sendAndReceive(request);

            return executorService.submit(response::getHeader);
        }
        catch (ConnectionException ex){
            return null;
        }
    }

    @Override
    public Future<String> delete(Pair<Long, Long> id) {
        Message request = new Message(IBusStopService.DELETE_ONE, id.toString());

        try {
            Message response = client.sendAndReceive(request);

            return executorService.submit(response::getHeader);
        }
        catch (ConnectionException ex){
            return null;
        }
    }

    @Override
    public Future<Set<BusStop>> findAll() {
        Message request = new Message(IBusStopService.GET_ALL, "");
        try {
            Message response = client.sendAndReceive(request);
            String jsonText = response.getBody();

            Set<BusStop> stops = BusStopEncoder.decodeBusStops(new JSONArray(jsonText));

            return executorService.submit(() -> stops);
        }
        catch (ConnectionException ex)
        {
            return null;
        }
    }
}
