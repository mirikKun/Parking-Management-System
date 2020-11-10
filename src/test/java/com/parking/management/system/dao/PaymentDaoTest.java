package com.parking.management.system.dao;

import com.parking.management.system.DBUnitConfig;
import com.parking.management.system.DBUnitConfigParametrResolver;
import com.parking.management.system.domain.Floor;
import com.parking.management.system.domain.Payment;
import org.dbunit.Assertion;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(DBUnitConfigParametrResolver.class)
class PaymentDaoTest extends DBUnitConfig{

    public PaymentDaoTest(String name) throws Exception {
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
    void givenPaymentId3_whenDelete_thenDeletedThirdPayment() throws Exception {
        paymentDao.delete(3);

        String file = getClass().getClassLoader().getResource("PaymentDao/delete.xml").getFile();
        IDataSet expectedData = new FlatXmlDataSetBuilder().build(new File(file));
        ITable expectedTable = expectedData.getTable("payments");

        ITable actualTable = tester.getConnection().createDataSet().getTable("payments");

        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    void givenNothing_whenGetAll_thenReturnedAllPayments() {
        List<Payment> expectedPayments = new ArrayList<>();
        expectedPayments.add(new Payment(1, LocalDate.parse("2020-11-07"), 1300, "Paid", "Cash"));
        expectedPayments.add(new Payment(2, LocalDate.parse("2020-10-06"), 753, "Not paid", "Card"));
        expectedPayments.add(new Payment(3, LocalDate.parse("2020-10-29"), 1550, "Paid", "Cash"));

        List<Payment> actualCourses = paymentDao.getAll();

        assertEquals(expectedPayments, actualCourses);
    }

    @Test
    void givenPayment_whenSave_thenAddedGivenPayment() throws Exception {
        paymentDao.save(new Payment(4, LocalDate.parse("2020-12-20"), 800, "Paid", "Card"));

        String file = getClass().getClassLoader().getResource("PaymentDao/add.xml").getFile();
        IDataSet expectedData = new FlatXmlDataSetBuilder().build(new File(file));
        ITable expectedTable = expectedData.getTable("payments");

        ITable actualTable = tester.getConnection().createDataSet().getTable("payments");

        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    void givenPayment_whenUpdate_thenUpdatedPaymentWithEqualId() throws Exception {
        paymentDao.update(new Payment(3, LocalDate.parse("2020-10-20"), 155, "Paid", "Cash"));

        String file = getClass().getClassLoader().getResource("PaymentDao/update.xml").getFile();
        IDataSet expectedData = new FlatXmlDataSetBuilder().build(new File(file));
        ITable expectedTable = expectedData.getTable("payments");

        ITable actualTable = tester.getConnection().createDataSet().getTable("payments");

        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    void givenPaymentId_whenGetById_thenReturnedPaymentWithGivenId() {
        Payment expectedPayment = new Payment(1, LocalDate.parse("2020-11-07"), 1300, "Paid", "Cash");

        Optional<Payment> actualPayment = paymentDao.getById(1);

        assertEquals(expectedPayment, actualPayment.get());
    }
}