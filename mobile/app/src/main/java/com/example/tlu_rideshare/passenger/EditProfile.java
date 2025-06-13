package com.example.tlu_rideshare.passenger;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tlu_rideshare.R;

public class EditProfile extends AppCompatActivity {

    private EditText editTextFullName, editTextDescrip, editTextEmail, editTextHometown, editTextPhoneNumber;
    private ImageView imgAvt;
    private ActivityResultLauncher<String> requestPermissionLauncher;
    private ActivityResultLauncher<Intent> galleryLauncher;
    private ActivityResultLauncher<Intent> cameraLauncher;
    private String lastRequestedPermission;
    private Uri selectedImageUri; // Lưu Uri của ảnh được chọn

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_layout);

        // Khởi tạo các view
        ImageView imgBackEditProfile = findViewById(R.id.imgBackAccountStatus);
        imgAvt = findViewById(R.id.imgAvtStatus);
        editTextFullName = findViewById(R.id.editTextText);
        editTextDescrip = findViewById(R.id.editTextText5);
        editTextEmail = findViewById(R.id.editTextText2);
        editTextHometown = findViewById(R.id.editTextText3);
        editTextPhoneNumber = findViewById(R.id.editTextText4);
        Button btnSave = findViewById(R.id.btnSave);

        // Khởi tạo ActivityResultLauncher cho quyền
        requestPermissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                isGranted -> {
                    if (isGranted) {
                        if (Manifest.permission.READ_EXTERNAL_STORAGE.equals(lastRequestedPermission)) {
                            openGallery();
                        } else if (Manifest.permission.CAMERA.equals(lastRequestedPermission)) {
                            openCamera();
                        }
                    } else {
                        Toast.makeText(this, "Quyền bị từ chối", Toast.LENGTH_SHORT).show();
                    }
                });

        // Khởi tạo ActivityResultLauncher cho thư viện ảnh
        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        selectedImageUri = result.getData().getData();
                        if (selectedImageUri != null) {
                            imgAvt.setImageURI(selectedImageUri);
                        }
                    }
                });

        // Khởi tạo ActivityResultLauncher cho máy ảnh
        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Bitmap bitmap = (Bitmap) result.getData().getExtras().get("data");
                        if (bitmap != null) {
                            imgAvt.setImageBitmap(bitmap);
                            // Lưu Bitmap thành Uri (giả định lưu tạm thời)
                            selectedImageUri = getImageUri(bitmap);
                            imgAvt.setImageURI(selectedImageUri);
                        }
                    }
                });

        // Lấy dữ liệu từ Intent
        Intent intent = getIntent();
        editTextFullName.setText(intent.getStringExtra("currentFullName"));
        editTextDescrip.setText(intent.getStringExtra("currentDescrip"));
        editTextEmail.setText(intent.getStringExtra("currentEmail"));
        editTextHometown.setText(intent.getStringExtra("currentHometown"));
        editTextPhoneNumber.setText(intent.getStringExtra("currentPhoneNumber"));
        String avatarUri = intent.getStringExtra("currentAvatar");
        if (avatarUri != null && !avatarUri.isEmpty()) {
            selectedImageUri = Uri.parse(avatarUri);
            imgAvt.setImageURI(selectedImageUri);
        }

        // Xử lý sự kiện nhấn imgAvt
        imgAvt.setOnClickListener(v -> showImagePickerDialog());

        // Xử lý sự kiện nhấn nút quay lại
        imgBackEditProfile.setOnClickListener(v -> finish());

        // Xử lý sự kiện nhấn nút lưu
        btnSave.setOnClickListener(v -> {
            // Lấy dữ liệu đã cập nhật
            String updatedFullName = editTextFullName.getText().toString();
            String updatedDescrip = editTextDescrip.getText().toString();
            String updatedEmail = editTextEmail.getText().toString();
            String updatedHometown = editTextHometown.getText().toString();
            String updatedPhoneNumber = editTextPhoneNumber.getText().toString();
            String updatedAvatar = selectedImageUri != null ? selectedImageUri.toString() : "";

            // Trả dữ liệu về AccountFragment
            Intent resultIntent = new Intent();
            resultIntent.putExtra("updatedFullName", updatedFullName);
            resultIntent.putExtra("updatedDescrip", updatedDescrip);
            resultIntent.putExtra("updatedEmail", updatedEmail);
            resultIntent.putExtra("updatedHometown", updatedHometown);
            resultIntent.putExtra("updatedPhoneNumber", updatedPhoneNumber);
            resultIntent.putExtra("updatedAvatar", updatedAvatar);
            setResult(RESULT_OK, resultIntent);
            finish();
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void showImagePickerDialog() {
        String[] options = {"Chọn từ thư viện", "Chụp ảnh"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chọn ảnh đại diện");
        builder.setItems(options, (dialog, which) -> {
            if (which == 0) {
                checkStoragePermission();
            } else {
                checkCameraPermission();
            }
        });
        builder.show();
    }

    private void checkStoragePermission() {
        lastRequestedPermission = Manifest.permission.READ_EXTERNAL_STORAGE;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
        } else {
            openGallery();
        }
    }

    private void checkCameraPermission() {
        lastRequestedPermission = Manifest.permission.CAMERA;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA);
        } else {
            openCamera();
        }
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryLauncher.launch(intent);
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            cameraLauncher.launch(intent);
        } else {
            Toast.makeText(this, "Không tìm thấy ứng dụng máy ảnh", Toast.LENGTH_SHORT).show();
        }
    }

    private Uri getImageUri(Bitmap bitmap) {
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Avatar", null);
        return Uri.parse(path);
    }
}