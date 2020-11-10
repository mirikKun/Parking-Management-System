package com.parking.management.system.service;

import com.parking.management.system.dao.ParkingDao;
import com.parking.management.system.dao.PaymentDao;
import com.parking.management.system.domain.Parking;
import com.parking.management.system.domain.Payment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentDao paymentDao;

    @InjectMocks
    private PaymentService paymentService;

    @Test
    void givenIdOfTheFirstPayment_whenGetById_thenReturnedPaymentWithGivenId() {
        Optional<Payment> expectedPayment = Optional.of(new Payment(1, LocalDate.parse("2020-11-07"), 1300, "Paid", "Cash"));
        when(paymentDao.getById(1)).thenReturn(expectedPayment);

        Optional<Payment> actualPayment = paymentDao.getById(1);

        verify(paymentDao, times(1)).getById(1);
        assertEquals(expectedPayment, actualPayment);
    }

    @Test
    void getAll() {
        List<Payment> expectedPayments = singletonList(new Payment(1, LocalDate.parse("2020-11-07"), 1300, "Paid", "Cash"));
        when(paymentDao.getAll()).thenReturn(expectedPayments);

        List<Payment> actualPayments = paymentService.getAll();

        verify(paymentDao, times(1)).getAll();
        assertEquals(expectedPayments, actualPayments);
    }

    @Test
    void save() {
        Payment payment = new Payment(1, LocalDate.parse("2020-11-07"), 1300, "Paid", "Cash");
        paymentService.save(payment);

        verify(paymentDao, times(1)).save(payment);
    }

    @Test
    void update() {
        Payment payment = new Payment(1, LocalDate.parse("2020-11-07"), 1300, "Paid", "Cash");
        when(paymentDao.getById(1)).thenReturn(Optional.of(payment));

        paymentService.update(payment);

        verify(paymentDao, times(1)).update(payment);
    }
}