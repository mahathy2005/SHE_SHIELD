package com.sheshield.app;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import android.util.Log;

/**
 * Screen-Off SOS trigger service.
 *
 * IMPORTANT:
 * This service is separate from VoiceSosService.
 * Do NOT modify VoiceSosService for this step.
 *
 * For now, this service only establishes the
 * Android Accessibility Service foundation.
 *
 * We will add the actual hardware-trigger detection
 * in the next step after the service is registered.
 */
public class ScreenOffSosService extends AccessibilityService {

    private static final String TAG =
            "ScreenOffSosService";


    // ============================================================
    // SERVICE CONNECTED
    // ============================================================

    @Override
    protected void onServiceConnected() {

        super.onServiceConnected();

        Log.d(
                TAG,
                "Screen-Off SOS Accessibility Service connected"
        );
    }


    // ============================================================
    // ACCESSIBILITY EVENTS
    // ============================================================

    @Override
    public void onAccessibilityEvent(
            AccessibilityEvent event
    ) {

        /*
         * Intentionally empty for now.
         *
         * We are NOT triggering SOS from accessibility
         * events yet.
         *
         * The existing SOS system remains completely
         * unchanged.
         */
    }


    // ============================================================
    // SERVICE INTERRUPTED
    // ============================================================

    @Override
    public void onInterrupt() {

        Log.d(
                TAG,
                "Screen-Off SOS Accessibility Service interrupted"
        );
    }


    // ============================================================
    // SERVICE DESTROYED
    // ============================================================

    @Override
    public void onDestroy() {

        Log.d(
                TAG,
                "Screen-Off SOS Accessibility Service destroyed"
        );

        super.onDestroy();
    }
}