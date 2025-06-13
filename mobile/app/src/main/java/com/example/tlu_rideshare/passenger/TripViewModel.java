package com.example.tlu_rideshare.passenger;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class TripViewModel extends ViewModel {
    private MutableLiveData<String> yourLocation = new MutableLiveData<>();
    private MutableLiveData<String> destination = new MutableLiveData<>();
    private MutableLiveData<String> selectedDate = new MutableLiveData<>();
    private MutableLiveData<List<Trip>> tripList = new MutableLiveData<>(new ArrayList<>());

    public void setYourLocation(String location) {
        yourLocation.setValue(location);
    }

    public LiveData<String> getYourLocation() {
        return yourLocation;
    }

    public void setDestination(String destination) {
        this.destination.setValue(destination);
    }

    public LiveData<String> getDestination() {
        return destination;
    }

    public void setSelectedDate(String date) {
        selectedDate.setValue(date);
    }

    public LiveData<String> getSelectedDate() {
        return selectedDate;
    }

    public void addTrip(Trip trip) {
        List<Trip> currentTrips = tripList.getValue();
        if (currentTrips == null) {
            currentTrips = new ArrayList<>();
        }
        currentTrips.add(trip);
        tripList.setValue(currentTrips);
    }

    public void updateTrip(Trip updatedTrip) {
        List<Trip> currentTrips = tripList.getValue();
        if (currentTrips != null) {
            for (int i = 0; i < currentTrips.size(); i++) {
                if (currentTrips.get(i).getLicensePlate().equals(updatedTrip.getLicensePlate())) {
                    currentTrips.set(i, updatedTrip);
                    tripList.setValue(currentTrips);
                    return;
                }
            }
            // Nếu không tìm thấy, có thể thêm mới (tùy logic)
            currentTrips.add(updatedTrip);
            tripList.setValue(currentTrips);
        }
    }

    public LiveData<List<Trip>> getTripList() {
        return tripList;
    }
}