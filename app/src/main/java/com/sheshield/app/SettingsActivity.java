package com.sheshield.app;

import android.os.Bundle;
import android.content.SharedPreferences;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private Switch screenOffSosSwitch;

    private static final String PREF_NAME =
            "SheShieldPreferences";

    private static final String KEY_SCREEN_OFF_SOS =
            "SCREEN_OFF_SOS_ENABLED";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);

        screenOffSosSwitch =
                findViewById(R.id.screenOffSosSwitch);

        SharedPreferences preferences =
                getSharedPreferences(
                        PREF_NAME,
                        MODE_PRIVATE
                );

        // Load saved ON/OFF state
        boolean enabled =
                preferences.getBoolean(
                        KEY_SCREEN_OFF_SOS,
                        true
                );

        screenOffSosSwitch.setChecked(enabled);

        // Save whenever user changes the switch
        screenOffSosSwitch.setOnCheckedChangeListener(
                (buttonView, isChecked) -> {

                    preferences.edit()
                            .putBoolean(
                                    KEY_SCREEN_OFF_SOS,
                                    isChecked
                            )
                            .apply();
                }
        );
    }
}