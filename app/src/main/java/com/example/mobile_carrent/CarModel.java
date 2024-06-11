package com.example.mobile_carrent;

public class CarModel {

    int Car_ID;
    String carName;
    String carDescription;
    int Seats;
    double price;
    String photo;

    String userName;

    public CarModel(int car_ID, String carName, String carDescription, int seats, double price, String photo, String userName) {
        Car_ID = car_ID;
        this.carName = carName;
        this.carDescription = carDescription;
        Seats = seats;
        this.price = price;
        this.photo = photo;
        this.userName = userName;
    }

    public int getCar_ID() {
        return Car_ID;
    }

    public String getCarName() {
        return carName;
    }

    public String getCarDescription() {
        return carDescription;
    }

    public int getSeats() {
        return Seats;
    }

    public double getPrice() {
        return price;
    }

    public String getPhoto() {
        return photo;
    }

    public String getUserName() {
        return userName;
    }
}
