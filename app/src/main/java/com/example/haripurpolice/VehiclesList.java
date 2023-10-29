package com.example.haripurpolice;

public class VehiclesList
{
    private final Integer VehicleIcon;
    private final String VehicleName;


    public VehiclesList(Integer vehicleIcon, String vehicleName)
    {
        VehicleIcon = vehicleIcon;
        VehicleName = vehicleName;
    }


    public Integer getVehicleIcon() {
        return VehicleIcon;
    }

    public String getVehicleName() {
        return VehicleName;
    }
}
