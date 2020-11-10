package com.parking.management.system.service;

import com.parking.management.system.dao.ParkingDao;
import com.parking.management.system.domain.Parking;
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
class ParkingServiceTest {

    @Mock
    private ParkingDao parkingDao;

    @InjectMocks
    private ParkingService parkingService;

    @Test
    void givenIdOfTheFirstParking_whenGetById_thenReturnedParkingWithGivenId() {
        Optional<Parking> expectedParking = Optional.of(new Parking(1, "Peremohy Ave, 37, Kyiv, 03056"));
        when(parkingDao.getById(1)).thenReturn(expectedParking);

        Optional<Parking> actualParking = parkingDao.getById(1);

        verify(parkingDao, times(1)).getById(1);
        assertEquals(expectedParking, actualParking);
    }

    @Test
    void getAll() {
        List<Parking> expectedParkings = singletonList(new Parking(1, "Peremohy Ave, 37, Kyiv, 03056"));
        when(parkingDao.getAll()).thenReturn(expectedParkings);

        List<Parking> actualParkings = parkingService.getAll();

        verify(parkingDao, times(1)).getAll();
        assertEquals(expectedParkings, actualParkings);
    }

    @Test
    void save() {
        Parking parking = new Parking(1, "Peremohy Ave, 37, Kyiv, 03056");
        parkingService.save(parking);

        verify(parkingDao, times(1)).save(parking);
    }

    @Test
    void update() {
        Parking parking = new Parking(1, "Peremohy Ave, 37, Kyiv, 03056");
        when(parkingDao.getById(1)).thenReturn(Optional.of(parking));

        parkingService.update(parking);

        verify(parkingDao, times(1)).update(parking);
    }
}