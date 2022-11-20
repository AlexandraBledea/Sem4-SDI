package common.encoders;

import common.domain.Bus;
import common.domain.BusStop;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class BusStopEncoder {

    public static JSONObject encodeBusStop(BusStop busStop){
        JSONObject obj = new JSONObject();

        obj.put("busid", busStop.getBusId());
        obj.put("stationid", busStop.getBusStationId());
        obj.put("stoptime", busStop.getStopTime().toString());

        return obj;
    }

    public static JSONArray encodeBusStops(Set<BusStop> stops){
        JSONArray arr = new JSONArray();

        stops.stream().map(BusStopEncoder::encodeBusStop)
                .forEach(arr::put);

        return arr;
    }

    public static BusStop decodeBusStop(JSONObject busStopJsonObject){
        Long busId = busStopJsonObject.getLong("busid");
        Long stationId = busStopJsonObject.getLong("stationid");
        String stopTime = busStopJsonObject.getString("stoptime");

        return new BusStop(busId, stationId, LocalTime.parse(stopTime));
    }

    public static Set<BusStop> decodeBusStops(JSONArray stopsJsonArray){
        Set<BusStop> stops = new HashSet<>();

        stopsJsonArray.toList().forEach((elem) -> {
            HashMap<String, Object> objectHashMap = (HashMap<String, Object>) elem;
            Long busId = Long.valueOf((Integer)objectHashMap.get("busid"));
            Long stationdId = Long.valueOf((Integer)objectHashMap.get("stationid"));
            String stopTime = (String)objectHashMap.get("stoptime");

            BusStop busStop = new BusStop(busId, stationdId, LocalTime.parse(stopTime));

            stops.add(busStop);
        });

        return stops;
    }
}
