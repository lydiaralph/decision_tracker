package com.lydiaralph.decisiontracker.database.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lydiaralph.decisiontracker.R;
import com.lydiaralph.decisiontracker.database.entity.Vote;

import java.util.List;

public class VoteListAdapter extends RecyclerView.Adapter<VoteListAdapter.VoteViewHolder> {

    public class VoteViewHolder extends RecyclerView.ViewHolder {
        private final TextView wordItemView;

        private VoteViewHolder(View itemView) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.textView);
        }
    }

    private final LayoutInflater mInflater;
    private List<Vote> mVotes;

    public VoteListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public VoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new VoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(VoteViewHolder holder, int position) {
        if (mVotes != null) {
            Vote current = mVotes.get(position);
            holder.wordItemView.setText(current.getId());
        } else {
            holder.wordItemView.setText("No Vote");
        }
    }

    public void setVotes(List<Vote> words) {
        mVotes = words;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mVotes != null)
            return mVotes.size();
        else return 0;
    }
}
