package com.parking.management.system.service;

import com.parking.management.system.dao.AdminDao;
import com.parking.management.system.domain.Admin;
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
class AdminServiceTest {

    @Mock
    private AdminDao adminDao;

    @InjectMocks
    private AdminService adminService;

    @Test
    void givenIdOfTheFirstAdmin_whenGetById_thenReturnedAdminWithGivenId() {
        Optional<Admin> expectedAdmin = Optional.of(new Admin(1, "Bob Marley", "Khreschatyk St, 14, Kyiv, 01001", "marley@gmail.com", "+380505050505", 1));
        when(adminDao.getById(1)).thenReturn(expectedAdmin);

        Optional<Admin> actualAdmin = adminService.getById(1);

        verify(adminDao, times(1)).getById(1);
        assertEquals(expectedAdmin, actualAdmin);
    }

    @Test
    void getAll() {
        List<Admin> expectedAdmins = singletonList(new Admin(1, "Bob Marley", "Khreschatyk St, 14, Kyiv, 01001", "marley@gmail.com", "+380505050505", 1));
        when(adminDao.getAll()).thenReturn(expectedAdmins);

        List<Admin> actualAdmins = adminService.getAll();

        verify(adminDao, times(1)).getAll();
        assertEquals(expectedAdmins, actualAdmins);
    }

    @Test
    void save() {
        Admin admin = new Admin(1, "Bob Marley", "Khreschatyk St, 14, Kyiv, 01001", "marley@gmail.com", "+380505050505", 1);
        when(adminDao.getByEmail("marley@gmail.com")).thenReturn(Optional.empty());
        when(adminDao.getByPhone("+380505050505")).thenReturn(Optional.empty());
        adminService.save(admin);

        verify(adminDao, times(1)).save(admin);
    }

    @Test
    void update() {
        Admin admin = new Admin(1, "Bob Marley", "Khreschatyk St, 14, Kyiv, 01001", "marley@gmail.com", "+380505050505", 1);
        when(adminDao.getById(1)).thenReturn(Optional.of(admin));
        when(adminDao.getByEmail("marley@gmail.com")).thenReturn(Optional.empty());

        adminService.update(admin);

        verify(adminDao, times(1)).update(admin);
    }
}