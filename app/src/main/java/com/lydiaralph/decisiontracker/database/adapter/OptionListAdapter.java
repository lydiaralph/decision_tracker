package com.lydiaralph.decisiontracker.database.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lydiaralph.decisiontracker.R;
import com.lydiaralph.decisiontracker.database.entity.Option;

import java.util.List;

public class OptionListAdapter extends RecyclerView.Adapter<OptionListAdapter.OptionViewHolder> {

    public class OptionViewHolder extends RecyclerView.ViewHolder {
        private final TextView wordItemView;

        private OptionViewHolder(View itemView) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.textView);
        }
    }

    private final LayoutInflater mInflater;
    private List<Option> mOptions;

    public OptionListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public OptionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new OptionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OptionViewHolder holder, int position) {
        if (mOptions != null) {
            Option current = mOptions.get(position);
            holder.wordItemView.setText(current.getOptionText());
        } else {
            holder.wordItemView.setText("No Option");
        }
    }

    public void setOptions(List<Option> words) {
        mOptions = words;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mOptions != null)
            return mOptions.size();
        else return 0;
    }
}
