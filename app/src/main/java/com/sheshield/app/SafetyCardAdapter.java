package com.sheshield.app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.card.MaterialCardView;
import java.util.List;

public class SafetyCardAdapter extends RecyclerView.Adapter<SafetyCardAdapter.ViewHolder> {

    private final List<SafetyCardModel> cardList;
    private final int layoutResId;

    public SafetyCardAdapter(List<SafetyCardModel> cardList, int layoutResId) {
        this.cardList = cardList;
        this.layoutResId = layoutResId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutResId, parent, false);
        return new ViewHolder(view, layoutResId);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SafetyCardModel model = cardList.get(position);

        if (holder.cardView != null) {
            holder.cardView.setStrokeColor(model.getStrokeColor());
            holder.cardView.setCardBackgroundColor(model.getBackgroundColor());
        }

        if (holder.tvCategory != null) holder.tvCategory.setText(model.getCategory());
        if (holder.tvTitle != null) holder.tvTitle.setText(model.getTitle());
        if (holder.tvDescription != null) holder.tvDescription.setText(model.getDescription());
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView cardView;
        TextView tvCategory, tvTitle, tvDescription;

        public ViewHolder(@NonNull View itemView, int layoutResId) {
            super(itemView);
            cardView = (MaterialCardView) itemView;

            if (layoutResId == R.layout.item_tip_card) {
                tvCategory = itemView.findViewById(R.id.tvCategory);
                tvTitle = itemView.findViewById(R.id.tvTitle);
                tvDescription = itemView.findViewById(R.id.tvDescription);
            } else if (layoutResId == R.layout.item_safety_card) {
                tvTitle = itemView.findViewById(R.id.tvCardTitle);
                tvDescription = itemView.findViewById(R.id.tvCardDescription);
            }
        }
    }
}