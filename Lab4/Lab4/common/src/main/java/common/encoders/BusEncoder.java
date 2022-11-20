package common.encoders;

import common.domain.Bus;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class BusEncoder {
    public static JSONObject encodeBus(Bus bus){
    JSONObject obj = new JSONObject();

    obj.put("busid", bus.getId());
    obj.put("modelname", bus.getModelName());
    obj.put("fuel", bus.getFuel());
    obj.put("capacity", bus.getCapacity());

    return obj;
    }

    public static JSONArray encodeBuses(Set<Bus> buses){
        JSONArray arr = new JSONArray();

        buses.stream().map(BusEncoder::encodeBus)
                .forEach(arr::put);

        return arr;
    }

    public static Bus decodeBus(JSONObject busJsonObject){
        Long busId = busJsonObject.getLong("busid");
        String busModelName = busJsonObject.getString("modelname");
        String busFuel = busJsonObject.getString("fuel");
        int busCapacity = busJsonObject.getInt("capacity");

        return new Bus(busId, busModelName, busFuel, busCapacity);
    }

    @SuppressWarnings("unchecked")
    public static Set<Bus> decodeBuses(JSONArray busJsonArray){
        Set<Bus> buses = new HashSet<>();

        busJsonArray.toList().forEach((elem) -> {
            HashMap<String, Object> objectHashMap = (HashMap<String, Object>) elem;
            Long busId = Long.valueOf((Integer) objectHashMap.get("busid"));
            String busModelName = (String) objectHashMap.get("modelname");
            String busFuel = (String) objectHashMap.get("fuel");
            int busCapacity = (Integer) objectHashMap.get("capacity");

            Bus bus = new Bus(busId, busModelName, busFuel, busCapacity);

            buses.add(bus);
        });

        return buses;
    }
}
