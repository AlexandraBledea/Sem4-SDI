package client.service;

import client.tcp.TcpClient;
import common.Message;
import common.domain.City;
import common.encoders.CityEncoder;
import common.exceptions.ConnectionException;
import common.service.ICityService;
import org.json.JSONArray;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Service
public class CityServiceClientImpl implements ICityService {

    private final TcpClient client;
    private final ExecutorService executorService;

    public CityServiceClientImpl(TcpClient client, ExecutorService executorService) {
        this.client = client;
        this.executorService = executorService;
    }


    @Override
    public Future<String> save(City city) {
        Message request = new Message(ICityService.SAVE_ONE, CityEncoder.encodeCity(city).toString());
        try {
            Message response = client.sendAndReceive(request);

            return executorService.submit(request::getHeader);
        }
        catch (ConnectionException ex){
            return null;
        }
    }

    @Override
    public Future<String> update(City city) {
        Message request = new Message(ICityService.UPDATE_ONE, CityEncoder.encodeCity(city).toString());
        try {
            Message response = client.sendAndReceive(request);

            return executorService.submit(request::getHeader);
        }
        catch (ConnectionException ex){
            return null;
        }
    }

    @Override
    public Future<String> delete(Long id) {
        Message request = new Message(ICityService.DELETE_ONE, id.toString());
        try {
            Message response = client.sendAndReceive(request);

            return executorService.submit(request::getHeader);
        }
        catch (ConnectionException ex){
            return null;
        }
    }

    @Override
    public Future<Set<City>> findAll() {
        Message request = new Message(ICityService.GET_ALL, "");
        try {
            Message response = client.sendAndReceive(request);
            String jsonText = response.getBody();

            Set<City> cities = CityEncoder.decodeCities(new JSONArray(jsonText));

            return executorService.submit(() -> cities);
        } catch (ConnectionException ex){
            return null;
        }
    }
}
