package com.sheshield.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ScreenOnReceiver extends BroadcastReceiver {

    private static final String TAG =
            "ScreenOnReceiver";


    @Override
    public void onReceive(
            Context context,
            Intent intent
    ) {

        if (intent == null) {
            return;
        }


        if (!Intent.ACTION_SCREEN_ON.equals(
                intent.getAction()
        )) {

            return;
        }


        Log.d(
                TAG,
                "Screen is ON"
        );


        /*
         * The screen-off trigger is no longer armed.
         */
    }
}