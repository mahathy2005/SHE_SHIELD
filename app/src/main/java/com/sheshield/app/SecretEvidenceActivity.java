package com.sheshield.app;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SecretEvidenceActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView tv = new TextView(this);
        tv.setText("Secret Vault - Recorded Evidence Log");
        tv.setTextSize(20);
        tv.setPadding(40, 40, 40, 40);

        setContentView(tv);
    }
}