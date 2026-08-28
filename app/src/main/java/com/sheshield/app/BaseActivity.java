package com.sheshield.app;

import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(
                LocaleHelper.setLocale(newBase)
        );
    }

    @Override
    protected void onPostCreate(
            @Nullable Bundle savedInstanceState
    ) {
        super.onPostCreate(savedInstanceState);

        getWindow()
                .getDecorView()
                .post(this::translateAndScalePage);
    }

    // ============================================================
    // TRANSLATE + SCALE + ALIGN
    // ============================================================

    public void translateAndScalePage() {

        String langCode =
                LocaleHelper.getSavedLanguage(this);

        if (langCode == null ||
                langCode.equals("en")) {
            return;
        }

        View rootView =
                findViewById(android.R.id.content);

        if (rootView == null) {
            return;
        }

        List<TextView> allViews =
                new ArrayList<>();

        collectAndTranslateViews(
                rootView,
                langCode,
                allViews
        );

        applySmallestUniformSize(
                allViews,
                langCode
        );

        // --------------------------------------------------------
        // GENTLE ALIGNMENT FIX
        // --------------------------------------------------------

        applyTextAlignment(allViews);
    }

    // ============================================================
    // COLLECT + TRANSLATE
    // ============================================================

    private void collectAndTranslateViews(
            View view,
            String langCode,
            List<TextView> viewList
    ) {

        if (view == null) {
            return;
        }

        if (view instanceof TextView) {

            TextView textView =
                    (TextView) view;

            try {

                LanguageManager.translateTextView(
                        this,
                        textView,
                        langCode
                );

                viewList.add(textView);

            } catch (Exception ignored) {
                // Translation must never crash the page.
            }
        }

        if (view instanceof ViewGroup) {

            ViewGroup viewGroup =
                    (ViewGroup) view;

            for (
                    int i = 0;
                    i < viewGroup.getChildCount();
                    i++
            ) {

                collectAndTranslateViews(
                        viewGroup.getChildAt(i),
                        langCode,
                        viewList
                );
            }
        }
    }

    // ============================================================
    // FONT SIZE
    // ============================================================

    private void applySmallestUniformSize(
            List<TextView> viewList,
            String langCode
    ) {

        float targetFactor = 0.85f;

        float scaledDensity =
                getResources()
                        .getDisplayMetrics()
                        .scaledDensity;

        if (scaledDensity <= 0) {
            return;
        }

        for (TextView tv : viewList) {

            if (tv == null) {
                continue;
            }

            try {

                float originalPx =
                        tv.getTextSize();

                float originalSp =
                        originalPx / scaledDensity;

                float newSp =
                        originalSp * targetFactor;

                if (newSp < 10.0f) {
                    newSp = 10.0f;
                }

                tv.setTextSize(
                        TypedValue.COMPLEX_UNIT_SP,
                        newSp
                );

                // ------------------------------------------------
                // BUTTONS
                // ------------------------------------------------

                if (tv instanceof Button ||
                        tv instanceof MaterialButton) {

                    tv.setMaxLines(2);

                    tv.setGravity(
                            Gravity.CENTER
                    );
                }

            } catch (Exception ignored) {
                // Ignore individual TextView errors.
            }
        }
    }

    // ============================================================
    // ALIGNMENT
    // ============================================================

    private void applyTextAlignment(
            List<TextView> viewList
    ) {

        for (TextView tv : viewList) {

            if (tv == null) {
                continue;
            }

            try {

                // =================================================
                // MATERIAL BUTTONS / BUTTONS
                // =================================================

                if (tv instanceof MaterialButton ||
                        tv instanceof Button) {

                    tv.setGravity(
                            Gravity.CENTER
                    );

                    tv.setTextAlignment(
                            View.TEXT_ALIGNMENT_CENTER
                    );

                    continue;
                }

                // =================================================
                // OTHER TEXT VIEWS
                // =================================================

                /*
                 * Do NOT center every TextView.
                 *
                 * Start alignment looks much cleaner for:
                 *
                 * Place names
                 * Addresses
                 * Descriptions
                 * Long translated sentences
                 */

                tv.setGravity(
                        Gravity.START |
                                Gravity.CENTER_VERTICAL
                );

                tv.setTextAlignment(
                        View.TEXT_ALIGNMENT_VIEW_START
                );

                // =================================================
                // MULTI-LINE TEXT
                // =================================================

                if (tv.getMaxLines() > 1 ||
                        tv.getLineCount() > 1) {

                    tv.setGravity(
                            Gravity.START |
                                    Gravity.TOP
                    );
                }

            } catch (Exception ignored) {
                // Never crash because of alignment.
            }
        }
    }
}