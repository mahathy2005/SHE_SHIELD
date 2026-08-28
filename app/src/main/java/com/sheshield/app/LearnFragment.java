package com.sheshield.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.card.MaterialCardView;

public class LearnFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_learn, container, false);
        LinearLayout containerList = view.findViewById(R.id.containerLearn);

        if (containerList != null) {
            addCard(containerList, "DEFENSE MOVE", "Palm Heel Strike", "Target: Nose/Chin. Flex wrist back and strike hard using the heel of your palm to stun the attacker and break away.");
            addCard(containerList, "DEFENSE MOVE", "Wrist Release Escape", "Target: Attacker Thumb. Rotate wrist towards their thumb-finger gap and pull back sharply to break free.");
            addCard(containerList, "DEFENSE MOVE", "Groin Kick / Knee Strike", "Target: Groin area. Deliver a rapid upward knee strike to immobilize the attacker instantly and create time to run.");
            addCard(containerList, "DEFENSE MOVE", "Bear Hug Escape", "Drop your body weight low immediately. Stomp hard on the attacker's foot and strike backward using your elbows.");
        }

        return view;
    }

    private void addCard(LinearLayout container, String category, String title, String description) {
        MaterialCardView card = new MaterialCardView(requireContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 0, 32);
        card.setLayoutParams(params);
        card.setCardElevation(4);
        card.setRadius(24);
        card.setStrokeColor(0xFFE0E0E0);
        card.setStrokeWidth(2);

        LinearLayout layout = new LinearLayout(requireContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(40, 40, 40, 40);

        TextView tvCategory = new TextView(requireContext());
        tvCategory.setText(category);
        tvCategory.setTextColor(0xFFE91E63);
        tvCategory.setTextSize(12);
        tvCategory.setTypeface(null, android.graphics.Typeface.BOLD);

        TextView tvTitle = new TextView(requireContext());
        tvTitle.setText(title);
        tvTitle.setTextColor(0xFF1A1A2E);
        tvTitle.setTextSize(18);
        tvTitle.setTypeface(null, android.graphics.Typeface.BOLD);
        tvTitle.setPadding(0, 8, 0, 12);

        TextView tvDesc = new TextView(requireContext());
        tvDesc.setText(description);
        tvDesc.setTextColor(0xFF555555);
        tvDesc.setTextSize(14);

        layout.addView(tvCategory);
        layout.addView(tvTitle);
        layout.addView(tvDesc);
        card.addView(layout);

        container.addView(card);
    }
}