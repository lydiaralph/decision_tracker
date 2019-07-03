package com.lydiaralph.decisiontracker.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import com.lydiaralph.decisiontracker.R;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;


public class DeleteDataFragment extends DialogFragment {

    public static DeleteDataFragment newInstance() {
        return new DeleteDataFragment();
    }

    public interface Listener {
        void deleteAllData(DialogFragment dialog);
        void cancelDelete(DialogFragment dialogFragment);
    }

    Listener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (Listener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement DeleteDataFragment.Listener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.delete_all_data_please_confirm))
                .setMessage(getString(R.string.delete_all_data_explanation))
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.deleteAllData(DeleteDataFragment.this);
                    }
                })
                .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.cancelDelete(DeleteDataFragment.this);
                    }
                });
        return builder.create();
    }
}