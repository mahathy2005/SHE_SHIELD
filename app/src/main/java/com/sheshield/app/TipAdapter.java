package com.sheshield.app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TipAdapter extends RecyclerView.Adapter<TipAdapter.TipViewHolder> {

    private List<TipModel> tipList;

    public TipAdapter(List<TipModel> tipList) {
        this.tipList = tipList;
    }

    @NonNull
    @Override
    public TipViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tip_card, parent, false);
        return new TipViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TipViewHolder holder, int position) {
        TipModel tip = tipList.get(position);
        holder.tvCategory.setText(tip.getCategory());
        holder.tvTitle.setText(tip.getTitle());
        holder.tvDescription.setText(tip.getDescription());
    }

    @Override
    public int getItemCount() {
        return tipList.size();
    }

    static class TipViewHolder extends RecyclerView.ViewHolder {
        TextView tvCategory, tvTitle, tvDescription;

        public TipViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
        }
    }
}