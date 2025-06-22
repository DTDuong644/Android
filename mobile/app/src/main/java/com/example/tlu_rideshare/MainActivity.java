package com.example.tlu_rideshare;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.tlu_rideshare.R;
import com.example.tlu_rideshare.passenger.AccountFragment;
import com.example.tlu_rideshare.passenger.HomeFragment;
import com.example.tlu_rideshare.passenger.ListFragment;
import com.example.tlu_rideshare.passenger.MoveFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    ImageView[] icons = new ImageView[4];
    TextView[] texts = new TextView[4];
    LinearLayout tab1, tab2, tab3, tab4;

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

        handleIntent(getIntent());
        checkFirestoreConnection();
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

    private void checkFirestoreConnection() {
        FirebaseFirestore.getInstance().collection("trips").limit(1).get()
                .addOnSuccessListener(querySnapshot -> Log.d("FIREBASE_CONN", "✅ Kết nối Firestore thành công"))
                .addOnFailureListener(e -> Log.e("FIREBASE_CONN", "❌ Kết nối Firestore thất bại: " + e.getMessage()));
    }


}
