package com.parking.management.system;

import javax.xml.stream.Location;
import java.util.List;

public class ParkingLot implements ParkingPlaceCreator, TicketCreator {

    private int id;
    private String address;
    private List<EntrancePanel> entrancePanels;
    private int slotNumber;
    private List<ParkingSpot> parkingSpots;

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

    public List<EntrancePanel> getEntrancePanels() {
        return entrancePanels;
    }

    public void setEntrancePanels(List<EntrancePanel> entrancePanels) {
        this.entrancePanels = entrancePanels;
    }

    public int getSlotNumber() {
        return slotNumber;
    }

    public void setSlotNumber(int slotNumber) {
        this.slotNumber = slotNumber;
    }

    public void addEntrancePanel(EntrancePanel entrancePanel) {
        entrancePanels.add(entrancePanel);
    }

    public List<ParkingSpot> getParkingSpots() {
        return parkingSpots;
    }

    public void setParkingSpots(List<ParkingSpot> parkingSpots) {
        this.parkingSpots = parkingSpots;
    }

    public boolean isFull() {
        return parkingSpots.size() == slotNumber;
    }

    @Override
    public ParkingFloor addParkingFloor() {
        return null;
    }

    @Override
    public ParkingTicket getNewParkingTicket() {
        return null;
    }
}
