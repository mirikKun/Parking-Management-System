package com.parking.management.system.domain;

import java.util.Objects;

public class Vehicle {

    private int id;
    private String type;

    public Vehicle(int id, String type) {
        this.id = id;
        this.type = type;
    }

    public Vehicle(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return id == vehicle.id &&
                type.equals(vehicle.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type);
    }
}
