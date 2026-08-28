package com.sheshield.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ScreenOffSosReceiver extends BroadcastReceiver {

    private static final String PREF_NAME =
            "SheShieldPreferences";

    private static final String KEY_SCREEN_OFF_SOS =
            "SCREEN_OFF_SOS_ENABLED";

    @Override
    public void onReceive(
            Context context,
            Intent intent
    ) {

        if (!Intent.ACTION_SCREEN_OFF.equals(
                intent.getAction()
        )) {
            return;
        }

        // ====================================================
        // CHECK SCREEN-OFF SOS ON/OFF
        // ====================================================

        android.content.SharedPreferences preferences =
                context.getSharedPreferences(
                        PREF_NAME,
                        Context.MODE_PRIVATE
                );

        boolean screenOffSosEnabled =
                preferences.getBoolean(
                        KEY_SCREEN_OFF_SOS,
                        true
                );

        // ====================================================
        // IF OFF → DO NOTHING
        // ====================================================

        if (!screenOffSosEnabled) {
            return;
        }

        // ====================================================
        // IF ON → EXISTING SCREEN-OFF SOS CODE
        // ====================================================

        // KEEP YOUR EXISTING SCREEN-OFF SOS CODE HERE.
    }
}