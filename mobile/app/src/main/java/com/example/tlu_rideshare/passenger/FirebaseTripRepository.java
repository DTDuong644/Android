package com.example.tlu_rideshare.passenger;

import com.example.tlu_rideshare.model.Trip;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public class FirebaseTripRepository {
    private final DatabaseReference tripRef = FirebaseDatabase.getInstance()
            .getReference("trips");

    public void addTrip(Trip trip) {
        tripRef.child(trip.getTripID()).setValue(trip);
    }

    public void updateTrip(Trip trip) {
        tripRef.child(trip.getTripID()).setValue(trip);
    }

    public void getAllTrips(ValueEventListener listener) {
        tripRef.addValueEventListener(listener);
    }
}

