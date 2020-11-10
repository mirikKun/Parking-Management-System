package com.parking.management.system.service;

import com.parking.management.system.dao.PaymentDao;
import com.parking.management.system.domain.Payment;
import com.parking.management.system.exception.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

public class PaymentService {

    private final PaymentDao paymentDao;

    public PaymentService(PaymentDao paymentDao) { this.paymentDao = paymentDao; }

    public Optional<Payment> getById(int id) {
        return paymentDao.getById(id);
    }

    public List<Payment> getAll() {
        return paymentDao.getAll();
    }

    public void save(Payment payment) { paymentDao.save(payment); }

    public void update(Payment payment) {
        verifyPaymentPresent(payment.getId());
        paymentDao.update(payment);
    }

    public void delete(int id) {
        paymentDao.delete(id);
    }

    private void verifyPaymentPresent(int id) {
        paymentDao.getById(id).orElseThrow(() -> new EntityNotFoundException(
                String.format("Payment with id %d is not present", id)));
    }
}
