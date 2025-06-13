package com.example.tlu_rideshare.passenger;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tlu_rideshare.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MoveFragment extends Fragment implements SelectLocationAdapter.OnLocationSelectedListener {

    private Button btnYourLocation;
    private Button btnDestination;
    private Button btnTime;
    private Button buttonSearch;
    private Trip trip;
    private TripViewModel tripViewModel;
    private Calendar selectedDateTime;
    private SimpleDateFormat dateFormat; // Định dạng ngày (dd/MM/yyyy)
    private SimpleDateFormat timeFormat; // Định dạng giờ (hh:mm a)

    public MoveFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.move_fragment_passenger_layout, container, false);

        // Khởi tạo ViewModel
        tripViewModel = new ViewModelProvider(requireActivity()).get(TripViewModel.class);

        // Khởi tạo định dạng ngày và giờ
        dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        timeFormat = new SimpleDateFormat("hh:mm", Locale.getDefault());

        // Khởi tạo các thành phần giao diện
        btnYourLocation = view.findViewById(R.id.btnYourLocation);
        btnDestination = view.findViewById(R.id.btnDestination);
        btnTime = view.findViewById(R.id.btnTime);
        buttonSearch = view.findViewById(R.id.buttonSearch);

        // Khởi tạo đối tượng Trip với time là chuỗi chứa cả ngày và giờ
        trip = new Trip("Nguyễn Văn A", "19:33, 07/06/2025", 150000, "Xe 16 chỗ");

        // Khởi tạo Calendar với thời gian hiện tại
        selectedDateTime = Calendar.getInstance();
        selectedDateTime.setTime(new Date()); // Đặt thời gian hiện tại là 05:33 PM, 06/06/2025

        // Mở LocationDialogFragment khi nhấn vào Vị trí của bạn
        btnYourLocation.setOnClickListener(v -> {
            LocationDialogFragment dialog = LocationDialogFragment.newInstance(true, trip);
            dialog.setOnLocationSelectedListener(this);
            dialog.show(getParentFragmentManager(), "location_picker");
        });

        // Mở LocationDialogFragment khi nhấn vào Điểm đến
        btnDestination.setOnClickListener(v -> {
            LocationDialogFragment dialog = LocationDialogFragment.newInstance(false, trip);
            dialog.setOnLocationSelectedListener(this);
            dialog.show(getParentFragmentManager(), "location_picker");
        });

        // Hiển thị DatePickerDialog khi nhấn vào btnTime
        btnTime.setOnClickListener(v -> btnShowSetDate_click(v));

        buttonSearch.setOnClickListener(v -> {
            // Kiểm tra xem người dùng đã chọn vị trí và điểm đến chưa
            if (trip.getYourLocation() == null || trip.getDestination() == null || trip.getTime() == null) {
                Toast.makeText(getContext(), "Vui lòng chọn vị trí, điểm đến, Thời gian", Toast.LENGTH_SHORT).show();
                return;
            }

            // Kiểm tra ngày có hợp lệ không (không nằm trong quá khứ)
            try {
                String currentDateStr = dateFormat.format(new Date());
                String tripTimeParts[] = trip.getTime().split(", ");
                String tripDateStr = tripTimeParts[1]; // Lấy phần ngày (ví dụ: "07/06/2025")
                Date currentDate = dateFormat.parse(currentDateStr);
                Date tripDate = dateFormat.parse(tripDateStr);
                if (tripDate.before(currentDate)) {
                    Toast.makeText(getContext(), "Không thể chọn ngày trong quá khứ!", Toast.LENGTH_SHORT).show();
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Lỗi khi kiểm tra ngày. Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Lưu dữ liệu người dùng nhập vào ViewModel
            String tripTimeParts[] = trip.getTime().split(", ");
            String tripDateStr = tripTimeParts[1]; // Lấy ngày (ví dụ: "07/06/2025")
            tripViewModel.setSelectedDate(tripDateStr);
            tripViewModel.setYourLocation(trip.getYourLocation()); // Lưu vị trí bắt đầu
            tripViewModel.setDestination(trip.getDestination());   // Lưu điểm đến

            // Chuyển sang SuitableTripFragment bằng FragmentTransaction
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.tabContent, new SuitableTripFragment())
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }

    // Phương thức được gọi khi nhấn btnTime
    public void btnShowSetDate_click(View view) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // Cập nhật ngày vào Calendar
                selectedDateTime.set(year, month, dayOfMonth);

                // Định dạng ngày đã chọn
                String selectedDateStr = dateFormat.format(selectedDateTime.getTime());

                // Lấy giờ hiện tại từ trip.time (phần trước dấu ", ")
                String currentTimeStr = trip.getTime().split(", ")[0]; // Lấy phần giờ (ví dụ: "05:33 PM")

                // Cập nhật time với ngày mới và giờ hiện tại
                trip.setTime(currentTimeStr + ", " + selectedDateStr); // Ví dụ: "05:33 PM, 07/06/2025"

                // Cập nhật văn bản của btnTime để hiển thị ngày đã chọn
                btnTime.setText(selectedDateStr);
            }
        }, year, month, day);

        datePickerDialog.show();
    }

    @Override
    public void onLocationSelected(String location, boolean isYourLocation) {
        if (isYourLocation) {
            btnYourLocation.setText(location);
        } else {
            btnDestination.setText(location);
        }
    }
}