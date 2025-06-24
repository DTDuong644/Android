package com.example.tlu_rideshare;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tlu_rideshare.R;

import java.util.ArrayList;
import java.util.List;

public class SelectOtherLocation extends AppCompatActivity {

    private ListView listView;
    private EditText edtSearch;
    private ArrayAdapter<String> adapter;
    private List<String> locationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_other_location);

        listView = findViewById(R.id.list_location);
        edtSearch = findViewById(R.id.edt_search);

        locationList = new ArrayList<>();
        
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
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, locationList);
        listView.setAdapter(adapter);

        // Bắt sự kiện click vào item
        listView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedLocation = adapter.getItem(position);
            Intent resultIntent = new Intent();
            resultIntent.putExtra("selected_location", selectedLocation); // Gửi kết quả về
            setResult(RESULT_OK, resultIntent);
            finish(); // Đóng màn này
        });

        // Optional: Tìm kiếm
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s); // Lọc danh sách
            }
            @Override public void afterTextChanged(Editable s) {}
        });
    }
}
