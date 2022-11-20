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
import ro.ubb.catalog.core.service.IBusStopService;
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


public class BusStopControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private BusStopController busStopController;

    @Mock
    private IBusStopService busStopService;

    @Mock
    private BusStopConverter busStopConverter;


    private Bus bus;
    private Driver driver;
    private City city;
    private BusStation station;
    private BusStop stop;

    private BusStopDTO stopDTO;
    private BusStopUpdateDTO stopUpdateDTO;
    private BusStopSaveDTO stopSaveDTO;


    @Before
    public void setup() throws Exception {
        initMocks(this);

        this.mockMvc = MockMvcBuilders
                .standaloneSetup(busStopController)
                .build();

        initData();
    }

    @Test
    public void findAll() throws Exception{
        List<BusStop> stops = Arrays.asList(stop);
        Set<BusStopDTO> stopDTOS = new HashSet<>(Arrays.asList(stopDTO));

        // configure mock objects to return test data when a method is invoked
        when(busStopService.findAll()).thenReturn(stops);
        when(busStopConverter.convertModelsToDTOs(stops)).thenReturn(stopDTOS);

        // invoke the HTTP get request
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/stops"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.stops", hasSize(1)))
                .andExpect(jsonPath("$.stops[0].stopTime", is("s1")));

        String result = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println(result);

        verify(busStopService, times(1)).findAll();
        verify(busStopConverter, times(1)).convertModelsToDTOs(stops);
        verifyNoMoreInteractions(busStopService, busStopConverter);

    }

    @Test
    public void save() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders
                .post("/stops", stopSaveDTO)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(toJsonString2(stopSaveDTO)))
                .andExpect(status().isOk());

        verify(busStopService, times(1)).save(stopSaveDTO.getBusId(), stopSaveDTO.getStationId(), stopSaveDTO.getStopTime());
        verifyNoMoreInteractions(busStopService);


    }

    @Test
    public void update() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders
                .put("/stops/" + stop.getBus().getId() + "/" + stop.getStation().getId(), stopUpdateDTO)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(toJsonString3(stopUpdateDTO)))
                .andExpect(status().isOk());

        verify(busStopService, times(1)).update(stop.getBus().getId(), stop.getStation().getId(), stopUpdateDTO.getStopTime());
        verifyNoMoreInteractions(busStopService);

    }

    @Test
    public void delete() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/stops/" + stop.getBus().getId() + "/" + stop.getStation().getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        verify(busStopService, times(1)).delete(stop.getBus().getId(), stop.getStation().getId());
        verifyNoMoreInteractions(busStopService);
    }

    private String toJsonString1(BusStopDTO busStopDTO) {
        try {
            return new ObjectMapper().writeValueAsString(busStopDTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String toJsonString2(BusStopSaveDTO busStopDTO) {
        try {
            return new ObjectMapper().writeValueAsString(busStopDTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String toJsonString3(BusStopUpdateDTO busStopDTO) {
        try {
            return new ObjectMapper().writeValueAsString(busStopDTO);
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

        stopDTO = createBusStopDTO(stop);
        stopUpdateDTO = createBusUpdateDTO(stop);
        stopSaveDTO = createBusStopSaveDTO(stop);
    }

    private BusStopSaveDTO createBusStopSaveDTO(BusStop stop){
        return new BusStopSaveDTO(stop.getBus().getId(), stop.getStation().getId(), stop.getStopTime());
    }

    private BusStopUpdateDTO createBusUpdateDTO(BusStop stop){
        return new BusStopUpdateDTO(stop.getStopTime());
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
