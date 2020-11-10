package com.parking.management.system.service;

import com.parking.management.system.dao.SpotDao;
import com.parking.management.system.domain.Spot;
import com.parking.management.system.exception.EntityNotFoundException;
import com.parking.management.system.exception.UsernameNotUniqueException;

import java.util.List;
import java.util.Optional;

public class SpotService {

    private final SpotDao spotDao;

    public SpotService(SpotDao spotDao) {
        this.spotDao = spotDao;
    }

    public Optional<Spot> getById(int id) {
        return spotDao.getById(id);
    }

    public List<Spot> getAll() {
        return spotDao.getAll();
    }

    public void save(Spot spot) {

        spotDao.save(spot);
    }

    public void update(Spot spot) {
        verifySpotPresent(spot.getId());
        spotDao.update(spot);
    }

    public void delete(int id) {
        spotDao.delete(id);
    }

    private void verifySpotPresent(int id) {
        spotDao.getById(id).orElseThrow(() -> new EntityNotFoundException(
                String.format("Spot with id %d is not present", id)));
    }
}
