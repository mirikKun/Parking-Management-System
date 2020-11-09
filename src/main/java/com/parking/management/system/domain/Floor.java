package com.parking.management.system.domain;

import java.util.Objects;

public class Floor {

    private int id;
    private int floorNumber;
    private int spotNumber;
    private int parkingID;

    public Floor(int floorNumber, int spotNumber, int parkingID) {
        this.floorNumber = floorNumber;
        this.spotNumber = spotNumber;
        this.parkingID = parkingID;
    }

    public Floor(int id, int floorNumber, int spotNumber, int parkingID) {
        this.id = id;
        this.floorNumber = floorNumber;
        this.spotNumber = spotNumber;
        this.parkingID = parkingID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public int getSpotNumber() {
        return spotNumber;
    }

    public void setSpotNumber(int spotNumber) {
        this.spotNumber = spotNumber;
    }

    public int getParkingID() {
        return parkingID;
    }

    public void setParkingID(int parkingID) {
        this.parkingID = parkingID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Floor floor = (Floor) o;
        return id == floor.id &&
                floorNumber == floor.floorNumber &&
                spotNumber == floor.spotNumber &&
                parkingID == floor.parkingID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, floorNumber, spotNumber, parkingID);
    }
}
