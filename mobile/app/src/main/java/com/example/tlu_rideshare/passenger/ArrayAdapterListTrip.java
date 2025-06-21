package com.example.tlu_rideshare.passenger;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tlu_rideshare.R;
import com.example.tlu_rideshare.model.Trip;

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

    @NonNull
    @Override
    public ListTripViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trip_list_layout, parent, false);
        return new ListTripViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListTripViewHolder holder, int position) {
        Trip trip = tripList.get(position);
        holder.txtSchedule.setText("Tuyến xe: " + trip.getYourLocation() + " -> " + trip.getDestination());
        holder.txtNameDriver.setText("Tài xế: " + trip.getDriverName());
        holder.txtPhoneNumber.setText("Số điện thoại: " + trip.getPhoneNumber());
        holder.txtLicensePlate.setText("Biển số xe: " + trip.getLicensePlate());
        // Hiển thị thời gian (time đã là chuỗi chứa cả ngày và giờ)
        holder.txtTime.setText("Thời gian khởi hành: " + trip.getTime());
        holder.txtNumOfChair.setText("Số ghế: " + trip.getEmptyChair());
        holder.txtPrice.setText("Giá vé: " + trip.getPrice() + " VNĐ");
    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }
}