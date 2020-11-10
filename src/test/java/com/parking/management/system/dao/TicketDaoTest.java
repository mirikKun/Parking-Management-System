package com.parking.management.system.dao;

import com.parking.management.system.DBUnitConfig;
import com.parking.management.system.DBUnitConfigParametrResolver;
import com.parking.management.system.domain.Ticket;

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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(DBUnitConfigParametrResolver.class)
class TicketDaoTest extends DBUnitConfig{

    public TicketDaoTest(String name) throws Exception {
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
    void givenTicket_whenSave_thenAddedGivenTicket() throws Exception {
        ticketDao.save(new Ticket(LocalDate.parse("2020-10-20"),3  ));

        String file = getClass().getClassLoader().getResource("TicketDao/add.xml").getFile();
        IDataSet expectedData = new FlatXmlDataSetBuilder().build(new File(file));
        ITable expectedTable = expectedData.getTable("tickets");

        ITable actualTable = tester.getConnection().createDataSet().getTable("tickets");

        Assertion.assertEquals(expectedTable, actualTable);
    }
    @Test
    void givenTicketId3_whenDelete_thenDeletedThirdTicket() throws Exception {
        ticketDao.delete(3);

        String file = getClass().getClassLoader().getResource("TicketDao/delete.xml").getFile();
        IDataSet expectedData = new FlatXmlDataSetBuilder().build(new File(file));
        ITable expectedTable = expectedData.getTable("tickets");

        ITable actualTable = tester.getConnection().createDataSet().getTable("tickets");

        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    void givenNothing_whenGetAll_thenReturnedAllTicket() {
        List<Ticket> expectedTicket = new ArrayList<>();
        expectedTicket.add(new Ticket(1,LocalDate.parse("2019-07-13"),1));
        expectedTicket.add(new Ticket(2,LocalDate.parse("2020-10-06"),2));
        expectedTicket.add(new Ticket(3,LocalDate.parse("2020-01-20"),3));

        List<Ticket> actualCourses = ticketDao.getAll();

        assertEquals(expectedTicket, actualCourses);
    }



    @Test
    void givenTicket_whenUpdate_thenUpdatedTicketWithEqualId() throws Exception {
        ticketDao.update(new Ticket(3, LocalDate.parse("2020-01-21"),3 ));

        String file = getClass().getClassLoader().getResource("TicketDao/update.xml").getFile();
        IDataSet expectedData = new FlatXmlDataSetBuilder().build(new File(file));
        ITable expectedTable = expectedData.getTable("tickets");

        ITable actualTable = tester.getConnection().createDataSet().getTable("tickets");

        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    void givenTicketId_whenGetById_thenReturnedTicketWithGivenId() {
        Ticket expectedTicket = new Ticket(1,LocalDate.parse("2019-07-13"),1);

        Optional<Ticket> actualTicket = ticketDao.getById(1);

        assertEquals(expectedTicket, actualTicket.get());
    }
}