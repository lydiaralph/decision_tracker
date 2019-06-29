package com.lydiaralph.decisiontracker.database.adapter;

/*
 * Copyright (C) 2019 Lydia Ralph
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Derived from https://github.com/googlecodelabs/android-room-with-a-view
 *
 * Modified: 'DecisionAdapter' rather than 'WordAdapter'. Added null checks. Migrated to AndroidX.
 */

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lydiaralph.decisiontracker.R;
import com.lydiaralph.decisiontracker.activities.ViewDecisionsCategoryActivity;
import com.lydiaralph.decisiontracker.activities.VoteActivity;
import com.lydiaralph.decisiontracker.database.entity.Decision;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class VoteDecisionAdapter extends RecyclerView.Adapter<VoteDecisionAdapter.DecisionViewHolder> {

    class DecisionViewHolder extends RecyclerView.ViewHolder {
        private final TextView decisionItemView;

        private DecisionViewHolder(View itemView) {
            super(itemView);
            decisionItemView = itemView.findViewById(R.id.textView);
        }
    }

    private final LayoutInflater mInflater;
    private List<Decision> decisions; // Cached copy of decisions

    public VoteDecisionAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

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
            holder.decisionItemView.setTextAppearance(holder.decisionItemView.getContext(), R.style.TextStyle);

            holder.decisionItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentViewDecisionDetail = new Intent(holder.decisionItemView.getContext(),
                            VoteActivity.class);
                    intentViewDecisionDetail.putExtra(ViewDecisionsCategoryActivity.VIEW_DECISION_ID, current.getId());
                    holder.decisionItemView.getContext().startActivity(intentViewDecisionDetail);
                }
            });

        } else {
            // Covers the case of data not being ready yet.
            holder.decisionItemView.setText("No decision");
        }
    }

    public void setDecisions(List<Decision> decisions) {
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
