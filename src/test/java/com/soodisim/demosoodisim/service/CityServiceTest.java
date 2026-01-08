package com.soodisim.demosoodisim.service;

import com.soodisim.demosoodisim.model.City;
import com.soodisim.demosoodisim.repository.CityRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CityServiceTest {

    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private CityService cityService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCity() {
        City city = new City();
        city.setCityId(1L);
        city.setName("Tirana");

        when(cityRepository.save(city)).thenReturn(city);

        City result = cityService.createCity(city);

        assertNotNull(result);
        assertEquals("Tirana", result.getName());
        verify(cityRepository).save(city);
    }

    @Test
    void testGetAllCities() {
        City c1 = new City();
        City c2 = new City();

        when(cityRepository.findAll()).thenReturn(Arrays.asList(c1, c2));

        List<City> result = cityService.getAllCities();

        assertEquals(2, result.size());
    }

    @Test
    void testGetCityByIdSuccess() {
        City city = new City();
        city.setCityId(1L);

        when(cityRepository.findById(1L)).thenReturn(Optional.of(city));

        City result = cityService.getCityById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getCityId());
    }

    @Test
    void testGetCityByIdNotFound() {
        when(cityRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> cityService.getCityById(1L)
        );

        assertEquals("City not found", ex.getMessage());
    }
}
