package com.parking.management.system.dao;

import com.parking.management.system.DBUnitConfig;
import com.parking.management.system.DBUnitConfigParametrResolver;
import com.parking.management.system.domain.Vehicle;
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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(DBUnitConfigParametrResolver.class)
class VehicleDaoTest extends DBUnitConfig {


    public VehicleDaoTest(String name) throws Exception {
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
    void givenVehicle_whenSave_thenAddedGivenVehicle() throws Exception {
        vehicleDao.save(new Vehicle("Car"));

        String file = getClass().getClassLoader().getResource("VehicleDao/add.xml").getFile();
        IDataSet expectedData = new FlatXmlDataSetBuilder().build(new File(file));
        ITable expectedTable = expectedData.getTable("vehicles");

        ITable actualTable = tester.getConnection().createDataSet().getTable("vehicles");

        Assertion.assertEquals(expectedTable, actualTable);
    }
    @Test
    void givenVehicleId3_whenDelete_thenDeletedThirdVehicle() throws Exception {
        vehicleDao.delete(5);

        String file = getClass().getClassLoader().getResource("VehicleDao/delete.xml").getFile();
        IDataSet expectedData = new FlatXmlDataSetBuilder().build(new File(file));
        ITable expectedTable = expectedData.getTable("vehicles");

        ITable actualTable = tester.getConnection().createDataSet().getTable("vehicles");

        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    void givenNothing_whenGetAll_thenReturnedAllVehicle() {
        List<Vehicle> expectedVehicle = new ArrayList<>();
        expectedVehicle.add(new Vehicle(1,"Car"));
        expectedVehicle.add(new Vehicle(2,"Car"));
        expectedVehicle.add(new Vehicle(3,"Truck"));
        expectedVehicle.add(new Vehicle(4,"Electric"));
        expectedVehicle.add(new Vehicle(5,"Motorbike"));

        List<Vehicle> actualCourses = vehicleDao.getAll();

        assertEquals(expectedVehicle, actualCourses);
    }



    @Test
    void givenVehicle_whenUpdate_thenUpdatedVehicleWithEqualId() throws Exception {
        vehicleDao.update(new Vehicle(5,"Car"));

        String file = getClass().getClassLoader().getResource("VehicleDao/update.xml").getFile();
        IDataSet expectedData = new FlatXmlDataSetBuilder().build(new File(file));
        ITable expectedTable = expectedData.getTable("vehicles");

        ITable actualTable = tester.getConnection().createDataSet().getTable("vehicles");

        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    void givenVehicleId_whenGetById_thenReturnedVehicleWithGivenId() {
        Vehicle expectedVehicle = new Vehicle(1,"Car");

        Vehicle actualVehicle = vehicleDao.getById(1);

        assertEquals(expectedVehicle, actualVehicle);
    }
}