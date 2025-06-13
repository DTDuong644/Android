package com.example.tlu_rideshare.passenger;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tlu_rideshare.R;

import java.util.ArrayList;
import java.util.List;

public class LocationDialogFragment extends DialogFragment {
    private RecyclerView recyclerView;
    private SelectLocationAdapter locationAdapter;
    private List<String> locationList;
    private Trip trip;
    private boolean isYourLocation;
    private SelectLocationAdapter.OnLocationSelectedListener listener;

    public LocationDialogFragment() {
        // Required empty public constructor
    }

    public static LocationDialogFragment newInstance(boolean isYourLocation, Trip trip) {
        LocationDialogFragment fragment = new LocationDialogFragment();
        Bundle args = new Bundle();
        args.putBoolean("isYourLocation", isYourLocation);
        fragment.setArguments(args);
        fragment.trip = trip;
        return fragment;
    }

    public void setOnLocationSelectedListener(SelectLocationAdapter.OnLocationSelectedListener listener) {
        this.listener = listener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.select_location, container, false);

        // Khởi tạo RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewLocations);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Lấy thông tin từ Bundle để xác định chọn yourLocation hay destination
        if (getArguments() != null) {
            isYourLocation = getArguments().getBoolean("isYourLocation", true);
        }

        // Tạo danh sách các vị trí gợi ý
        locationList = new ArrayList<>();
        locationList.add("Trường Đại học Thủy lợi - Hà Nội");
        // Hà Nội
        locationList.add("Bến xe Mỹ Đình - Hà Nội");
        locationList.add("Bến xe Giáp Bát - Hà Nội");
        locationList.add("Bến xe Nước Ngầm - Hà Nội");
        locationList.add("Bến xe Gia Lâm - Hà Nội");
        locationList.add("Bến xe Yên Nghĩa - Hà Nội");
        locationList.add("Bến xe Sơn Tây - Hà Nội");

// Hải Phòng
        locationList.add("Bến xe Niệm Nghĩa - Hải Phòng");
        locationList.add("Bến xe Cầu Rào - Hải Phòng");
        locationList.add("Bến xe Thượng Lý - Hải Phòng");
        locationList.add("Bến xe Lạc Long - Hải Phòng");

// Quảng Ninh
        locationList.add("Bến xe Bãi Cháy - Quảng Ninh");
        locationList.add("Bến xe Cẩm Phả - Quảng Ninh");
        locationList.add("Bến xe Móng Cái - Quảng Ninh");
        locationList.add("Bến xe Uông Bí - Quảng Ninh");

// Bắc Ninh
        locationList.add("Bến xe Bắc Ninh - Bắc Ninh");
        locationList.add("Bến xe Lim - Bắc Ninh");

// Bắc Giang
        locationList.add("Bến xe Bắc Giang - Bắc Giang");
        locationList.add("Bến xe Kép - Bắc Giang");

// Thái Nguyên
        locationList.add("Bến xe trung tâm - Thái Nguyên");
        locationList.add("Bến xe phía Nam - Thái Nguyên");

// Lào Cai
        locationList.add("Bến xe Lào Cai - Lào Cai");
        locationList.add("Bến xe Bát Xát - Lào Cai");

// Yên Bái
        locationList.add("Bến xe Yên Bái - Yên Bái");
        locationList.add("Bến xe Nghĩa Lộ - Yên Bái");

// Phú Thọ
        locationList.add("Bến xe Việt Trì - Phú Thọ");
        locationList.add("Bến xe Thanh Ba - Phú Thọ");

// Tuyên Quang
        locationList.add("Bến xe Tuyên Quang - Tuyên Quang");

// Hà Giang
        locationList.add("Bến xe Hà Giang - Hà Giang");

// Cao Bằng
        locationList.add("Bến xe Cao Bằng - Cao Bằng");

// Lạng Sơn
        locationList.add("Bến xe phía Bắc - Lạng Sơn");
        locationList.add("Bến xe Đông Kinh - Lạng Sơn");

// Sơn La
        locationList.add("Bến xe Sơn La - Sơn La");

// Lai Châu
        locationList.add("Bến xe Lai Châu - Lai Châu");

// Điện Biên
        locationList.add("Bến xe Điện Biên Phủ - Điện Biên");

// Hòa Bình
        locationList.add("Bến xe Hòa Bình - Hòa Bình");

// Ninh Bình
        locationList.add("Bến xe Ninh Bình - Ninh Bình");
        locationList.add("Bến xe Kim Sơn - Ninh Bình");

// Thanh Hóa
        locationList.add("Bến xe phía Bắc - Thanh Hóa");
        locationList.add("Bến xe phía Nam - Thanh Hóa");
        locationList.add("Bến xe thị xã Bỉm Sơn - Thanh Hóa");
        locationList.add("Bến xe thị xã Sầm Sơn - Thanh Hóa");

// Nghệ An
        locationList.add("Bến xe Vinh - Nghệ An");
        locationList.add("Bến xe Bắc Vinh - Nghệ An");
        locationList.add("Bến xe Nam Vinh - Nghệ An");
        locationList.add("Bến xe Cửa Lò - Nghệ An");


        // Khởi tạo và gán Adapter cho RecyclerView
        locationAdapter = new SelectLocationAdapter(locationList, trip, isYourLocation, new SelectLocationAdapter.OnLocationSelectedListener() {
            @Override
            public void onLocationSelected(String location, boolean isYourLocation) {
                // Gọi listener để thông báo về MoveFragment
                if (listener != null) {
                    listener.onLocationSelected(location, isYourLocation);
                }
                // Đóng DialogFragment sau khi chọn
                dismiss();
            }
        });
        recyclerView.setAdapter(locationAdapter);

        ImageView imgX = view.findViewById(R.id.imgX);
        imgX.setOnClickListener(v -> {
            dismiss();
        });

        return view;
    }
}
