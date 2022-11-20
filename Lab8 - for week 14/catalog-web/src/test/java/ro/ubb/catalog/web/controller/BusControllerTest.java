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


import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ro.ubb.catalog.core.converter.BusConverter;
import ro.ubb.catalog.core.converter.BusStopConverter;
import ro.ubb.catalog.core.dto.*;
import ro.ubb.catalog.core.model.*;
import ro.ubb.catalog.core.service.IBusService;


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


public class BusControllerTest {

    private MockMvc mockMvc;


    @InjectMocks
    private BusController busController;

    @Mock
    private IBusService busService;

    @Mock
    private BusStopConverter stopConverter;

    @Mock
    private BusConverter busConverter;

    private Bus bus;
    private Driver driver;
    private City city;
    private BusStation station;
    private BusStop stop;

    private BusDTO busDTO;
    private BusStopDTO stopDTO;
    private DriverDTO driverDTO;
    private BusSaveDTO busSaveDTO;
    private BusUpdateDTO busUpdateDTO;

    @Before
    public void setup() throws Exception {
        initMocks(this);

        this.mockMvc = MockMvcBuilders
                .standaloneSetup(busController)
                .build();

        initData();
    }

    @Test
    public void findAll() throws Exception{
        List<Bus> buses = Arrays.asList(bus);
        Set<BusDTO> busDTOS = new HashSet<>(Arrays.asList(busDTO));

        // configure mock objects to return test data when a method is invoked
        when(busService.findAll()).thenReturn(buses);
        when(busConverter.convertModelsToDTOs(buses)).thenReturn(busDTOS);

        // invoke the HTTP get request
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/buses"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.buses", hasSize(1)))
                .andExpect(jsonPath("$.buses[0].modelName", anyOf(is("n1"))))
                .andExpect(jsonPath("$.buses[0].fuel", anyOf(is("f1"))))
                .andExpect(jsonPath("$.buses[0].capacity", anyOf(is(1))));

        String result = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println(result);

        verify(busService, times(1)).findAll();
        verify(busConverter, times(1)).convertModelsToDTOs(buses);
        verifyNoMoreInteractions(busService, busConverter);
    }

    @Test
    public void findAllStopsForBus() throws Exception{
        Set<BusStop> stops = new HashSet<>(Arrays.asList(stop));
        Set<BusStopDTO> stopDTOS = new HashSet<>(Arrays.asList(stopDTO));

        when(busService.findAllStopsForBus(bus.getId())).thenReturn(stops);
        when(stopConverter.convertModelsToDTOs(stops)).thenReturn(stopDTOS);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/buses/" + bus.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.stops", hasSize(1)))
                .andExpect(jsonPath("$.stops[0].stopTime", is("s1")));

        String result = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println(result);

        verify(busService, times(1)).findAllStopsForBus(bus.getId());
        verify(stopConverter, times(1)).convertModelsToDTOs(stops);
        verifyNoMoreInteractions(busService, stopConverter);

    }

    @Test
    public void save() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                .post("/buses", busSaveDTO)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(toJsonString2(busSaveDTO)))
                .andExpect(status().isOk());

        verify(busService, times(1)).save(busSaveDTO.getModelName(), busSaveDTO.getFuel(), busSaveDTO.getCapacity(), busSaveDTO.getDriverId());
        verifyNoMoreInteractions(busService, busConverter);
    }

    @Test
    public void update() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                .put("/buses/" + bus.getId(), busUpdateDTO)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(toJsonString3(busUpdateDTO)))
                .andExpect(status().isOk());

        verify(busService, times(1)).update(bus.getId(), busUpdateDTO.getModelName(), busUpdateDTO.getFuel(), busUpdateDTO.getCapacity());
        verifyNoMoreInteractions(busService, busConverter);
    }

    @Test
    public void delete() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/buses/" + bus.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        verify(busService, times(1)).delete(bus.getId());
        verifyNoMoreInteractions(busService, busConverter);
    }

    @Test
    public void getBus() throws Exception{
        when(busService.findBusByModelName("n1")).thenReturn(bus);
        when(busConverter.convertModelToDto(any(Bus.class))).thenReturn(busDTO);

        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/buses/getBus/" + "n1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.modelName", is("n1")))
                .andExpect(jsonPath("$.fuel", is("f1")))
                .andExpect(jsonPath("$.capacity", is(1)));

        String result = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println(result);

        verify(busService, times(1)).findBusByModelName("n1");
        verify(busConverter, times(1)).convertModelToDto(any(Bus.class));
        verifyNoMoreInteractions(busService, busConverter);

    }

    private String toJsonString1(BusDTO busDTO) {
        try {
            return new ObjectMapper().writeValueAsString(busDTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String toJsonString2(BusSaveDTO busDTO) {
        try {
            return new ObjectMapper().writeValueAsString(busDTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String toJsonString3(BusUpdateDTO busDTO) {
        try {
            return new ObjectMapper().writeValueAsString(busDTO);
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

        driverDTO = createDriverDTO(driver);
        busDTO = createBusDTO(bus);
        stopDTO = createBusStopDTO(stop);
        busSaveDTO = createBusSaveDTO(bus);
        busUpdateDTO = createBusUpdateDTO(bus);
    }

    private BusUpdateDTO createBusUpdateDTO(Bus bus){
        BusUpdateDTO dto = new BusUpdateDTO(bus.getModelName(), bus.getFuel(), bus.getCapacity());
        return dto;
    }

    private DriverDTO createDriverDTO(Driver driver){
        DriverDTO driverDTO = DriverDTO.builder()
                .name(driver.getName())
                .cnp(driver.getCnp())
                .build();
        driverDTO.setId(driver.getId());

        return driverDTO;
    }

    private BusSaveDTO createBusSaveDTO(Bus bus){
        BusSaveDTO busSaveDTO = new BusSaveDTO(bus.getModelName(), bus.getFuel(), bus.getCapacity(), bus.getDriver().getId());
        return busSaveDTO;
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
