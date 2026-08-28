package com.sheshield.app;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText etMobile, etPassword;
    private MaterialButton btnLogin;
    private TextView tvGoToSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Views
        etMobile = findViewById(R.id.etLoginMobile);
        etPassword = findViewById(R.id.etLoginPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvGoToSignUp = findViewById(R.id.tvGoToSignUp);

        // Navigation to Sign Up Screen
        tvGoToSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

        // Login Action handler
        btnLogin.setOnClickListener(v -> {
            if (validateInputs()) {
                performLogin();
            }
        });
    }

    private boolean validateInputs() {
        String mobile = etMobile.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Check if fields are empty
        if (TextUtils.isEmpty(mobile)) {
            etMobile.setError("Mobile number required");
            etMobile.requestFocus();
            return false;
        }

        // Validate basic 10-digit mobile format
        if (!mobile.matches("^[6-9]\\d{9}$")) {
            etMobile.setError("Enter a valid 10-digit mobile number");
            etMobile.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Password required");
            etPassword.requestFocus();
            return false;
        }

        return true;
    }

    private void performLogin() {
        String mobile = etMobile.getText().toString().trim();

        // Placeholder for future backend integration (Firebase/REST API)
        Toast.makeText(this, "Login initiated for: " + mobile, Toast.LENGTH_SHORT).show();

        // Navigate to MainActivity after successful login
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);

        // Finish LoginActivity so user cannot press back to return here
        finish();
    }
}