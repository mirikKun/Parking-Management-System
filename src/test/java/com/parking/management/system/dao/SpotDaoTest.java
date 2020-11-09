package com.parking.management.system.dao;

import com.parking.management.system.DBUnitConfig;
import com.parking.management.system.DBUnitConfigParametrResolver;
import com.parking.management.system.domain.Spot;
import com.parking.management.system.domain.Spot;
import org.dbunit.Assertion;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(DBUnitConfigParametrResolver.class)
class SpotDaoTest extends DBUnitConfig {

    public SpotDaoTest(String name) throws Exception {
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
    void givenSpot_whenSave_thenAddedGivenSpot() throws Exception {
        spotDao.save(new Spot(false,"NEW", 1));

        String file = getClass().getClassLoader().getResource("SpotDao/add.xml").getFile();
        IDataSet expectedData = new FlatXmlDataSetBuilder().build(new File(file));
        ITable expectedTable = expectedData.getTable("spots");

        ITable actualTable = tester.getConnection().createDataSet().getTable("spots");

        Assertion.assertEquals(expectedTable, actualTable);
    }
    @Test
    void givenSpotId3_whenDelete_thenDeletedThirdSpot() throws Exception {
        spotDao.delete(3);

        String file = getClass().getClassLoader().getResource("SpotDao/delete.xml").getFile();
        IDataSet expectedData = new FlatXmlDataSetBuilder().build(new File(file));
        ITable expectedTable = expectedData.getTable("spots");

        ITable actualTable = tester.getConnection().createDataSet().getTable("spots");

        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    void givenNothing_whenGetAll_thenReturnedAllSpot() {
        List<Spot> expectedSpot = new ArrayList<>();
        expectedSpot.add(new Spot(1, true, "Compact", 1));
        expectedSpot.add(new Spot(2, false, "Handicapped", 2));
        expectedSpot.add(new Spot(3, true, "Electric", 3));

        List<Spot> actualCourses = spotDao.getAll();

        assertEquals(expectedSpot, actualCourses);
    }



    @Test
    void givenSpot_whenUpdate_thenUpdatedSpotWithEqualId() throws Exception {
        spotDao.update(new Spot(3, true, "updated" ,3));

        String file = getClass().getClassLoader().getResource("SpotDao/update.xml").getFile();
        IDataSet expectedData = new FlatXmlDataSetBuilder().build(new File(file));
        ITable expectedTable = expectedData.getTable("spots");

        ITable actualTable = tester.getConnection().createDataSet().getTable("spots");

        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    void givenSpotId_whenGetById_thenReturnedSpotWithGivenId() {
        Spot expectedSpot = new Spot(1 ,true ,"Compact" ,1);

        Spot actualSpot = spotDao.getById(1);

        assertEquals(expectedSpot, actualSpot);
    }
}