package com.parking.management.system;

public class ParkingDisplayBoard {

    private int id;
    private HandicappedSpot handicappedFreeSpot;
    private CompactSpot compactFreeSpot;
    private LargeSpot largeFreeSpot;
    private MotorbikeSpot motorbikeFreeSpot;
    private ElectricSpot electricFreeSpot;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public HandicappedSpot getHandicappedFreeSpot() {
        return handicappedFreeSpot;
    }

    public void setHandicappedFreeSpot(HandicappedSpot handicappedFreeSpot) {
        this.handicappedFreeSpot = handicappedFreeSpot;
    }

    public CompactSpot getCompactFreeSpot() {
        return compactFreeSpot;
    }

    public void setCompactFreeSpot(CompactSpot compactFreeSpot) {
        this.compactFreeSpot = compactFreeSpot;
    }

    public LargeSpot getLargeFreeSpot() {
        return largeFreeSpot;
    }

    public void setLargeFreeSpot(LargeSpot largeFreeSpot) {
        this.largeFreeSpot = largeFreeSpot;
    }

    public MotorbikeSpot getMotorbikeFreeSpot() {
        return motorbikeFreeSpot;
    }

    public void setMotorbikeFreeSpot(MotorbikeSpot motorbikeFreeSpot) {
        this.motorbikeFreeSpot = motorbikeFreeSpot;
    }

    public ElectricSpot getElectricFreeSpot() {
        return electricFreeSpot;
    }

    public void setElectricFreeSpot(ElectricSpot electricFreeSpot) {
        this.electricFreeSpot = electricFreeSpot;
    }

    public int showEmptySpotNumber(ParkingSpotType type) {
        switch (type) {
            case HANDICAPPED:
                return handicappedFreeSpot.getNumber();
            case COMPACT:
                return compactFreeSpot.getNumber();
            case LARGE:
                return largeFreeSpot.getNumber();
            case MOTORBIKE:
                return motorbikeFreeSpot.getNumber();
            case ELECTRIC:
                return electricFreeSpot.getNumber();
            default:
                return -1;
        }
    }
}
