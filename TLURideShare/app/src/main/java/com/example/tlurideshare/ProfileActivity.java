package com.example.tlurideshare;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;
import android.content.Intent;

public class ProfileActivity extends AppCompatActivity {

    private ImageView profileImage;
    private TextView userNameText;
    private TextView userStatusText;
    private Button editProfileButton;
    private TextView travelHistoryItem;
    private TextView yourReviewsItem;
    private TextView accountStatusItem;
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.profile_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize views
        profileImage = findViewById(R.id.profile_image);
        userNameText = findViewById(R.id.user_name_text);
        userStatusText = findViewById(R.id.user_status_text);
        editProfileButton = findViewById(R.id.edit_profile_button);
        travelHistoryItem = findViewById(R.id.travel_history_item);
        yourReviewsItem = findViewById(R.id.your_reviews_item);
        accountStatusItem = findViewById(R.id.account_status_item);
        logoutButton = findViewById(R.id.logout_button);

        // Set up click listeners (for now, just show a Toast or log)
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                startActivity(intent);
            }
        });

        travelHistoryItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, AverageRatingActivity.class);
                startActivity(intent);
            }
        });

        yourReviewsItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, YourReviewsActivity.class);
                startActivity(intent);
            }
        });

        accountStatusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, AccountStatusActivity.class);
                startActivity(intent);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Handle logout click
                // Example: Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                // startActivity(intent);
                // finishAffinity(); // Close all previous activities
            }
        });

        // You can set user data here, e.g., from a database or shared preferences
        // userNameText.setText("User's Name");
        // userStatusText.setText("User's Status");
        // profileImage.setImageResource(R.drawable.your_profile_image);
    }
} 