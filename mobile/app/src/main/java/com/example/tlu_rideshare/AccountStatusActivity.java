package com.example.tlu_rideshare;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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

        // Apply padding for system bars (status bar, navigation bar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.account_status_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize views by ID
        profileImageStatus = findViewById(R.id.profile_image_status);
        userNameStatusText = findViewById(R.id.user_name_status_text);
        accountStatusMessage = findViewById(R.id.account_status_message);
        verifyNowButton = findViewById(R.id.verify_now_button);
        backButton = findViewById(R.id.back_button);

        // Handle back button click: return to the previous screen
        backButton.setOnClickListener(v -> finish());

        // Handle "Verify Now" button click: go to SendOTPActivity
        verifyNowButton.setOnClickListener(v -> {
            Intent intent = new Intent(AccountStatusActivity.this, SendOTPActivity.class);
            startActivity(intent);
        });

        // Optional: Set user info dynamically if needed
        // userNameStatusText.setText("User Name");
        // profileImageStatus.setImageResource(R.drawable.your_profile_image);
    }
}
