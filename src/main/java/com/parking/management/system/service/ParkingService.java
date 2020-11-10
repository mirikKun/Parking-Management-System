package com.parking.management.system.service;

import com.parking.management.system.dao.ParkingDao;
import com.parking.management.system.domain.Floor;
import com.parking.management.system.domain.Parking;
import com.parking.management.system.exception.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

public class ParkingService {

    private final ParkingDao parkingDao;

    public ParkingService(ParkingDao parkingDao) { this.parkingDao = parkingDao; }

    public Optional<Parking> getById(int id) {
        return parkingDao.getById(id);
    }

    public List<Parking> getAll() {
        return parkingDao.getAll();
    }

    public void save(Parking parking) { parkingDao.save(parking); }

    public void update(Parking parking) {
        verifyParkingPresent(parking.getId());
        parkingDao.update(parking);
    }

    public void delete(int id) {
        parkingDao.delete(id);
    }

    private void verifyParkingPresent(int id) {
        parkingDao.getById(id).orElseThrow(() -> new EntityNotFoundException(
                String.format("Parking with id %d is not present", id)));
    }
}
