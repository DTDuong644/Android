package com.example.tlu_rideshare;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

public class EditProfileActivity extends AppCompatActivity {

    private ImageView editProfileImage;
    private TextView changePhotoText;
    private EditText displayNameEditText;
    private EditText emailEditText;
    private EditText hometownEditText;
    private EditText phoneEditText;
    private EditText descriptionEditText;
    private Button saveChangesButton;

    private ImageButton backButton;

    private ActivityResultLauncher<String> pickImageLauncher;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.edit_profile_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize views
        editProfileImage = findViewById(R.id.edit_profile_image);
        changePhotoText = findViewById(R.id.change_photo_text);
        displayNameEditText = findViewById(R.id.display_name_edit_text);
        emailEditText = findViewById(R.id.email_edit_text);
        hometownEditText = findViewById(R.id.hometown_edit_text);
        phoneEditText = findViewById(R.id.phone_edit_text);
        descriptionEditText = findViewById(R.id.description_edit_text);
        saveChangesButton = findViewById(R.id.save_changes_button);
        backButton = findViewById(R.id.back_button);

        // Register ActivityResultLauncher for picking images
        pickImageLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> {
                    if (uri != null) {
                        editProfileImage.setImageURI(uri);
                    }
                }
        );

        // Set up click listener for back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Close the current activity
            }
        });

        // Set up click listener for save changes button
        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Handle save changes click, e.g., save data to database
                finish(); // Close the current activity
            }
        });

        // Implement logic for changing photo
        changePhotoText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImageLauncher.launch("image/*"); // Open image picker
            }
        });
    }
} 