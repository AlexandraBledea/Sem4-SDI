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
import ro.ubb.catalog.core.service.IBusStationService;
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


public class BusStationControllerTest {

    private MockMvc mockMvc;


    @InjectMocks
    private BusStationController busStationController;

    @Mock
    private IBusStationService busStationService;

    @Mock
    private BusStationConverter busStationConverter;

    @Mock
    private BusStopConverter stopConverter;

    private Bus bus;
    private Driver driver;
    private City city;
    private BusStation station;
    private BusStop stop;

    private BusStopDTO stopDTO;
    private BusStationDTO stationDTO;
    private BusStationSaveDTO stationSaveDTO;
    private BusStationUpdateDTO stationUpdateDTO;


    @Before
    public void setup() throws Exception {
        initMocks(this);

        this.mockMvc = MockMvcBuilders
                .standaloneSetup(busStationController)
                .build();

        initData();
    }

    @Test
    public void getStation() throws Exception{
        when(busStationService.findBusStationByName("n1")).thenReturn(station);
        when(busStationConverter.convertModelToDto(any(BusStation.class))).thenReturn(stationDTO);

        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/stations/getStation/" + station.getName()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.name", is("n1")));

        String result = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println(result);

        verify(busStationService, times(1)).findBusStationByName("n1");
        verify(busStationConverter, times(1)).convertModelToDto(any(BusStation.class));
        verifyNoMoreInteractions(busStationService, busStationConverter);
    }

    @Test
    public void findAll() throws Exception{
        List<BusStation> stations=  Arrays.asList(station);
        Set<BusStationDTO> stationDTOS = new HashSet<>(Arrays.asList(stationDTO));

        // configure mock objects to return test data when a method is invoked
        when(busStationService.findAll()).thenReturn(stations);
        when(busStationConverter.convertModelsToDTOs(stations)).thenReturn(stationDTOS);

        // invoke the HTTP get request
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/stations"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.stations", hasSize(1)))
                .andExpect(jsonPath("$.stations[0].name", is("n1")));

        String result = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println(result);


        verify(busStationService, times(1)).findAll();
        verify(busStationConverter, times(1)).convertModelsToDTOs(stations);
        verifyNoMoreInteractions(busStationService, busStationConverter);

    }

    @Test
    public void findAllStopsForStation() throws Exception{
        Set<BusStop> stops=  new HashSet<>(Arrays.asList(stop));
        Set<BusStopDTO> stopDTOS = new HashSet<>(Arrays.asList(stopDTO));

        when(busStationService.findAllStopsForStation(1L)).thenReturn(stops);
        when(stopConverter.convertModelsToDTOs(stops)).thenReturn(stopDTOS);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/stations/" + station.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.stops", hasSize(1)))
                .andExpect(jsonPath("$.stops[0].stopTime", is("s1")));

        String result = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println(result);

        verify(busStationService, times(1)).findAllStopsForStation(1L);
        verify(stopConverter, times(1)).convertModelsToDTOs(stops);
        verifyNoMoreInteractions(busStationService, stopConverter);

    }

    @Test
    public void save() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                .post("/stations", stationSaveDTO)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(toJsonString2(stationSaveDTO)))
                .andExpect(status().isOk());

        verify(busStationService, times(1)).save(stationSaveDTO.getName(), stationSaveDTO.getCityId());
        verifyNoMoreInteractions(busStationService, busStationConverter);

    }

    @Test
    public void update() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                .put("/stations/" + station.getId(), stationUpdateDTO)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(toJsonString3(stationUpdateDTO)))
                .andExpect(status().isOk());


        verify(busStationService, times(1)).update(station.getId(), stationUpdateDTO.getName());
        verifyNoMoreInteractions(busStationService, busStationConverter);

    }

    @Test
    public void delete() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/stations/" + station.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        verify(busStationService, times(1)).delete(station.getId());
        verifyNoMoreInteractions(busStationService, busStationConverter);
    }


    private String toJsonString1(BusStationDTO stationDTO) {
        try {
            return new ObjectMapper().writeValueAsString(stationDTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String toJsonString2(BusStationSaveDTO stationDTO) {
        try {
            return new ObjectMapper().writeValueAsString(stationDTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String toJsonString3(BusStationUpdateDTO stationDTO) {
        try {
            return new ObjectMapper().writeValueAsString(stationDTO);
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

        stationDTO = createBusStationDTO(station);
        stopDTO = createBusStopDTO(stop);
        stationSaveDTO = createBusStationSaveDTO(station);
        stationUpdateDTO = createBusStationUpdateDTO(station);

    }

    private BusStationSaveDTO createBusStationSaveDTO(BusStation station){
        return new BusStationSaveDTO(station.getName(), station.getCity().getId());
    }

    private BusStationUpdateDTO createBusStationUpdateDTO(BusStation station){
        return new BusStationUpdateDTO(station.getName());
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
