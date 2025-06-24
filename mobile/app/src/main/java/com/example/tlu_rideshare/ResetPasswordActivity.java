package com.example.tlu_rideshare;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.ActionCodeResult;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {

    private static final String TAG = "ResetPasswordActivity";

    private TextInputEditText etNewPassword, etConfirmPassword;
    private Button btnResetPassword;
    private FirebaseAuth mAuth;
    private String actionCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        mAuth = FirebaseAuth.getInstance();

        etNewPassword = findViewById(R.id.newPasswordEditText);
        etConfirmPassword = findViewById(R.id.confirmPasswordEditText);
        btnResetPassword = findViewById(R.id.resetPasswordButton);

        // Lấy actionCode từ Intent (được gửi qua email khôi phục)
        actionCode = getIntent().getStringExtra("oobCode"); // "oobCode" là tham số từ email khôi phục
        if (actionCode == null) {
            Log.e(TAG, "Không nhận được actionCode.");
            Toast.makeText(this, "Liên kết không hợp lệ. Vui lòng thử lại.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        btnResetPassword.setOnClickListener(v -> resetPassword());
    }

    private void resetPassword() {
        String newPassword = etNewPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        if (TextUtils.isEmpty(newPassword)) {
            etNewPassword.setError("Vui lòng nhập mật khẩu mới.");
            return;
        }
        if (newPassword.length() < 6) {
            etNewPassword.setError("Mật khẩu phải có ít nhất 6 ký tự.");
            return;
        }
        if (!newPassword.equals(confirmPassword)) {
            etConfirmPassword.setError("Mật khẩu xác nhận không khớp.");
            return;
        }

        mAuth.checkActionCode(actionCode)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        ActionCodeResult result = task.getResult();
                        if (result != null) {
                            Log.d(TAG, "Mã hành động hợp lệ.");
                            mAuth.confirmPasswordReset(actionCode, newPassword)
                                    .addOnCompleteListener(this, resetTask -> {
                                        if (resetTask.isSuccessful()) {
                                            Log.d(TAG, "Đặt lại mật khẩu thành công.");
                                            Toast.makeText(ResetPasswordActivity.this, "Đặt lại mật khẩu thành công!", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Log.e(TAG, "Đặt lại mật khẩu thất bại.", resetTask.getException());
                                            Toast.makeText(ResetPasswordActivity.this, "Đặt lại mật khẩu thất bại. Vui lòng thử lại.", Toast.LENGTH_LONG).show();
                                        }
                                    });
                        }
                    } else {
                        Log.e(TAG, "Xác minh actionCode thất bại.", task.getException());
                        Toast.makeText(this, "Liên kết không hợp lệ hoặc đã hết hạn.", Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
    }
}