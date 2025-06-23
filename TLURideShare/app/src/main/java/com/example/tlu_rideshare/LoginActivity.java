package com.example.tlu_rideshare; // Đảm bảo package name này khớp với applicationId trong build.gradle của bạn

import android.app.ProgressDialog; // Import ProgressDialog
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils; // Import TextUtils
import android.util.Log; // Import Log
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tlurideshare.ForgotPasswordActivity;
import com.example.tlurideshare.MainActivity;
import com.example.tlurideshare.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException; // Import thêm cho lỗi người dùng không tồn tại
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity"; // TAG cho Logcat để dễ lọc

    // Khai báo các View từ layout XML của màn hình đăng nhập
    // Các ID này ĐÃ KHỚP CHÍNH XÁC với activity_login.xml bạn vừa cung cấp
    private TextInputEditText etEmail;
    private TextInputEditText etPassword;
    private Button btnLogin;
    private TextView tvForgotPassword;
    private TextView tvRegisterNow; // TextView "Đăng ký ngay!"

    // Khai báo đối tượng Firebase Authentication
    private FirebaseAuth mAuth;

    private ProgressDialog progressDialog; // Khai báo ProgressDialog

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login); // Đảm bảo tên layout XML này khớp

        // Đảm bảo ID này khớp với ID của layout gốc trong activity_login.xml
        // (Đây là ConstraintLayout bên trong ScrollView)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 1. Khởi tạo đối tượng Firebase Authentication
        mAuth = FirebaseAuth.getInstance();

        // Khởi tạo ProgressDialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Đang đăng nhập...");
        progressDialog.setCancelable(false); // Không cho hủy khi đang xử lý

        // 2. Ánh xạ các Views từ layout XML
        // ĐÃ SỬA CÁC ID ĐỂ KHỚP CHÍNH XÁC VỚI activity_login.xml BẠN CUNG CẤP
        etEmail = findViewById(R.id.emailEditText);
        etPassword = findViewById(R.id.passwordEditText);
        btnLogin = findViewById(R.id.loginButton);
        tvForgotPassword = findViewById(R.id.forgotPasswordTextView);
        tvRegisterNow = findViewById(R.id.registerTextView); // TextView "Đăng ký ngay!"

        // 3. Thiết lập sự kiện lắng nghe cho nút Đăng nhập
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Nút Đăng nhập đã được bấm. Gọi loginUser()."); // Log kiểm tra click
                loginUser(); // Gọi hàm xử lý đăng nhập
            }
        });

        // 4. Thiết lập sự kiện lắng nghe cho TextView "Đăng ký ngay!"
        tvRegisterNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "TextView 'Đăng ký ngay!' đã được bấm. Chuyển sang RegisterActivity.");
                Intent intent = new Intent(LoginActivity.this, com.example.tlu_rideshare.RegisterActivity.class);
                startActivity(intent);
                // Bạn có thể chọn finish() ở đây nếu không muốn người dùng quay lại LoginActivity bằng nút back
                // finish();
            }
        });

        // 5. Thiết lập sự kiện lắng nghe cho TextView "Quên mật khẩu"
        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "TextView 'Quên mật khẩu' đã được bấm. Chuyển sang ForgotPasswordActivity.");
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    // Hàm để xử lý logic Đăng nhập tài khoản với Firebase Authentication
    private void loginUser() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        Log.d(TAG, "Đang kiểm tra dữ liệu đầu vào cho đăng nhập...");

        // 1. Kiểm tra tính hợp lệ của dữ liệu đầu vào
        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Email không được để trống.");
            Toast.makeText(this, "Vui lòng điền Email.", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Lỗi: Email rỗng khi đăng nhập.");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Mật khẩu không được để trống.");
            Toast.makeText(this, "Vui lòng điền Mật khẩu.", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Lỗi: Mật khẩu rỗng khi đăng nhập.");
            return;
        }

        // Nếu dữ liệu hợp lệ, hiển thị ProgressDialog
        progressDialog.show();
        Log.d(TAG, "Dữ liệu đăng nhập hợp lệ. Bắt đầu đăng nhập Firebase Auth...");

        // 2. Thực hiện Đăng nhập tài khoản với Firebase Authentication
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss(); // Ẩn dialog dù thành công hay thất bại

                        if (task.isSuccessful()) {
                            // Đăng nhập thành công
                            Log.d(TAG, "signInWithEmailAndPassword thành công.");
                            Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                            // Chuyển sang MainActivity hoặc màn hình chính của ứng dụng
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class); // Thay MainActivity.class bằng Activity chính của bạn
                            startActivity(intent);
                            finish(); // Đóng LoginActivity để người dùng không quay lại màn hình đăng nhập
                        } else {
                            // Đăng nhập thất bại
                            String errorMessage = "Đăng nhập thất bại.";
                            Exception exception = task.getException();
                            if (exception != null) {
                                if (exception instanceof FirebaseAuthInvalidUserException) {
                                    errorMessage = "Tài khoản không tồn tại hoặc đã bị vô hiệu hóa.";
                                } else if (exception instanceof FirebaseAuthInvalidCredentialsException) {
                                    errorMessage = "Mật khẩu không đúng. Vui lòng thử lại.";
                                } else {
                                    // Các lỗi khác, ví dụ lỗi mạng
                                    errorMessage = "Lỗi kết nối hoặc không xác định: " + exception.getMessage();
                                }
                            }
                            Log.e(TAG, "signInWithEmailAndPassword thất bại: " + errorMessage, exception);
                            Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    // Tùy chọn: Xử lý khi người dùng đã đăng nhập sẵn
    @Override
    protected void onStart() {
        super.onStart();
        // Kiểm tra xem người dùng đã đăng nhập chưa
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // Nếu đã đăng nhập, chuyển hướng thẳng đến MainActivity (hoặc màn hình chính)
            Log.d(TAG, "Người dùng đã đăng nhập: " + currentUser.getEmail() + ". Chuyển hướng.");
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Đóng LoginActivity
        }
    }
}