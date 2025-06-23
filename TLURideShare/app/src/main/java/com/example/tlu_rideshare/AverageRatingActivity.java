package com.example.tlurideshare;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageButton;
import android.view.View;

public class AverageRatingActivity extends AppCompatActivity {

    private ImageButton backButtonRating;
    private ImageView profileImageRating;
    private TextView userNameRatingText;
    private TextView userSloganText;
    private TextView averageRatingValue;
    private TextView numberOfReviewsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_average_rating);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.average_rating_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize views
        backButtonRating = findViewById(R.id.back_button_rating);
        profileImageRating = findViewById(R.id.profile_image_rating);
        userNameRatingText = findViewById(R.id.user_name_rating_text);
        userSloganText = findViewById(R.id.user_slogan_text);
        averageRatingValue = findViewById(R.id.average_rating_value);
        numberOfReviewsText = findViewById(R.id.number_of_reviews_text);

        // Set up click listener for back button
        backButtonRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Close the current activity
            }
        });

        // You can set user data here, e.g., from a database or shared preferences
        // userNameRatingText.setText("User's Name");
        // userSloganText.setText("User's Slogan");
        // averageRatingValue.setText("4.5/5.0");
        // numberOfReviewsText.setText("10 đánh giá");
        // profileImageRating.setImageResource(R.drawable.your_profile_image);
    }
} 