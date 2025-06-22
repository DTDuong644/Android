package com.example.tlu_rideshare.passenger;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tlu_rideshare.model.Trip;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class TripViewModel extends ViewModel {

    private final MutableLiveData<List<Trip>> tripList = new MutableLiveData<>(new ArrayList<>());

    private final MutableLiveData<String> yourLocation = new MutableLiveData<>();
    private final MutableLiveData<String> destination = new MutableLiveData<>();
    private final MutableLiveData<String> selectedDate = new MutableLiveData<>();

    public TripViewModel() {
        fetchTripsFromFirebase();
    }

    private void fetchTripsFromFirebase() {
        FirebaseFirestore.getInstance().collection("trips")
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        Log.e("TripViewModel", "Lỗi Firestore: " + error.getMessage());
                        return;
                    }

                    if (value != null) {
                        List<Trip> trips = new ArrayList<>();
                        for (QueryDocumentSnapshot doc : value) {
                            Trip trip = doc.toObject(Trip.class);
                            trips.add(trip);
                        }
                        tripList.postValue(trips);
                    }
                });
    }
    public void updateTrip(Trip updatedTrip) {
        List<Trip> currentTrips = tripList.getValue();
        if (currentTrips == null) return;

        for (int i = 0; i < currentTrips.size(); i++) {
            if (currentTrips.get(i).getTripID().equals(updatedTrip.getTripID())) {
                currentTrips.set(i, updatedTrip);
                break;
            }
        }

        tripList.setValue(new ArrayList<>(currentTrips)); // 🔥 Đặt lại để kích hoạt observer
    }

    public LiveData<List<Trip>> getTripList() {
        return tripList;
    }

    public void setYourLocation(String location) {
        yourLocation.setValue(location);
    }

    public LiveData<String> getYourLocation() {
        return yourLocation;
    }

    public void setDestination(String dest) {
        destination.setValue(dest);
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
}
