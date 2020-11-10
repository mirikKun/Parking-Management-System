package com.parking.management.system.dao;

import com.parking.management.system.DBUnitConfig;
import com.parking.management.system.DBUnitConfigParametrResolver;
import com.parking.management.system.domain.Floor;
import com.parking.management.system.domain.Parking;
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
class ParkingDaoTest extends DBUnitConfig{

    public ParkingDaoTest(String name) throws Exception {
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
    void givenParkingId3_whenDelete_thenDeletedThirdParking() throws Exception {
        parkingDao.delete(2);

        String file = getClass().getClassLoader().getResource("ParkingDao/delete.xml").getFile();
        IDataSet expectedData = new FlatXmlDataSetBuilder().build(new File(file));
        ITable expectedTable = expectedData.getTable("parkings");

        ITable actualTable = tester.getConnection().createDataSet().getTable("parkings");

        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    void givenNothing_whenGetAll_thenReturnedAllParkings() {
        List<Parking> expectedParkings = new ArrayList<>();
        expectedParkings.add(new Parking(1, "Peremohy Ave, 37, Kyiv, 03056"));
        expectedParkings.add(new Parking(2, "Shevchenka St, Dnipro, Dnipropetrovsk Oblast, 49000"));

        List<Parking> actualCourses = parkingDao.getAll();

        assertEquals(expectedParkings, actualCourses);
    }

    @Test
    void givenParking_whenSave_thenAddedGivenParking() throws Exception {
        parkingDao.save(new Parking(3, "Brown Street"));

        String file = getClass().getClassLoader().getResource("ParkingDao/add.xml").getFile();
        IDataSet expectedData = new FlatXmlDataSetBuilder().build(new File(file));
        ITable expectedTable = expectedData.getTable("parkings");

        ITable actualTable = tester.getConnection().createDataSet().getTable("parkings");

        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    void givenParking_whenUpdate_thenUpdatedParkingWithEqualId() throws Exception {
        parkingDao.update(new Parking(2, "Unknown"));

        String file = getClass().getClassLoader().getResource("ParkingDao/update.xml").getFile();
        IDataSet expectedData = new FlatXmlDataSetBuilder().build(new File(file));
        ITable expectedTable = expectedData.getTable("parkings");

        ITable actualTable = tester.getConnection().createDataSet().getTable("parkings");

        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    void givenParkingId_whenGetById_thenReturnedParkingWithGivenId() {
        Parking expectedParking = new Parking(1, "Peremohy Ave, 37, Kyiv, 03056");

        Optional<Parking> actualParking = parkingDao.getById(1);

        assertEquals(expectedParking, actualParking.get());
    }
}