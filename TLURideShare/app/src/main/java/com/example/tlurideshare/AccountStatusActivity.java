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
import android.widget.ImageButton;

public class AccountStatusActivity extends AppCompatActivity {

    private ImageView profileImageStatus;
    private TextView userNameStatusText;
    private TextView accountStatusMessage;
    private Button verifyNowButton;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_account_status);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.account_status_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize views
        profileImageStatus = findViewById(R.id.profile_image_status);
        userNameStatusText = findViewById(R.id.user_name_status_text);
        accountStatusMessage = findViewById(R.id.account_status_message);
        verifyNowButton = findViewById(R.id.verify_now_button);
        backButton = findViewById(R.id.back_button);

        // Set up click listener for back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Close the current activity
            }
        });

        // Set up click listener for verify button
        verifyNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Handle verify now click, e.g., navigate to ID card capture
                // Example: Intent intent = new Intent(AccountStatusActivity.this, CaptureIDCardActivity.class);
                // startActivity(intent);
                finish(); // Close the current activity
            }
        });

        // You can set user data here if needed
        // userNameStatusText.setText("User's Name");
        // profileImageStatus.setImageResource(R.drawable.your_profile_image);
    }
} 