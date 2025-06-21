package com.example.tlurideshare;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CaptureIDCardActivity extends AppCompatActivity {

    private static final int STATE_FRONT_SIDE = 0;
    private static final int STATE_BACK_SIDE = 1;
    private int currentState = STATE_FRONT_SIDE;

    private PreviewView viewFinder;
    private ImageView idCardImageView;
    private TextView titleTextView;
    private TextView instructionTextView;
    private Button captureButton;
    private Button retakeButton;
    private Button continueButton;

    private ImageCapture imageCapture;
    private File outputDirectory;
    private ExecutorService cameraExecutor;

    private ActivityResultLauncher<String[]> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), permissions -> {
                boolean allGranted = true;
                for (Boolean granted : permissions.values()) {
                    if (!granted) {
                        allGranted = false;
                        break;
                    }
                }
                if (allGranted) {
                    startCamera();
                } else {
                    Toast.makeText(this, "Permissions not granted by the user.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_capture_id_card);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.capture_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        viewFinder = findViewById(R.id.viewFinder);
        idCardImageView = findViewById(R.id.idCardImageView);
        titleTextView = findViewById(R.id.titleTextView);
        instructionTextView = findViewById(R.id.instructionTextView);
        captureButton = findViewById(R.id.captureButton);
        retakeButton = findViewById(R.id.retakeButton);
        continueButton = findViewById(R.id.continueButton);

        // Initially show camera preview and capture button
        viewFinder.setVisibility(View.VISIBLE);
        idCardImageView.setVisibility(View.GONE);
        captureButton.setVisibility(View.VISIBLE);
        retakeButton.setVisibility(View.GONE);
        continueButton.setVisibility(View.GONE);

        findViewById(R.id.backButton).setOnClickListener(v -> finish());

        captureButton.setOnClickListener(v -> takePhoto());

        retakeButton.setOnClickListener(v -> {
            // Show camera preview again, hide image and other buttons
            viewFinder.setVisibility(View.VISIBLE);
            idCardImageView.setVisibility(View.GONE);
            captureButton.setVisibility(View.VISIBLE);
            retakeButton.setVisibility(View.GONE);
            continueButton.setVisibility(View.GONE);
            updateUIForState(); // Reset text views
            startCamera(); // Restart camera preview
        });

        continueButton.setOnClickListener(v -> {
            if (currentState == STATE_FRONT_SIDE) {
                currentState = STATE_BACK_SIDE;
                updateUIForState();
                // Show camera preview again for back side capture
                viewFinder.setVisibility(View.VISIBLE);
                idCardImageView.setVisibility(View.GONE);
                captureButton.setVisibility(View.VISIBLE);
                retakeButton.setVisibility(View.GONE);
                continueButton.setVisibility(View.GONE);
                startCamera(); // Restart camera preview for back side
            } else {
                // Both sides captured, finish activity or go to next screen
                Intent intent = new Intent(CaptureIDCardActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Setup output directory and camera executor
        outputDirectory = getOutputDirectory();
        cameraExecutor = Executors.newSingleThreadExecutor();

        // Request camera permissions
        requestPermissions();
    }

    private void requestPermissions() {
        String[] permissions = getRequiredPermissions();
        if (allPermissionsGranted(permissions)) {
            startCamera();
        } else {
            requestPermissionLauncher.launch(permissions);
        }
    }

    private boolean allPermissionsGranted(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private String[] getRequiredPermissions() {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            return new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        } else {
            return new String[]{Manifest.permission.CAMERA};
        }
    }

    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);

        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();

                // Preview
                Preview preview = new Preview.Builder()
                        .build();
                preview.setSurfaceProvider(viewFinder.getSurfaceProvider());

                imageCapture = new ImageCapture.Builder()
                        .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                        .build();

                // Select back camera as a default
                CameraSelector cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA;

                // Unbind use cases before rebinding
                cameraProvider.unbindAll();

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture);

            } catch (Exception exc) {
                Toast.makeText(this, "Error starting camera: " + exc.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }, ContextCompat.getMainExecutor(this));
    }

    private void takePhoto() {
        if (imageCapture == null) {
            return;
        }

        // Create time-stamped name and MediaStore entry.
        String name = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS", Locale.US)
                .format(System.currentTimeMillis());
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, name);
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");
        
        // Set the relative path for Android 10 and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/" + getString(R.string.app_name));
            contentValues.put(MediaStore.Images.Media.IS_PENDING, 1);
        }

        // Create output options object for ImageCapture.
        ImageCapture.OutputFileOptions outputOptions = new ImageCapture.OutputFileOptions
                .Builder(getContentResolver(), MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                .build();

        // Set up image capture listener which is triggered after photo has been taken
        imageCapture.takePicture(
                outputOptions,
                ContextCompat.getMainExecutor(this),
                new ImageCapture.OnImageSavedCallback() {
                    @Override
                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                        Uri savedUri = outputFileResults.getSavedUri();
                        if (savedUri != null) {
                            // For Android 10 and above, update the IS_PENDING flag
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                ContentValues updateValues = new ContentValues();
                                updateValues.put(MediaStore.Images.Media.IS_PENDING, 0);
                                getContentResolver().update(savedUri, updateValues, null, null);
                            }
                            
                            Toast.makeText(CaptureIDCardActivity.this, "Photo capture succeeded: " + savedUri, Toast.LENGTH_SHORT).show();
                            idCardImageView.setImageURI(savedUri);
                            viewFinder.setVisibility(View.GONE);
                            idCardImageView.setVisibility(View.VISIBLE);
                            captureButton.setVisibility(View.GONE);
                            retakeButton.setVisibility(View.VISIBLE);
                            continueButton.setVisibility(View.VISIBLE);
                        } else {
                            Toast.makeText(CaptureIDCardActivity.this, "Photo capture failed: Saved URI is null", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(@NonNull ImageCaptureException exc) {
                        String msg = "Photo capture failed: " + exc.getMessage();
                        if (exc.getImageCaptureError() == ImageCapture.ERROR_FILE_IO) {
                            msg += "\nPlease check storage permissions and available space.";
                        }
                        Toast.makeText(CaptureIDCardActivity.this, msg, Toast.LENGTH_LONG).show();
                        exc.printStackTrace();
                    }
                }
        );
    }

    private File getOutputDirectory() {
        File[] mediaDirs = ContextCompat.getExternalFilesDirs(this, null);
        File appSpecificExternalDir = null;
        if (mediaDirs.length > 0) {
            appSpecificExternalDir = new File(mediaDirs[0], getResources().getString(R.string.app_name));
            if (!appSpecificExternalDir.exists()) {
                appSpecificExternalDir.mkdirs();
            }
        }
        return appSpecificExternalDir;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraExecutor.shutdown();
    }

    private void updateUIForState() {
        if (currentState == STATE_FRONT_SIDE) {
            titleTextView.setText("Mặt trước Căn cước công dân");
            instructionTextView.setText("Đảm bảo rằng giấy tờ bạn chụp rõ nét, các thông tin trong giấy tờ rõ ràng");
        } else {
            titleTextView.setText("Mặt sau Căn cước công dân");
            instructionTextView.setText("Đảm bảo rằng giấy tờ bạn chụp rõ nét, các thông tin trong giấy tờ rõ ràng");
        }
    }
} 