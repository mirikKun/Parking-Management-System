package com.parking.management.system.domain;

import java.time.LocalDate;
import java.util.Objects;

public class Ticket {

    private int id;
    private int paymentId;
    private LocalDate creationDate;

    public Ticket(int id, LocalDate creationDate, int paymentId ){
        this.id = id;
        this.paymentId = paymentId;
        this.creationDate = creationDate;
    }

    public Ticket(LocalDate creationDate, int paymentId) {

        this.paymentId = paymentId;
        this.creationDate = creationDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return id == ticket.id &&
                paymentId == ticket.paymentId &&
                creationDate.equals(ticket.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, paymentId, creationDate);
    }
}
