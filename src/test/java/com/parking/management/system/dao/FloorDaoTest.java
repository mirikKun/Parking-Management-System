package com.parking.management.system.dao;

import com.parking.management.system.DBUnitConfig;
import com.parking.management.system.DBUnitConfigParametrResolver;
import com.parking.management.system.domain.Floor;
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
class FloorDaoTest extends DBUnitConfig {

    public FloorDaoTest(String name) throws Exception {
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
    void givenFloorId3_whenDelete_thenDeletedThirdFloor() throws Exception {
        floorDao.delete(3);

        String file = getClass().getClassLoader().getResource("FloorDao/delete.xml").getFile();
        IDataSet expectedData = new FlatXmlDataSetBuilder().build(new File(file));
        ITable expectedTable = expectedData.getTable("floors");

        ITable actualTable = tester.getConnection().createDataSet().getTable("floors");

        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    void givenNothing_whenGetAll_thenReturnedAllFloors() {
        List<Floor> expectedFloors = new ArrayList<>();
        expectedFloors.add(new Floor(1, 1, 40, 1));
        expectedFloors.add(new Floor(2, 2, 12, 1));
        expectedFloors.add(new Floor(3, 1, 35, 2));

        List<Floor> actualCourses = floorDao.getAll();

        assertEquals(expectedFloors, actualCourses);
    }

    @Test
    void givenFloor_whenSave_thenAddedGivenFloor() throws Exception {
        floorDao.save(new Floor(4, 2, 41, 2));

        String file = getClass().getClassLoader().getResource("FloorDao/add.xml").getFile();
        IDataSet expectedData = new FlatXmlDataSetBuilder().build(new File(file));
        ITable expectedTable = expectedData.getTable("floors");

        ITable actualTable = tester.getConnection().createDataSet().getTable("floors");

        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    void givenFloor_whenUpdate_thenUpdatedFloorWithEqualId() throws Exception {
        floorDao.update(new Floor(3, 2, 42, 2));

        String file = getClass().getClassLoader().getResource("FloorDao/update.xml").getFile();
        IDataSet expectedData = new FlatXmlDataSetBuilder().build(new File(file));
        ITable expectedTable = expectedData.getTable("floors");

        ITable actualTable = tester.getConnection().createDataSet().getTable("floors");

        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    void givenFloorId_whenGetById_thenReturnedFloorWithGivenId() {
        Floor expectedFloor = new Floor(1, 1, 40, 1);

        Optional<Floor> actualFloor = floorDao.getById(1);

        assertEquals(expectedFloor, actualFloor.get());
    }
}