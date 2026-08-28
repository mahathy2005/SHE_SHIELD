package com.sheshield.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SafeZonesAdapter
        extends RecyclerView.Adapter<SafeZonesAdapter.SafeZoneViewHolder> {

    // =============================================================
    // CLICK LISTENER
    // =============================================================

    public interface OnPlaceClickListener {

        void onNavigate(
                SafeZonesActivity.SafePlace place
        );

        void onCall(
                SafeZonesActivity.SafePlace place
        );
    }

    // =============================================================
    // DATA
    // =============================================================

    private final List<SafeZonesActivity.SafePlace> placeList =
            new ArrayList<>();

    private final OnPlaceClickListener listener;

    // =============================================================
    // CONSTRUCTOR
    // =============================================================

    public SafeZonesAdapter(
            List<SafeZonesActivity.SafePlace> places,
            OnPlaceClickListener listener
    ) {

        if (places != null) {
            placeList.addAll(places);
        }

        this.listener = listener;
    }

    // =============================================================
    // UPDATE LIST
    // =============================================================

    public void updateList(
            List<SafeZonesActivity.SafePlace> places
    ) {

        placeList.clear();

        if (places != null) {
            placeList.addAll(places);
        }

        notifyDataSetChanged();
    }

    // =============================================================
    // CREATE VIEW HOLDER
    // =============================================================

    @NonNull
    @Override
    public SafeZoneViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {

        View view =
                LayoutInflater.from(parent.getContext())
                        .inflate(
                                R.layout.item_safe_zone,
                                parent,
                                false
                        );

        return new SafeZoneViewHolder(view);
    }

    // =============================================================
    // BIND VIEW HOLDER
    // =============================================================

    @Override
    public void onBindViewHolder(
            @NonNull SafeZoneViewHolder holder,
            int position
    ) {

        SafeZonesActivity.SafePlace place =
                placeList.get(position);

        Context context =
                holder.itemView.getContext();

        // =========================================================
        // CURRENT LANGUAGE
        // =========================================================

        String language =
                LocaleHelper.getSavedLanguage(context);

        if (language == null ||
                language.trim().isEmpty()) {

            language = "en";
        }

        final String targetLanguage =
                language;

        // =========================================================
        // PLACE NAME
        // =========================================================

        String placeName =
                place.name;

        if (placeName == null ||
                placeName.trim().isEmpty()) {

            placeName = "Nearby Safe Place";
        }

        setTranslatedText(
                holder.tvPlaceName,
                placeName,
                targetLanguage
        );

        // =========================================================
        // CATEGORY
        // =========================================================

        String category =
                place.category;

        if (category == null ||
                category.trim().isEmpty()) {

            category = "SAFE PLACE";
        }

        setTranslatedText(
                holder.tvCategoryBadge,
                category,
                targetLanguage
        );

        // =========================================================
        // ADDRESS
        // =========================================================

        String address =
                place.address;

        if (address == null ||
                address.trim().isEmpty()) {

            address = "Address unavailable";
        }

        setTranslatedText(
                holder.tvPlaceAddress,
                address,
                targetLanguage
        );

        // =========================================================
        // DISTANCE
        // =========================================================

        String distanceText;

        if (place.distanceKm < 1.0) {

            distanceText =
                    String.format(
                            Locale.getDefault(),
                            "%.0f m away",
                            place.distanceKm * 1000
                    );

        } else {

            distanceText =
                    String.format(
                            Locale.getDefault(),
                            "%.1f km away",
                            place.distanceKm
                    );
        }

        setTranslatedText(
                holder.tvDistance,
                distanceText,
                targetLanguage
        );

        // =========================================================
        // CALL BUTTON
        // =========================================================

        if (place.phoneNumber == null ||
                place.phoneNumber.trim().isEmpty()) {

            holder.btnCallPlace.setVisibility(
                    View.GONE
            );

            holder.btnCallPlace.setOnClickListener(
                    null
            );

        } else {

            holder.btnCallPlace.setVisibility(
                    View.VISIBLE
            );

            setTranslatedText(
                    holder.btnCallPlace,
                    "Call",
                    targetLanguage
            );

            holder.btnCallPlace.setOnClickListener(
                    v -> {

                        if (listener != null) {

                            listener.onCall(place);
                        }
                    }
            );
        }

        // =========================================================
        // NAVIGATE BUTTON
        // =========================================================

        setTranslatedText(
                holder.btnNavigate,
                "Navigate",
                targetLanguage
        );

        holder.btnNavigate.setOnClickListener(
                v -> {

                    if (listener != null) {

                        listener.onNavigate(place);
                    }
                }
        );
    }

    // =============================================================
    // TRANSLATION
    // =============================================================

    private void setTranslatedText(
            TextView textView,
            String originalText,
            String language
    ) {

        if (textView == null) {
            return;
        }

        if (originalText == null ||
                originalText.trim().isEmpty()) {
            return;
        }

        final String cleanOriginal =
                originalText.trim();

        // =========================================================
        // STORE ORIGINAL TEXT
        // =========================================================

        textView.setTag(
                R.id.tvUserGreeting,
                cleanOriginal
        );

        // =========================================================
        // ENGLISH
        // =========================================================

        if (language == null ||
                language.equalsIgnoreCase("en")) {

            textView.setText(
                    cleanOriginal
            );

            return;
        }

        // =========================================================
        // SHOW ORIGINAL WHILE TRANSLATING
        // =========================================================

        textView.setText(
                cleanOriginal
        );

        final String finalLanguage =
                language;

        // =========================================================
        // TRANSLATE
        // =========================================================

        LanguageManager.translateText(
                textView.getContext(),
                cleanOriginal,
                finalLanguage,
                translatedText -> {

                    if (translatedText == null ||
                            translatedText.trim().isEmpty()) {

                        return;
                    }

                    // -------------------------------------------------
                    // CHECK THAT THIS TEXTVIEW STILL BELONGS TO
                    // THE SAME PLACE AFTER RECYCLER VIEW RECYCLING
                    // -------------------------------------------------

                    Object currentTag =
                            textView.getTag(
                                    R.id.tvUserGreeting
                            );

                    if (currentTag == null ||
                            !cleanOriginal.equals(
                                    currentTag.toString()
                            )) {

                        return;
                    }

                    textView.post(() -> {

                        Object latestTag =
                                textView.getTag(
                                        R.id.tvUserGreeting
                                );

                        if (latestTag == null ||
                                !cleanOriginal.equals(
                                        latestTag.toString()
                                )) {

                            return;
                        }

                        textView.setText(
                                translatedText.trim()
                        );
                    });
                }
        );
    }

    // =============================================================
    // ITEM COUNT
    // =============================================================

    @Override
    public int getItemCount() {

        return placeList.size();
    }

    // =============================================================
    // VIEW HOLDER
    // =============================================================

    static class SafeZoneViewHolder
            extends RecyclerView.ViewHolder {

        TextView tvCategoryBadge;
        TextView tvDistance;
        TextView tvPlaceName;
        TextView tvPlaceAddress;

        MaterialButton btnCallPlace;
        MaterialButton btnNavigate;

        SafeZoneViewHolder(
                @NonNull View itemView
        ) {

            super(itemView);

            tvCategoryBadge =
                    itemView.findViewById(
                            R.id.tvCategoryBadge
                    );

            tvDistance =
                    itemView.findViewById(
                            R.id.tvDistance
                    );

            tvPlaceName =
                    itemView.findViewById(
                            R.id.tvPlaceName
                    );

            tvPlaceAddress =
                    itemView.findViewById(
                            R.id.tvPlaceAddress
                    );

            btnCallPlace =
                    itemView.findViewById(
                            R.id.btnCallPlace
                    );

            btnNavigate =
                    itemView.findViewById(
                            R.id.btnNavigate
                    );
        }
    }
}