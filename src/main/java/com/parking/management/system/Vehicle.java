package com.parking.management.system;


public abstract class Vehicle {

    public String licenseNumber;
    public VehicleType type;

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public VehicleType getType() {
        return type;
    }

    public void setType(String VehicleType) {
        this.licenseNumber = licenseNumber;
    }

    public void assignTicket() {
        //в базу данных
    }
}
