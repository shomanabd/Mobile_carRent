package com.example.mobile_carrent;

public class CarModel {

    String carName;
    String carDescription;
    int Seats;
    double price;
    String photo;

    public CarModel(String carName, String carDescription, int seats, double price, String photo) {
        this.carName = carName;
        this.carDescription = carDescription;
        Seats = seats;
        this.price = price;
        this.photo = photo;
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
}
