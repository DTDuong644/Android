package com.example.tlu_rideshare.passenger;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.tlu_rideshare.R;
import com.example.tlu_rideshare.model.FeedBack;
import com.example.tlu_rideshare.model.User;
import com.example.tlu_rideshare.model.Trip;

public class AccountFragment extends Fragment {

    private static final int PERMISSION_REQUEST_CODE = 102;
    private ImageView imgAvatar;
    private TextView txtFullName, txtDescri;
    private Button btnHistoryList, btnRate, btnRepairResume, btnActivityAccount;
    private ActivityResultLauncher<String> requestPermissionLauncher;
    private ActivityResultLauncher<Intent> galleryLauncher;
    private ActivityResultLauncher<Intent> cameraLauncher;
    private ActivityResultLauncher<Intent> editProfileLauncher;
    private User customer;
    private String lastRequestedPermission;

    public AccountFragment() {
        // Constructor rỗng
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String defaultAvatarUri = "android.resource://" + requireContext().getPackageName() + "/" + R.drawable.avatar;

        String avatar;
        try {
            avatar = prefs.getString("avatar", defaultAvatarUri);
        } catch (ClassCastException e) {
            int oldAvatar = prefs.getInt("avatar", 0);
            avatar = oldAvatar != 0 ? "android.resource://" + requireContext().getPackageName() + "/" + oldAvatar : defaultAvatarUri;
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("avatar", avatar);
            editor.apply();
        }

        // ✅ Đọc customerId từ SharedPreferences
        String customerId = prefs.getString("customerId", "unknown_id");

        boolean isVerified = prefs.getBoolean("isAccountVerified", false);

        customer = new User(
                customerId,
                avatar,
                prefs.getString("descrip", "Chưa có mô tả"),
                prefs.getString("email", "email@example.com"),
                prefs.getString("fullName", "Tên người dùng"),
                prefs.getString("hometown", "Chưa có quê quán"),
                prefs.getString("phoneNumber", "0123456789"),
                isVerified
        );

        // Các launcher như cũ
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
                        Toast.makeText(requireContext(), "Quyền bị từ chối", Toast.LENGTH_SHORT).show();
                    }
                });

        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri imageUri = result.getData().getData();
                        if (imageUri != null) {
                            imgAvatar.setImageURI(imageUri);
                            customer.setAvatar(imageUri.toString());
                            saveCustomerData(customer);
                        }
                    }
                });

        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Bitmap bitmap = (Bitmap) result.getData().getExtras().get("data");
                        if (bitmap != null) {
                            Uri imageUri = getImageUri(bitmap);
                            imgAvatar.setImageURI(imageUri);
                            customer.setAvatar(imageUri.toString());
                            saveCustomerData(customer);
                        }
                    }
                });

        editProfileLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        String updatedFullName = result.getData().getStringExtra("updatedFullName");
                        String updatedDescrip = result.getData().getStringExtra("updatedDescrip");
                        String updatedEmail = result.getData().getStringExtra("updatedEmail");
                        String updatedHometown = result.getData().getStringExtra("updatedHometown");
                        String updatedPhoneNumber = result.getData().getStringExtra("updatedPhoneNumber");
                        String updatedAvatar = result.getData().getStringExtra("updatedAvatar");

                        if (updatedFullName != null) {
                            customer.setFullName(updatedFullName);
                            txtFullName.setText(updatedFullName);
                        }
                        if (updatedDescrip != null) {
                            customer.setDescrip(updatedDescrip);
                            txtDescri.setText(updatedDescrip);
                        }
                        if (updatedEmail != null) {
                            customer.setEmail(updatedEmail);
                        }
                        if (updatedHometown != null) {
                            customer.setHometown(updatedHometown);
                        }
                        if (updatedPhoneNumber != null) {
                            customer.setPhoneNumber(updatedPhoneNumber);
                        }
                        if (updatedAvatar != null && !updatedAvatar.isEmpty()) {
                            customer.setAvatar(updatedAvatar);
                            imgAvatar.setImageURI(Uri.parse(updatedAvatar));
                        }

                        saveCustomerData(customer);
                    }
                });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.account_fragment_passenger_layout, container, false);

        imgAvatar = view.findViewById(R.id.imgAvatar);
        txtFullName = view.findViewById(R.id.txtFullName);
        txtDescri = view.findViewById(R.id.txtDescri);
        btnRepairResume = view.findViewById(R.id.btnRepairResume);
        btnHistoryList = view.findViewById(R.id.btnHistoryList);
        btnRate = view.findViewById(R.id.btnYourRate);
        btnActivityAccount = view.findViewById(R.id.btnActivityAccount);

        txtFullName.setText(customer.getFullName());
        txtDescri.setText(customer.getDescrip());
        if (customer.getAvatar() != null && !customer.getAvatar().isEmpty()) {
            imgAvatar.setImageURI(Uri.parse(customer.getAvatar()));
        }

        imgAvatar.setOnClickListener(v -> showImagePickerDialog());

        btnRepairResume.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), EditProfile.class);
            intent.putExtra("currentFullName", customer.getFullName());
            intent.putExtra("currentDescrip", customer.getDescrip());
            intent.putExtra("currentEmail", customer.getEmail());
            intent.putExtra("currentHometown", customer.getHometown());
            intent.putExtra("currentPhoneNumber", customer.getPhoneNumber());
            intent.putExtra("currentAvatar", customer.getAvatar());
            editProfileLauncher.launch(intent);
        });

        btnHistoryList.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), HistoryListActivity.class);
            startActivity(intent);
        });

        btnRate.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), YourRatingActivity.class);

            HistoryListActivity history = HistoryListActivity.getInstance();
            if (history != null && !history.getFeedbackList().isEmpty()) {
                FeedBack feedback = history.getFeedbackList().get(0); // Lấy feedback đầu tiên

                intent.putExtra("driverName", feedback.getDriverID());
                intent.putExtra("rating", feedback.getRating());
                intent.putExtra("tripID", feedback.getTripID());
            }

            startActivity(intent);
        });


        btnActivityAccount.setOnClickListener(v -> {
            SharedPreferences prefs = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
            boolean isVerified = prefs.getBoolean("isAccountVerified", false);
            Intent intent = isVerified
                    ? new Intent(getActivity(), AccountStatusTrue.class)
                    : new Intent(getActivity(), AccountStatusFalse.class);
            startActivity(intent);
        });

        return view;
    }

    private void showImagePickerDialog() {
        String[] options = {"Chọn từ thư viện", "Chụp ảnh"};
        new AlertDialog.Builder(requireContext())
                .setTitle("Chọn ảnh đại diện")
                .setItems(options, (dialog, which) -> {
                    if (which == 0) checkStoragePermission();
                    else checkCameraPermission();
                })
                .show();
    }

    private void checkStoragePermission() {
        lastRequestedPermission = Manifest.permission.READ_EXTERNAL_STORAGE;
        if (ContextCompat.checkSelfPermission(requireContext(), lastRequestedPermission) != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(lastRequestedPermission);
        } else {
            openGallery();
        }
    }

    private void checkCameraPermission() {
        lastRequestedPermission = Manifest.permission.CAMERA;
        if (ContextCompat.checkSelfPermission(requireContext(), lastRequestedPermission) != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(lastRequestedPermission);
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
        if (intent.resolveActivity(requireActivity().getPackageManager()) != null) {
            cameraLauncher.launch(intent);
        } else {
            Toast.makeText(requireContext(), "Không tìm thấy ứng dụng máy ảnh", Toast.LENGTH_SHORT).show();
        }
    }

    private Uri getImageUri(Bitmap bitmap) {
        String path = MediaStore.Images.Media.insertImage(requireContext().getContentResolver(), bitmap, "Avatar", null);
        return Uri.parse(path);
    }

    private void saveCustomerData(User customer) {
        SharedPreferences prefs = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("customerId", customer.getCustomerId()); // ✅ Lưu customerId
        editor.putString("avatar", customer.getAvatar());
        editor.putString("descrip", customer.getDescrip());
        editor.putString("email", customer.getEmail());
        editor.putString("fullName", customer.getFullName());
        editor.putString("hometown", customer.getHometown());
        editor.putString("phoneNumber", customer.getPhoneNumber());
        editor.apply();
    }
}
