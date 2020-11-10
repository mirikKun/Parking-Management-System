package com.parking.management.system.dao;

import com.parking.management.system.DBUnitConfig;
import com.parking.management.system.DBUnitConfigParametrResolver;
import com.parking.management.system.domain.Admin;
import org.dbunit.Assertion;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(DBUnitConfigParametrResolver.class)
class AdminDaoTest extends DBUnitConfig{

    public AdminDaoTest(String name) throws Exception {
        super(name);
    }

    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();
        String file = getClass().getClassLoader().getResource("preparedDataset.xml").getFile();
        beforeData = new FlatXmlDataSetBuilder().build(new File(file));
        tester.setDataSet(beforeData);
        tester.onSetup();
    }

    @Test
    void givenAdminId3_whenDelete_thenDeletedThirdAdmin() throws Exception {
        adminDao.delete(3);

        String file = getClass().getClassLoader().getResource("AdminDao/delete.xml").getFile();
        IDataSet expectedData = new FlatXmlDataSetBuilder().build(new File(file));
        ITable expectedTable = expectedData.getTable("admins");

        ITable actualTable = tester.getConnection().createDataSet().getTable("admins");

        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    void givenNothing_whenGetAll_thenReturnedAllAdmins() {
        List<Admin> expectedAdmins = new ArrayList<>();
        expectedAdmins.add(new Admin(1, "Bob Marley", "Khreschatyk St, 14, Kyiv, 01001", "marley@gmail.com", "+380505050505", 1));
        expectedAdmins.add(new Admin(2, "Bob Dylan", "Esplanadna St, 17, Kyiv, 02000", "dylan@gmail.com", "+380665039348", 2));
        expectedAdmins.add(new Admin(3, "Andrey Cartman", "1 Naberezhno Khreshchatytska Str, Kyiv, 04070", "cartman@gmail.com", "+380999932131", 3));

        List<Admin> actualCourses = adminDao.getAll();

        assertEquals(expectedAdmins, actualCourses);
    }

    @Test
    void givenAdmin_whenSave_thenAddedGivenAdmin() throws Exception {
        adminDao.save(new Admin("Some Guy", "Brown Street 7", "someguy@gmail.com", "+380963786265", 3));

        String file = getClass().getClassLoader().getResource("AdminDao/add.xml").getFile();
        IDataSet expectedData = new FlatXmlDataSetBuilder().build(new File(file));
        ITable expectedTable = expectedData.getTable("admins");

        ITable actualTable = tester.getConnection().createDataSet().getTable("admins");

        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    void givenAdmin_whenUpdate_thenUpdatedAdminWithEqualId() throws Exception {
        adminDao.update(new Admin(3, "Name Lastname", "Address", "email@gmail.com", "+380999999999", 3));

        String file = getClass().getClassLoader().getResource("AdminDao/update.xml").getFile();
        IDataSet expectedData = new FlatXmlDataSetBuilder().build(new File(file));
        ITable expectedTable = expectedData.getTable("admins");

        ITable actualTable = tester.getConnection().createDataSet().getTable("admins");

        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    void givenAdminId_whenGetById_thenReturnedAdminWithGivenId() {
        Admin expectedAdmin = new Admin(1, "Bob Marley", "Khreschatyk St, 14, Kyiv, 01001", "marley@gmail.com", "+380505050505", 1);

        Optional<Admin> actualAdmin = adminDao.getById(1);

        assertEquals(expectedAdmin, actualAdmin.get());
    }
}