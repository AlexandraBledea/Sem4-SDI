package common.encoders;

import common.domain.Bus;
import common.domain.BusStation;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class BusStationEncoder {

    public static JSONObject encodeBusStation(BusStation busStation){
        JSONObject obj = new JSONObject();

        obj.put("stationid", busStation.getId());
        obj.put("cityid", busStation.getCityId());
        obj.put("name", busStation.getName());

        return obj;
    }

    public static JSONArray encodeBusStations(Set<BusStation> stations){
        JSONArray arr = new JSONArray();

        stations.stream().map(BusStationEncoder::encodeBusStation)
                .forEach(arr::put);

        return arr;
    }

    public static BusStation decodeBusStation(JSONObject busStationJsonObject){
        Long stationId = busStationJsonObject.getLong("stationid");
        Long cityid = busStationJsonObject.getLong("cityid");
        String name = busStationJsonObject.getString("name");

        return new BusStation(stationId, cityid, name);
    }

    @SuppressWarnings("unchecked")
    public static Set<BusStation> decodeBusStations(JSONArray stationsJsonArray){
        Set<BusStation> stations = new HashSet<>();

        stationsJsonArray.toList().forEach((elem) -> {
            HashMap<String, Object> objectHashMap = (HashMap<String, Object>) elem;
            Long stationId = Long.valueOf((Integer) objectHashMap.get("stationid"));
            Long cityId = Long. valueOf((Integer) objectHashMap.get("cityid"));
            String name = (String) objectHashMap.get("name");

            BusStation busStation = new BusStation(stationId, cityId, name);

            stations.add(busStation);
        });

        return stations;
    }
}
