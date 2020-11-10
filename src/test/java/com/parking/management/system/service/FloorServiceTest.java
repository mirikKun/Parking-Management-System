package com.parking.management.system.service;

import com.parking.management.system.dao.FloorDao;
import com.parking.management.system.domain.Floor;
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
class FloorServiceTest {
    @Mock
    private FloorDao floorDao;

    @InjectMocks
    private FloorService floorService;

    @Test
    void givenIdOfTheFirstFloor_whenGetById_thenReturnedAdminWithGivenId() {
        Optional<Floor> expectedFloor = Optional.of(new Floor(1, 1, 40, 1));
        when(floorDao.getById(1)).thenReturn(expectedFloor);

        Optional<Floor> actualFloor = floorService.getById(1);

        verify(floorDao, times(1)).getById(1);
        assertEquals(expectedFloor, actualFloor);
    }

    @Test
    void getAll() {
        List<Floor> expectedFloors = singletonList(new Floor(1, 1, 40, 1));
        when(floorDao.getAll()).thenReturn(expectedFloors);

        List<Floor> actualFloors = floorService.getAll();

        verify(floorDao, times(1)).getAll();
        assertEquals(expectedFloors, actualFloors);
    }

    @Test
    void save() {
        Floor floor = new Floor(1, 1, 40, 1);
        floorService.save(floor);

        verify(floorDao, times(1)).save(floor);
    }

    @Test
    void update() {
        Floor floor = new Floor(1, 1, 40, 1);
        when(floorDao.getById(1)).thenReturn(Optional.of(floor));

        floorService.update(floor);

        verify(floorDao, times(1)).update(floor);
    }
}