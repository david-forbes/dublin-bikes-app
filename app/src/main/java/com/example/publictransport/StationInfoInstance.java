package com.example.publictransport;

public class StationInfoInstance {

    public String name;
    public String bikes_available;
    public String available_bike_stands;

    public String getAvailable_bike_stands() {
        return available_bike_stands;
    }

    public void setAvailable_bike_stands(String available_bike_stands) {
        this.available_bike_stands = available_bike_stands;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBikes_available() {
        return bikes_available;
    }

    public void setBikes_available(String bikes_available) {
        this.bikes_available = bikes_available;
    }

    public StationInfoInstance(String name, String bikes_available,String available_bike_stands) {
        this.name = name;
        this.bikes_available = bikes_available;
        this.available_bike_stands=available_bike_stands;

    }
}
