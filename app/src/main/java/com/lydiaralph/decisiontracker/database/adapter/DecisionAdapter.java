package com.lydiaralph.decisiontracker.database.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lydiaralph.decisiontracker.R;

import com.lydiaralph.decisiontracker.database.entity.Decision;

import java.util.List;

public class DecisionAdapter extends RecyclerView.Adapter<DecisionAdapter.DecisionViewHolder> {

    class DecisionViewHolder extends RecyclerView.ViewHolder {
        private final TextView decisionItemView;

        private DecisionViewHolder(View itemView) {
            super(itemView);
            decisionItemView = itemView.findViewById(R.id.textView);
        }
    }

    private final LayoutInflater mInflater;
    private List<Decision> decisions; // Cached copy of decisions

    public DecisionAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @Override
    public DecisionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new DecisionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DecisionViewHolder holder, int position) {
        if (decisions != null) {
            Decision current = decisions.get(position);
            holder.decisionItemView.setText(current.getDecisionText());
        } else {
            // Covers the case of data not being ready yet.
            holder.decisionItemView.setText("No decision");
        }
    }

    public void setDecisions(List<Decision> decisions){
        this.decisions = decisions;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (decisions != null)
            return decisions.size();
        else return 0;
    }
}
