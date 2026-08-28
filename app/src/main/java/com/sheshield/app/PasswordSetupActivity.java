package com.sheshield.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class PasswordSetupActivity extends AppCompatActivity {

    private TextInputEditText etPassword;
    private TextInputEditText etConfirmPassword;

    private MaterialButton btnFinalSignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_password_setup
        );


        // =========================
        // INITIALIZE VIEWS
        // =========================

        etPassword =
                findViewById(R.id.etPassword);

        etConfirmPassword =
                findViewById(R.id.etConfirmPassword);

        btnFinalSignUp =
                findViewById(R.id.btnFinalSignUp);


        // =========================
        // FINAL SIGN UP BUTTON
        // =========================

        btnFinalSignUp.setOnClickListener(v -> {

            if (validatePasswords()) {

                // =========================================
                // GET FULL NAME FROM SIGNUP ACTIVITY
                // =========================================

                String fullName =
                        getIntent()
                                .getStringExtra(
                                        "fullName"
                                );


                // =========================================
                // SAVE USER NAME
                // =========================================

                SharedPreferences sharedPreferences =
                        getSharedPreferences(
                                "UserSession",
                                MODE_PRIVATE
                        );


                SharedPreferences.Editor editor =
                        sharedPreferences.edit();


                editor.putString(
                        "USER_NAME",
                        fullName != null
                                ? fullName.trim()
                                : "User"
                );


                // Optional:
                // Save login/registration status

                editor.putBoolean(
                        "IS_LOGGED_IN",
                        true
                );


                editor.apply();


                // =========================================
                // SHOW SUCCESS MESSAGE
                // =========================================

                Toast.makeText(
                        PasswordSetupActivity.this,
                        "Registration Successful!",
                        Toast.LENGTH_LONG
                ).show();


                // =========================================
                // OPEN MAIN ACTIVITY
                // =========================================

                Intent intent =
                        new Intent(
                                PasswordSetupActivity.this,
                                MainActivity.class
                        );


                startActivity(intent);


                // Prevent returning to signup pages

                finishAffinity();
            }
        });
    }


    // =========================
    // VALIDATE PASSWORDS
    // =========================

    private boolean validatePasswords() {

        String password =
                etPassword.getText() != null
                        ?
                        etPassword
                                .getText()
                                .toString()
                                .trim()
                        :
                        "";


        String confirmPassword =
                etConfirmPassword.getText() != null
                        ?
                        etConfirmPassword
                                .getText()
                                .toString()
                                .trim()
                        :
                        "";


        // =========================
        // EMPTY PASSWORD
        // =========================

        if (TextUtils.isEmpty(password)) {

            etPassword.setError(
                    "Password is required"
            );

            etPassword.requestFocus();

            return false;
        }


        // =========================
        // MINIMUM LENGTH
        // =========================

        if (password.length() < 6) {

            etPassword.setError(
                    "Password must be at least 6 characters"
            );

            etPassword.requestFocus();

            return false;
        }


        // =========================
        // EMPTY CONFIRM PASSWORD
        // =========================

        if (TextUtils.isEmpty(confirmPassword)) {

            etConfirmPassword.setError(
                    "Please confirm your password"
            );

            etConfirmPassword.requestFocus();

            return false;
        }


        // =========================
        // PASSWORD MATCH
        // =========================

        if (!password.equals(confirmPassword)) {

            etConfirmPassword.setError(
                    "Passwords do not match"
            );

            etConfirmPassword.requestFocus();

            return false;
        }


        return true;
    }
}