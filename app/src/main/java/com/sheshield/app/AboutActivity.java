package com.sheshield.app;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.card.MaterialCardView;

public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        // Bind Views
        ImageButton btnBack = findViewById(R.id.btnBack);
        TextView tvVersion = findViewById(R.id.tvAppVersion);
        MaterialCardView cardPrivacy = findViewById(R.id.cardPrivacyPolicy);
        MaterialCardView cardSupport = findViewById(R.id.cardSupport);
        MaterialCardView cardShare = findViewById(R.id.cardShareApp);

        // Back Button Action
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        // Non-Deprecated App Version Check (Handles Android 13+ & older versions)
        try {
            PackageInfo pInfo;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                pInfo = getPackageManager().getPackageInfo(
                        getPackageName(),
                        PackageManager.PackageInfoFlags.of(0)
                );
            } else {
                pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            }

            long versionCode = Build.VERSION.SDK_INT >= Build.VERSION_CODES.P
                    ? pInfo.getLongVersionCode()
                    : pInfo.versionCode;

            tvVersion.setText("Version " + pInfo.versionName + " (Build " + versionCode + ")");
        } catch (PackageManager.NameNotFoundException e) {
            tvVersion.setText("Version 1.0.0");
        }

        // Privacy Policy Click
        if (cardPrivacy != null) {
            cardPrivacy.setOnClickListener(v ->
                    Toast.makeText(this, "Opening Privacy Policy...", Toast.LENGTH_SHORT).show()
            );
        }

        // Customer Support Email Click
        if (cardSupport != null) {
            cardSupport.setOnClickListener(v -> {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:support@sheshield.app"));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "SheShield App Support Inquiry");
                try {
                    startActivity(Intent.createChooser(emailIntent, "Send Email"));
                } catch (Exception e) {
                    Toast.makeText(this, "No email client found.", Toast.LENGTH_SHORT).show();
                }
            });
        }

        // Share App Click
        if (cardShare != null) {
            cardShare.setOnClickListener(v -> {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(
                        Intent.EXTRA_TEXT,
                        "Stay safe with SheShield - Emergency SOS and Personal Safety App for Women. Download now!"
                );
                startActivity(Intent.createChooser(shareIntent, "Share SheShield via"));
            });
        }
    }
}