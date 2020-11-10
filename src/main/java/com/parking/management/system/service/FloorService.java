package com.parking.management.system.service;

import com.parking.management.system.dao.FloorDao;
import com.parking.management.system.domain.Floor;
import com.parking.management.system.exception.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

public class FloorService {

    private final FloorDao floorDao;

    public FloorService(FloorDao adminDao, FloorDao floorDao) { this.floorDao = floorDao; }

    public Optional<Floor> getById(int id) {
        return floorDao.getById(id);
    }

    public List<Floor> getAll() {
        return floorDao.getAll();
    }

    public void save(Floor floor) { floorDao.save(floor); }

    public void update(Floor floor) {
        verifyAdminPresent(floor.getId());
        floorDao.update(floor);
    }

    public void delete(int id) {
        floorDao.delete(id);
    }

    private void verifyAdminPresent(int id) {
        floorDao.getById(id).orElseThrow(() -> new EntityNotFoundException(
                String.format("Floor with id %d is not present", id)));
    }
}
