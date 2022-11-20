package ro.ubb.catalog.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.junit.jupiter.api.Test;


import java.lang.reflect.Array;
import org.junit.Ignore;
import org.mockito.ArgumentCaptor;
import ro.ubb.catalog.core.converter.BusConverter;
import ro.ubb.catalog.core.converter.BusStationConverter;
import ro.ubb.catalog.core.converter.BusStopConverter;
import ro.ubb.catalog.core.converter.CityConverter;
import ro.ubb.catalog.core.dto.*;
import ro.ubb.catalog.core.model.*;
import ro.ubb.catalog.core.service.IBusService;
import ro.ubb.catalog.core.service.ICityService;


import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.startsWith;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CityControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private CityController cityController;

    @Mock
    private ICityService cityService;

    @Mock
    private CityConverter cityConverter;

    @Mock
    private BusStationConverter stationConverter;

    @Mock
    private BusStopConverter stopConverter;


    private Bus bus;
    private Driver driver;
    private City city;
    private BusStation station;
    private BusStop stop;

    private CityDTO cityDTO;
    private BusStopDTO stopDTO;
    private BusStationDTO stationDTO;

    @Before
    public void setup() throws Exception {
        initMocks(this);

        this.mockMvc = MockMvcBuilders
                .standaloneSetup(cityController)
                .build();

        initData();
    }

    @Test
    public void findAll() throws Exception{
        List<City> cities = Arrays.asList(city);
        List<CityDTO> cityDTOS = Arrays.asList(cityDTO);

        // configure mock objects to return test data when a method is invoked
        when(cityService.findAll()).thenReturn(cities);
        when(cityConverter.convertModelsToDTOsList(cities)).thenReturn(cityDTOS);

        // invoke the HTTP get request
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/cities"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.cities", hasSize(1)))
                .andExpect(jsonPath("$.cities[0].name", is("n1")))
                .andExpect(jsonPath("$.cities[0].population", is(1)));

        String result = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println(result);

        verify(cityService, times(1)).findAll();
        verify(cityConverter, times(1)).convertModelsToDTOsList(cities);
        verifyNoMoreInteractions(cityService, cityConverter);
    }

    @Test
    public void getCity() throws Exception{
        when(cityService.findCityByName("n1")).thenReturn(city);
        when(cityConverter.convertModelToDto(any(City.class))).thenReturn(cityDTO);

        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/cities/getCity/" + city.getName()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.name", is("n1")))
                .andExpect(jsonPath("$.population", is(1)));

        String result = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println(result);

        verify(cityService, times(1)).findCityByName("n1");
        verify(cityConverter, times(1)).convertModelToDto(any(City.class));
        verifyNoMoreInteractions(cityService, cityConverter);
    }

    @Test
    public void findAllStopsForCity() throws Exception{
        Set<BusStop> stops=  new HashSet<>(Arrays.asList(stop));
        Set<BusStopDTO> stopDTOS = new HashSet<>(Arrays.asList(stopDTO));

        when(cityService.findAllStopsForCity(1L, 1L)).thenReturn(stops);
        when(stopConverter.convertModelsToDTOs(stops)).thenReturn(stopDTOS);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/cities/" + city.getId() + "/" + station.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.stops", hasSize(1)))
                .andExpect(jsonPath("$.stops[0].stopTime", is("s1")));

        String result = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println(result);

        verify(cityService, times(1)).findAllStopsForCity(1L, 1L);
        verify(stopConverter, times(1)).convertModelsToDTOs(stops);
        verifyNoMoreInteractions(cityService, stopConverter);
    }

    @Test
    public void findAllStationsForCity() throws Exception{
        Set<BusStation> stations=  new HashSet<>(Arrays.asList(station));
        Set<BusStationDTO> stationDTOS = new HashSet<>(Arrays.asList(stationDTO));

        when(cityService.findAllStationsForCity(1L)).thenReturn(stations);
        when(stationConverter.convertModelsToDTOs(stations)).thenReturn(stationDTOS);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/cities/" + city.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.stations", hasSize(1)))
                .andExpect(jsonPath("$.stations[0].name", is("n1")));

        String result = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println(result);

        verify(cityService, times(1)).findAllStationsForCity(1L);
        verify(stationConverter, times(1)).convertModelsToDTOs(stations);
        verifyNoMoreInteractions(cityService, stopConverter);
    }

    @Test
    public void save() throws Exception{
        when(cityConverter.convertDtoToModel(any(CityDTO.class))).thenReturn(city);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/cities", cityDTO)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(toJsonString(cityDTO)))
                .andExpect(status().isOk());

        verify(cityService, times(1)).save(city);
        verify(cityConverter, times(1)).convertDtoToModel(any(CityDTO.class));
        verifyNoMoreInteractions(cityService, cityConverter);
    }

    @Test
    public void update() throws Exception{
        when(cityConverter.convertDtoToModel(any(CityDTO.class))).thenReturn(city);
        mockMvc.perform(MockMvcRequestBuilders
                .put("/cities/" + cityDTO.getId(), cityDTO)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(toJsonString(cityDTO)))
                .andExpect(status().isOk());
        verify(cityService, times(1)).update(cityDTO.getId(), cityDTO.getName(), cityDTO.getPopulation());
        verify(cityConverter, times(1)).convertDtoToModel(any(CityDTO.class));
        verifyNoMoreInteractions(cityService, cityConverter);
    }

    @Test
    public void delete() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/cities/" + city.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
        verify(cityService, times(1)).delete(city.getId());
        verifyNoMoreInteractions(cityService, cityConverter);
    }

    private String toJsonString(CityDTO cityDTO) {
        try {
            return new ObjectMapper().writeValueAsString(cityDTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void initData(){

        driver = Driver.builder()
                .name("n1")
                .cnp("c1")
                .build();
        driver.setId(1L);

        bus = Bus.builder()
                .modelName("n1")
                .fuel("f1")
                .capacity(1)
                .driver(driver)
                .build();
        bus.setId(1L);

        city = City.builder()
                .name("n1")
                .population(1)
                .build();
        city.setId(1L);

        station = BusStation.builder()
                .name("n1")
                .city(city)
                .build();

        station.setId(1L);

        stop = BusStop.builder()
                .bus(bus)
                .station(station)
                .stopTime("s1")
                .build();

        cityDTO = createCityDTO(city);
        stationDTO = createBusStationDTO(station);
        stopDTO = createBusStopDTO(stop);

    }

    private DriverDTO createDriverDTO(Driver driver){
        DriverDTO driverDTO = DriverDTO.builder()
                .name(driver.getName())
                .cnp(driver.getCnp())
                .build();
        driverDTO.setId(driver.getId());

        return driverDTO;
    }

    private BusDTO createBusDTO(Bus bus){
        DriverDTO driverDTO = createDriverDTO(bus.getDriver());

        BusDTO busDTO = BusDTO.builder()
                .modelName(bus.getModelName())
                .fuel(bus.getFuel())
                .capacity(bus.getCapacity())
                .driver(driverDTO)
                .build();
        busDTO.setId(bus.getId());

        return busDTO;
    }

    private CityDTO createCityDTO(City city){
        CityDTO cityDTO = CityDTO.builder()
                .name(city.getName())
                .population(city.getPopulation())
                .build();
        cityDTO.setId(city.getId());

        return cityDTO;
    }

    private BusStationDTO createBusStationDTO(BusStation station){
        CityDTO cityDTO = createCityDTO(station.getCity());

        BusStationDTO busStationDTO = BusStationDTO.builder()
                .name(station.getName())
                .city(cityDTO)
                .build();
        busStationDTO.setId(station.getId());

        return busStationDTO;
    }

    private BusStopDTO createBusStopDTO(BusStop busStop){
        BusStationDTO stationDTO = createBusStationDTO(busStop.getStation());
        BusDTO busDTO = createBusDTO(busStop.getBus());

        return BusStopDTO.builder()
                .bus(busDTO)
                .busStation(stationDTO)
                .stopTime(busStop.getStopTime())
                .build();
    }

}
