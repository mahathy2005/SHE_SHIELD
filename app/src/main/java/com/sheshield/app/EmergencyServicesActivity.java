package com.sheshield.app;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class EmergencyServicesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_emergency_services);

        setupAppearance();
        setupButtons();
        startAnimations();
    }


    // ============================================================
    // APPEARANCE
    // ============================================================

    private void setupAppearance() {

        // SOS icon
        TextView emergencyIcon = findViewById(R.id.emergencyIcon);

        if (emergencyIcon != null) {
            emergencyIcon.setBackground(
                    createGradient(
                            Color.rgb(190, 35, 82),
                            Color.rgb(235, 72, 118),
                            32
                    )
            );
            emergencyIcon.setElevation(6f);
        }


        // Police
        TextView policeIcon = findViewById(R.id.policeIcon);

        if (policeIcon != null) {
            policeIcon.setBackground(
                    createGradient(
                            Color.rgb(45, 91, 165),
                            Color.rgb(78, 126, 207),
                            30
                    )
            );
        }

        Button policeCall = findViewById(R.id.policeCall);

        if (policeCall != null) {
            policeCall.setBackground(
                    createGradient(
                            Color.rgb(45, 91, 165),
                            Color.rgb(70, 119, 197),
                            24
                    )
            );
        }


        // Ambulance
        TextView ambulanceIcon = findViewById(R.id.ambulanceIcon);

        if (ambulanceIcon != null) {
            ambulanceIcon.setBackground(
                    createGradient(
                            Color.rgb(31, 130, 94),
                            Color.rgb(62, 170, 128),
                            30
                    )
            );
        }

        Button ambulanceCall = findViewById(R.id.ambulanceCall);

        if (ambulanceCall != null) {
            ambulanceCall.setBackground(
                    createGradient(
                            Color.rgb(31, 130, 94),
                            Color.rgb(56, 163, 120),
                            24
                    )
            );
        }


        // Fire
        TextView fireIcon = findViewById(R.id.fireIcon);

        if (fireIcon != null) {
            fireIcon.setBackground(
                    createGradient(
                            Color.rgb(194, 78, 31),
                            Color.rgb(235, 112, 57),
                            30
                    )
            );
        }

        Button fireCall = findViewById(R.id.fireCall);

        if (fireCall != null) {
            fireCall.setBackground(
                    createGradient(
                            Color.rgb(194, 78, 31),
                            Color.rgb(225, 101, 48),
                            24
                    )
            );
        }


        // Women helpline
        TextView womenIcon = findViewById(R.id.womenIcon);

        if (womenIcon != null) {
            womenIcon.setBackground(
                    createGradient(
                            Color.rgb(123, 74, 152),
                            Color.rgb(166, 108, 194),
                            30
                    )
            );
        }

        Button womenCall = findViewById(R.id.womenCall);

        if (womenCall != null) {
            womenCall.setBackground(
                    createGradient(
                            Color.rgb(123, 74, 152),
                            Color.rgb(155, 93, 183),
                            24
                    )
            );
        }


        // National emergency
        TextView nationalIcon = findViewById(R.id.nationalIcon);

        if (nationalIcon != null) {
            nationalIcon.setBackground(
                    createGradient(
                            Color.rgb(165, 29, 45),
                            Color.rgb(207, 57, 75),
                            30
                    )
            );
        }

        Button emergencyCall = findViewById(R.id.emergencyCall);

        if (emergencyCall != null) {
            emergencyCall.setBackground(
                    createGradient(
                            Color.rgb(165, 29, 45),
                            Color.rgb(198, 49, 66),
                            24
                    )
            );
        }


        // SOS button
        Button sosButton = findViewById(R.id.sosButton);

        if (sosButton != null) {
            sosButton.setBackground(
                    createGradient(
                            Color.rgb(181, 26, 73),
                            Color.rgb(226, 52, 101),
                            28
                    )
            );
            sosButton.setElevation(7f);
        }


        // Location button
        Button shareLocation = findViewById(R.id.shareLocation);

        if (shareLocation != null) {
            shareLocation.setBackground(
                    createGradient(
                            Color.rgb(86, 52, 105),
                            Color.rgb(122, 75, 143),
                            27
                    )
            );
            shareLocation.setElevation(5f);
        }
    }


    // ============================================================
    // CREATE GRADIENT
    // ============================================================

    private GradientDrawable createGradient(
            int startColor,
            int endColor,
            float radiusDp
    ) {

        GradientDrawable drawable =
                new GradientDrawable(
                        GradientDrawable.Orientation.LEFT_RIGHT,
                        new int[]{
                                startColor,
                                endColor
                        }
                );

        drawable.setCornerRadius(
                radiusDp * getResources().getDisplayMetrics().density
        );

        return drawable;
    }


    // ============================================================
    // BUTTONS
    // ============================================================

    private void setupButtons() {

        TextView backButton = findViewById(R.id.backButton);

        if (backButton != null) {

            backButton.setOnClickListener(v -> {

                pressAnimation(v);

                v.postDelayed(
                        this::finish,
                        100
                );
            });
        }


        Button policeCall = findViewById(R.id.policeCall);

        if (policeCall != null) {

            policeCall.setOnClickListener(v -> {

                pressAnimation(v);

                openDialer("112");
            });
        }


        Button ambulanceCall = findViewById(R.id.ambulanceCall);

        if (ambulanceCall != null) {

            ambulanceCall.setOnClickListener(v -> {

                pressAnimation(v);

                openDialer("108");
            });
        }


        Button fireCall = findViewById(R.id.fireCall);

        if (fireCall != null) {

            fireCall.setOnClickListener(v -> {

                pressAnimation(v);

                openDialer("101");
            });
        }


        Button womenCall = findViewById(R.id.womenCall);

        if (womenCall != null) {

            womenCall.setOnClickListener(v -> {

                pressAnimation(v);

                openDialer("181");
            });
        }


        Button emergencyCall = findViewById(R.id.emergencyCall);

        if (emergencyCall != null) {

            emergencyCall.setOnClickListener(v -> {

                pressAnimation(v);

                openDialer("112");
            });
        }


        // ========================================================
        // SOS
        // ========================================================

        Button sosButton = findViewById(R.id.sosButton);

        if (sosButton != null) {

            sosButton.setOnClickListener(v -> {

                pressAnimation(v);

                v.postDelayed(
                        this::triggerSOS,
                        150
                );
            });
        }


        // ========================================================
        // LOCATION
        // ========================================================

        Button shareLocation = findViewById(R.id.shareLocation);

        if (shareLocation != null) {

            shareLocation.setOnClickListener(v -> {

                pressAnimation(v);

                v.postDelayed(
                        this::shareEmergencyLocation,
                        150
                );
            });
        }
    }


    // ============================================================
    // ENTRANCE ANIMATIONS
    // ============================================================

    private void startAnimations() {

        View header = findViewById(R.id.headerSection);
        View hero = findViewById(R.id.emergencyHero);
        View police = findViewById(R.id.policeCard);
        View ambulance = findViewById(R.id.ambulanceCard);
        View fire = findViewById(R.id.fireCard);
        View women = findViewById(R.id.womenCard);
        View national = findViewById(R.id.nationalCard);
        View location = findViewById(R.id.shareLocation);


        slideUp(header, 0, 350);
        popIn(hero, 120, 400);

        slideUp(police, 220, 350);
        slideUp(ambulance, 320, 350);
        slideUp(fire, 420, 350);
        slideUp(women, 520, 350);
        slideUp(national, 620, 350);

        popIn(location, 720, 400);


        // Gentle SOS pulse
        Button sosButton = findViewById(R.id.sosButton);

        if (sosButton != null) {

            sosButton.postDelayed(
                    () -> pulseSOS(sosButton),
                    1100
            );
        }
    }


    // ============================================================
    // SLIDE UP
    // ============================================================

    private void slideUp(
            View view,
            long delay,
            long duration
    ) {

        if (view == null) {
            return;
        }

        view.setAlpha(0f);

        TranslateAnimation animation =
                new TranslateAnimation(
                        0,
                        0,
                        35,
                        0
                );

        animation.setDuration(duration);
        animation.setStartOffset(delay);

        animation.setAnimationListener(
                new Animation.AnimationListener() {

                    @Override
                    public void onAnimationStart(
                            Animation animation
                    ) {
                    }

                    @Override
                    public void onAnimationEnd(
                            Animation animation
                    ) {
                        view.setAlpha(1f);
                    }

                    @Override
                    public void onAnimationRepeat(
                            Animation animation
                    ) {
                    }
                }
        );

        view.startAnimation(animation);
    }


    // ============================================================
    // POP IN
    // ============================================================

    private void popIn(
            View view,
            long delay,
            long duration
    ) {

        if (view == null) {
            return;
        }

        view.setAlpha(0f);
        view.setScaleX(0.94f);
        view.setScaleY(0.94f);

        view.animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f)
                .setStartDelay(delay)
                .setDuration(duration)
                .start();
    }


    // ============================================================
    // SOS PULSE
    // ============================================================

    private void pulseSOS(View view) {

        if (view == null) {
            return;
        }

        view.animate()
                .scaleX(1.025f)
                .scaleY(1.025f)
                .setDuration(550)
                .withEndAction(() ->
                        view.animate()
                                .scaleX(1f)
                                .scaleY(1f)
                                .setDuration(550)
                                .withEndAction(() ->
                                        pulseSOS(view)
                                )
                                .start()
                )
                .start();
    }


    // ============================================================
    // BUTTON PRESS
    // ============================================================

    private void pressAnimation(View view) {

        if (view == null) {
            return;
        }

        view.animate()
                .scaleX(0.95f)
                .scaleY(0.95f)
                .setDuration(70)
                .withEndAction(() ->
                        view.animate()
                                .scaleX(1f)
                                .scaleY(1f)
                                .setDuration(100)
                                .start()
                )
                .start();
    }


    // ============================================================
    // OPEN DIALER
    // ============================================================

    private void openDialer(String number) {

        try {

            Intent intent =
                    new Intent(Intent.ACTION_DIAL);

            intent.setData(
                    Uri.parse("tel:" + number)
            );

            startActivity(intent);

        } catch (Exception e) {

            Toast.makeText(
                    this,
                    "Unable to open phone dialer.",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }


    // ============================================================
    // SOS
    // ============================================================

    private void triggerSOS() {

        try {

            Intent intent =
                    new Intent(
                            EmergencyServicesActivity.this,
                            MainActivity.class
                    );

            intent.putExtra(
                    "TRIGGER_SOS",
                    true
            );

            intent.addFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP
                            | Intent.FLAG_ACTIVITY_SINGLE_TOP
            );

            startActivity(intent);

            Toast.makeText(
                    this,
                    "SOS Activated",
                    Toast.LENGTH_SHORT
            ).show();

        } catch (Exception e) {

            Toast.makeText(
                    this,
                    "Unable to activate SOS.",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }


    // ============================================================
    // SHARE LOCATION
    // ============================================================

    private void shareEmergencyLocation() {

        try {

            Intent settingsIntent =
                    new Intent(
                            Settings.ACTION_LOCATION_SOURCE_SETTINGS
                    );

            startActivity(settingsIntent);

        } catch (Exception e) {

            Toast.makeText(
                    this,
                    "Unable to open location settings.",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}