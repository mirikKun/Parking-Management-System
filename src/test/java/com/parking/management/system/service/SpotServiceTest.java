package com.parking.management.system.service;

import com.parking.management.system.dao.SpotDao;
import com.parking.management.system.domain.Spot;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class SpotServiceTest {

    @Mock
    private SpotDao SpotDao;

    @InjectMocks
    private SpotService SpotService;

    @Test
    void givenIdOfTheFirstSpot_whenGetById_thenReturnedSpotWithGivenId() {
        Optional<Spot> expectedSpot = Optional.of(new Spot(1, true,"Compact", 1));
        when(SpotDao.getById(1)).thenReturn(expectedSpot);

        Optional<Spot> actualSpot = SpotService.getById(1);

        verify(SpotDao, times(1)).getById(1);
        assertEquals(expectedSpot, actualSpot);
    }

    @Test
    void getAll() {
        List<Spot> expectedSpots = singletonList(new Spot(1, true,"Compact", 1));
        when(SpotDao.getAll()).thenReturn(expectedSpots);

        List<Spot> actualSpots = SpotService.getAll();

        verify(SpotDao, times(1)).getAll();
        assertEquals(expectedSpots, actualSpots);
    }

    @Test
    void save() {
        Spot Spot = new Spot(1, true,"Compact", 1);

        SpotService.save(Spot);

        verify(SpotDao, times(1)).save(Spot);
    }

    @Test
    void update() {
        Spot Spot = new Spot(1, true,"Compact", 1);
        when(SpotDao.getById(1)).thenReturn(Optional.of(Spot));

        SpotService.update(Spot);

        verify(SpotDao, times(1)).update(Spot);
    }
}