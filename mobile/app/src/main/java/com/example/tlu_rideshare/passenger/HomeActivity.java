package com.example.tlu_rideshare.passenger;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tlu_rideshare.R;
import com.example.tlu_rideshare.model.Trip;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    ImageView[] icons = new ImageView[4];
    TextView[] texts = new TextView[4];
    LinearLayout tab1, tab2, tab3, tab4;
    private boolean isSampleDataAdded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.tab_home_passenger_layout);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TripViewModel tripViewModel = new ViewModelProvider(this).get(TripViewModel.class);

        if (!isSampleDataAdded) {
            List<Trip> sampleTrips = new ArrayList<>();
            try {
                sampleTrips.add(new Trip("Nguyễn Văn A", "51H-123.45", 2, "0909123456", 300000,
                        "Trường Đại học Thủy lợi - Hà Nội", "Bến xe Mỹ Đình - Hà Nội", "18:00, 10/06/2025", "Xe máy", false));
                sampleTrips.add(new Trip("Nguyễn Văn B", "51B-456.78", 10, "0912345678", 300000,
                        "Đại học Thủy Lợi", "Bến xe Mỹ Đình", "18:00, 10/06/2025", "Ô tô", false));
                sampleTrips.add(new Trip("Nguyễn Văn A", "51H-123.45", 2, "0909123456", 150000,
                        "Bến xe Mỹ Đình - Hà Nội", "Bến xe Giáp Bát - Hà Nội", "08:00, 07/06/2025", "Ô tô", false));
                sampleTrips.add(new Trip("Trần Văn B", "51B-456.78", 3, "0912345678", 200000,
                        "TP. Hồ Chí Minh", "Nha Trang", "10:30, 06/06/2025", "Ô tô", false));
            } catch (Exception e) {
                e.printStackTrace();
            }

            for (Trip sampleTrip : sampleTrips) {
                tripViewModel.addTrip(sampleTrip);
            }
            isSampleDataAdded = true;
        }

        tab1 = findViewById(R.id.tab1);
        tab2 = findViewById(R.id.tab2);
        tab3 = findViewById(R.id.tab3);
        tab4 = findViewById(R.id.tab4);

        icons[0] = findViewById(R.id.icon_home);
        icons[1] = findViewById(R.id.icon_car);
        icons[2] = findViewById(R.id.icon_list);
        icons[3] = findViewById(R.id.icon_account);

        texts[0] = findViewById(R.id.txt_home);
        texts[1] = findViewById(R.id.txt_car);
        texts[2] = findViewById(R.id.txt_list);
        texts[3] = findViewById(R.id.txt_account);

        loadFragment(new HomeFragment());
        updateTabUI(0);

        tab1.setOnClickListener(v -> switchToTab(0));
        tab2.setOnClickListener(v -> switchToTab(1));
        tab3.setOnClickListener(v -> switchToTab(2));
        tab4.setOnClickListener(v -> switchToTab(3));

        // Xử lý Intent để chọn tab
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (intent.hasExtra("selected_tab")) {
            int tabIndex = intent.getIntExtra("selected_tab", 0);
            switchToTab(tabIndex);
        }
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.tabContent, fragment)
                .commit();
    }

    private void updateTabUI(int selectedTab) {
        String colorSelected = "#007BFF";
        String colorDefault = "#000000";

        for (int i = 0; i < icons.length; i++) {
            if (i == selectedTab) {
                icons[i].setColorFilter(Color.parseColor(colorSelected));
                texts[i].setTextColor(Color.parseColor(colorSelected));
            } else {
                icons[i].setColorFilter(Color.parseColor(colorDefault));
                texts[i].setTextColor(Color.parseColor(colorDefault));
            }
        }
    }

    public void switchToTab(int tabIndex) {
        switch (tabIndex) {
            case 0:
                loadFragment(new HomeFragment());
                break;
            case 1:
                loadFragment(new MoveFragment());
                break;
            case 2:
                loadFragment(new ListFragment());
                break;
            case 3:
                loadFragment(new AccountFragment());
                break;
            default:
                loadFragment(new HomeFragment());
                break;
        }
        updateTabUI(tabIndex);
    }
}