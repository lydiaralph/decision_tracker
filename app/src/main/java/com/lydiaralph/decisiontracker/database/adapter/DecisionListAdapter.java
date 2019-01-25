package com.lydiaralph.decisiontracker.database.adapter;


import android.R;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lydiaralph.decisiontracker.database.entity.Decision;

import java.util.List;

public class DecisionListAdapter extends RecyclerView.Adapter<DecisionListAdapter.DecisionViewHolder> {

    public class DecisionViewHolder extends RecyclerView.ViewHolder {
        private final TextView decisionItemView;

        private DecisionViewHolder(View itemView) {
            super(itemView);
            decisionItemView = itemView.findViewById(R.id.);
        }
    }

    private final LayoutInflater mInflater;
    private List<Decision> mDecisions;

    public DecisionListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public DecisionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new DecisionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DecisionViewHolder holder, int position) {
        if (mDecisions != null) {
            Decision current = mDecisions.get(position);
            holder.decisionItemView.setText(current.getDecisionText());
        } else {
            holder.decisionItemView.setText("No Decision");
        }
    }

    public void setDecisions(List<Decision> words) {
        mDecisions = words;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mDecisions != null)
            return mDecisions.size();
        else return 0;
    }
}
