package com.sheshield.app;

import android.content.Context;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;

public class LanguageManager {

    public interface TranslationCallback {
        void onTranslationComplete(String translatedText);
    }

    public static void translateTextView(Context context, TextView textView, String targetLanguage) {
        if (textView == null) return;

        // Retrieve or store original English text
        String originalText = (String) textView.getTag(R.id.tvUserGreeting);
        if (originalText == null) {
            originalText = textView.getText().toString();
            textView.setTag(R.id.tvUserGreeting, originalText); // Cache original English
        }

        if (originalText.trim().isEmpty()) return;

        // Reset to original English first if target is English
        if (targetLanguage == null || targetLanguage.equals("en")) {
            textView.setText(originalText);
            return;
        }

        String textToTranslate = originalText;
        translateText(context, textToTranslate, targetLanguage, translatedText -> {
            if (translatedText != null && !translatedText.trim().isEmpty()) {
                textView.setText(translatedText);
            }
        });
    }

    public static void translateMenuItem(Context context, MenuItem menuItem, String targetLanguage) {
        if (menuItem == null || menuItem.getTitle() == null) return;

        String originalText = menuItem.getTitle().toString();
        if (originalText.trim().isEmpty()) return;

        if (targetLanguage == null || targetLanguage.equals("en")) {
            return;
        }

        translateText(context, originalText, targetLanguage, translatedText -> {
            if (translatedText != null && !translatedText.trim().isEmpty()) {
                menuItem.setTitle(translatedText);
            }
        });
    }

    public static void translateText(
            Context context,
            String text,
            String targetLanguage,
            TranslationCallback callback
    ) {

        if (text == null ||
                text.trim().isEmpty()) {

            callback.onTranslationComplete(text);
            return;
        }

        if (targetLanguage == null ||
                targetLanguage.equals("en")) {

            callback.onTranslationComplete(text);
            return;
        }

        TranslatorOptions options =
                new TranslatorOptions.Builder()
                        .setSourceLanguage("en")
                        .setTargetLanguage(targetLanguage)
                        .build();

        Translator translator =
                Translation.getClient(options);

        translator.downloadModelIfNeeded()
                .addOnSuccessListener(unused -> {

                    translator.translate(text)
                            .addOnSuccessListener(
                                    translatedText -> {

                                        if (translatedText != null &&
                                                !translatedText.trim().isEmpty()) {

                                            callback.onTranslationComplete(
                                                    translatedText
                                            );

                                        } else {

                                            callback.onTranslationComplete(
                                                    text
                                            );
                                        }
                                    }
                            )
                            .addOnFailureListener(e -> {

                                // ------------------------------------------------
                                // ML KIT FAILED
                                // TRY DIRECT TRANSLATION
                                // ------------------------------------------------

                                new Thread(() -> {

                                    String fallback =
                                            translateDirect(
                                                    text,
                                                    targetLanguage
                                            );

                                    callback.onTranslationComplete(
                                            fallback
                                    );

                                }).start();
                            });
                })
                .addOnFailureListener(e -> {

                    // --------------------------------------------------------
                    // MODEL DOWNLOAD FAILED
                    // TRY DIRECT TRANSLATION
                    // --------------------------------------------------------

                    new Thread(() -> {

                        String fallback =
                                translateDirect(
                                        text,
                                        targetLanguage
                                );

                        callback.onTranslationComplete(
                                fallback
                        );

                    }).start();
                });
    }
    // ============================================================
    // SIMPLE DIRECT TEXT TRANSLATION (THREAD-SAFE)
    // ============================================================

    public static String translateDirect(String text, String targetLang) {
        if (targetLang == null || targetLang.equals("en") || text == null || text.trim().isEmpty()) {
            return text;
        }

        try {
            String urlStr = "https://translate.googleapis.com/translate_a/single?client=gtx&sl=en&tl="
                    + targetLang + "&dt=t&q=" + java.net.URLEncoder.encode(text, "UTF-8");

            java.net.URL url = new java.net.URL(urlStr);
            java.net.HttpURLConnection con = (java.net.HttpURLConnection) url.openConnection();

            con.setConnectTimeout(3000);
            con.setReadTimeout(5000);

            con.setRequestProperty("User-Agent", "Mozilla/5.0");

            java.io.BufferedReader in = new java.io.BufferedReader(
                    new java.io.InputStreamReader(con.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Extract translated text from returned JSON array string
            org.json.JSONArray mainArray = new org.json.JSONArray(response.toString());
            org.json.JSONArray rawArray = mainArray.getJSONArray(0);
            StringBuilder translatedResult = new StringBuilder();

            for (int i = 0; i < rawArray.length(); i++) {
                translatedResult.append(rawArray.getJSONArray(i).getString(0));
            }

            return translatedResult.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return text; // Return original text if network fails
        }
    }
}