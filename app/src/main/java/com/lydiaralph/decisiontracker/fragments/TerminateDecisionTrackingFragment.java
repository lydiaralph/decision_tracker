package com.lydiaralph.decisiontracker.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import com.lydiaralph.decisiontracker.R;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;


public class TerminateDecisionTrackingFragment extends DialogFragment {

    public interface Listener {
        public void terminateDecisionTrackingEarly(DialogFragment dialog, int decisionId);
        public void keepTrackingDecision(DialogFragment dialog);
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
                    + " must implement NoticeDialogListener");
        }
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.stop_tracking_decision_question))
                .setMessage(getString(R.string.stop_tracking_decision_explanation))
                .setPositiveButton(getString(R.string.stop_tracking_decision), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.terminateDecisionTrackingEarly(TerminateDecisionTrackingFragment.this, savedInstanceState.getInt("decisionId"));
                    }
                })
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.keepTrackingDecision(TerminateDecisionTrackingFragment.this);
                    }
                });
        return builder.create();
    }
}