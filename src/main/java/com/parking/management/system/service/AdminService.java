package com.parking.management.system.service;

import com.parking.management.system.dao.AdminDao;
import com.parking.management.system.domain.Admin;
import com.parking.management.system.exception.EmailNotUniqueException;
import com.parking.management.system.exception.EntityNotFoundException;
import com.parking.management.system.exception.PhoneNotUniqueException;

import java.util.List;
import java.util.Optional;

public class AdminService {

    private final AdminDao adminDao;

    public AdminService(AdminDao adminDao) { this.adminDao = adminDao; }

    public Optional<Admin> getById(int id) {
        return adminDao.getById(id);
    }

    public List<Admin> getAll() {
        return adminDao.getAll();
    }

    public void save(Admin admin) {
        verifyAdminUnique(admin);
        adminDao.save(admin);
    }

    public void update(Admin admin) {
        verifyAdminUnique(admin);
        verifyAdminPresent(admin.getId());
        adminDao.update(admin);
    }

    public void delete(int id) {
        adminDao.delete(id);
    }

    private void verifyAdminPresent(int id) {
        adminDao.getById(id).orElseThrow(() -> new EntityNotFoundException(
                String.format("Admin with id %d is not present", id)));
    }

    private void verifyAdminUnique(Admin admin) {
        adminDao.getByEmail(admin.getEmail()).ifPresent(adminWithSameEmail -> {
            if (admin.getId() != adminWithSameEmail.getId()) {
                throw new EmailNotUniqueException(String.format("Admin with email %s already exist", admin.getEmail()));
            }
        });
        adminDao.getByPhone(admin.getPhone()).ifPresent(adminWithSamePhone -> {
            if (admin.getId() != adminWithSamePhone.getId()) {
                throw new PhoneNotUniqueException(String.format("Admin with phone %s already exist", admin.getEmail()));
            }
        });
    }
}
