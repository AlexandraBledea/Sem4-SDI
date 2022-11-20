package common.encoders;

import common.domain.City;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class CityEncoder {

    public static JSONObject encodeCity(City city){
        JSONObject obj = new JSONObject();

        obj.put("cityid", city.getId());
        obj.put("name", city.getName());
        obj.put("population", city.getPopulation());

        return obj;
    }

    public static JSONArray encodeCities(Set<City> cities){
        JSONArray arr = new JSONArray();

        cities.stream().map(CityEncoder::encodeCity)
                .forEach(arr::put);

        return arr;
    }

    public static City decodeCity(JSONObject cityJsonObject){
        Long cityId = cityJsonObject.getLong("cityid");
        String name = cityJsonObject.getString("name");
        int population = cityJsonObject.getInt("population");

        City city = new City(cityId, name, population);

        return city;
    }

    public static Set<City> decodeCities(JSONArray citiesJsonArray){
        Set<City> cities = new HashSet<>();

        citiesJsonArray.toList().forEach((elem) -> {
            HashMap<String, Object> objectHashMap = (HashMap<String, Object>) elem;
            Long cityId = Long.valueOf((Integer)objectHashMap.get("cityid"));
            String name = (String) objectHashMap.get("name");
            int population = (Integer)objectHashMap.get("population");

            City city = new City(cityId, name, population);

            cities.add(city);
        });

        return cities;
    }
}
