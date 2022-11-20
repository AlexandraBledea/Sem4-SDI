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
import ro.ubb.catalog.core.converter.DriverConverter;
import ro.ubb.catalog.core.dto.DriverDTO;
import ro.ubb.catalog.core.dto.DriverUpdateDTO;
import ro.ubb.catalog.core.model.Driver;
import ro.ubb.catalog.core.service.IDriverService;

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

public class DriverControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private DriverController driverController;

    @Mock
    private IDriverService driverService;

    @Mock
    private DriverConverter driverConverter;

    private Driver driver1;
    private Driver driver2;

    private DriverDTO driverDTO1;
    private DriverDTO driverDTO2;

    private DriverUpdateDTO driverUpdateDTO1;


    @Before
    public void setup() throws Exception {
        initMocks(this);

        this.mockMvc = MockMvcBuilders
                .standaloneSetup(driverController)
                .build();

        initData();
    }

    @Test
    public void findAll() throws Exception{
        List<Driver> drivers = Arrays.asList(driver1, driver2);
        Set<DriverDTO> driversDTO = new HashSet<>(
                Arrays.asList(driverDTO1, driverDTO2)
        );

        // configure mock objects to return test data when a method is invoked
        when(driverService.findAll()).thenReturn(drivers);
        when(driverConverter.convertModelsToDTOs(drivers)).thenReturn(driversDTO);

        // invoke the HTTP get request
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/drivers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.drivers", hasSize(2)))
                .andExpect(jsonPath("$.drivers[0].name", anyOf(is("d1"), is("d2"))))
                .andExpect(jsonPath("$.drivers[1].cnp", anyOf(is("c1"), is("c2"))));

        String result = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println(result);


        // verify that the methods are invoked exactly once
        // verify that after the response, no more interactions are made
        verify(driverService, times(1)).findAll();
        verify(driverConverter, times(1)).convertModelsToDTOs(drivers);
        verifyNoMoreInteractions(driverService, driverConverter);

    }

    @Test
    public void getDriver() throws Exception{
        when(driverService.findDriverByCnp("c1")).thenReturn(driver1);
        when(driverConverter.convertModelToDto(any(Driver.class))).thenReturn(driverDTO1);

        ResultActions resultActions = mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/drivers/getDriver/" + "c1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.name", is("d1")))
                .andExpect(jsonPath("$.cnp", is("c1")));

        String result = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println(result);

        verify(driverService, times(1)).findDriverByCnp("c1");
        verify(driverConverter, times(1)).convertModelToDto(any(Driver.class));
        verifyNoMoreInteractions(driverService, driverConverter);
    }

    @Test
    public void save() throws Exception{
        when(driverConverter.convertDtoToModel(any(DriverDTO.class))).thenReturn(driver1);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/drivers", driverDTO1)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(toJsonString(driverDTO1)))
                .andExpect(status().isOk());

        verify(driverService, times(1)).save(driver1);
        verify(driverConverter, times(1)).convertDtoToModel(any(DriverDTO.class));
        verifyNoMoreInteractions(driverService, driverConverter);
    }

    @Test
    public void delete() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/drivers/" + driver1.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        verify(driverService, times(1)).delete(driver1.getId());
        verifyNoMoreInteractions(driverService);
    }

    @Test
    public void update() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                .put("/drivers/" + driver1.getId(), driverDTO1)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(toJsonString(driverDTO1)))
                .andExpect(status().isOk());

        verify(driverService, times(1)).update(driver1.getId(), driverDTO1.getName());
        verifyNoMoreInteractions(driverService, driverConverter);
    }

    private String toJsonString(DriverDTO driverDTO) {
        try {
            return new ObjectMapper().writeValueAsString(driverDTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void initData(){
        driver1 = Driver.builder()
                .name("d1")
                .cnp("c1")
                .build();
        driver1.setId(1L);

        driver2 = Driver.builder()
                .name("d2")
                .cnp("c2")
                .build();
        driver2.setId(2L);

        driverUpdateDTO1 = new DriverUpdateDTO(driver1.getName());

        driverDTO1 = createDriverDTO(driver1);

        driverDTO2 = createDriverDTO(driver2);
    }

    private DriverDTO createDriverDTO(Driver driver){
        DriverDTO driverDTO = DriverDTO.builder()
                .name(driver.getName())
                .cnp(driver.getCnp())
                .build();
        driverDTO.setId(driver.getId());

        return driverDTO;
    }
}
