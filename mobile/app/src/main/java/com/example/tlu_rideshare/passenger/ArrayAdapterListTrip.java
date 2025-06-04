package com.example.tlu_rideshare.passenger;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tlu_rideshare.R;

import java.util.List;

public class ArrayAdapterListTrip extends RecyclerView.Adapter<ArrayAdapterListTrip.ListTripViewHolder> {
    private List<Trip> tripList;

    public ArrayAdapterListTrip(List<Trip> tripList) {
        this.tripList = tripList;
    }

    public static class ListTripViewHolder extends RecyclerView.ViewHolder {
        TextView txtSchedule, txtPhoneNumber, txtLicensePlate, txtTime, txtNumOfChair, txtPrice, txtNameDriver;

        public ListTripViewHolder(View itemView) {
            super(itemView);
            txtSchedule = itemView.findViewById(R.id.txtSchedule);
            txtNameDriver = itemView.findViewById(R.id.txtNameDriver);
            txtPhoneNumber = itemView.findViewById(R.id.txtPhoneNumber);
            txtLicensePlate = itemView.findViewById(R.id.txtLicensePlate);
            txtTime = itemView.findViewById(R.id.txtTime);
            txtNumOfChair = itemView.findViewById(R.id.txtNumOfChair);
            txtPrice = itemView.findViewById(R.id.txtPrice);
        }
    }

    @Override
    public ListTripViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trip_list_layout, parent, false);
        return new ListTripViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListTripViewHolder holder, int position) {
        Trip trip = tripList.get(position);
        holder.txtSchedule.setText("Tuyến xe: " + trip.schedule);
        holder.txtNameDriver.setText("Tài xế: " + trip.driverName);
        holder.txtPhoneNumber.setText("Số điện thoại: " + trip.phoneNumber);
        holder.txtLicensePlate.setText("Biển số xe: " + trip.licensePlate);
        holder.txtTime.setText("Thời gian khởi hành: " + trip.time);
        holder.txtNumOfChair.setText("Số ghế: " + trip.numOfChair);
        holder.txtPrice.setText("Giá vé: " + trip.price + " VNĐ");
    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }
}
