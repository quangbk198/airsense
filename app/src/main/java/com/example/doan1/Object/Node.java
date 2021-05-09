package com.example.doan1.Object;

public class Node {
    private String ID;
    private String Address;
    private double Latitude;        //Vĩ độ
    private double Longitude;       //Kinh độ
    private String Description;
    private boolean isChecked;

    public Node(String ID, String address, double latitude, double longitude, String description) {
        this.ID = ID;
        Address = address;
        Latitude = latitude;
        Longitude = longitude;
        Description = description;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public boolean getChecked() {
        return isChecked;
    }
    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
