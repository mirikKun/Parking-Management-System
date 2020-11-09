package com.parking.management.system.domain;

import java.util.Objects;

public class Parking {

    private int id;
    private String address;

    public Parking(String address) {
        this.address = address;
    }

    public Parking(int id, String address) {
        this.id = id;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Parking parking = (Parking) o;
        return id == parking.id &&
                address.equals(parking.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, address);
    }
}
