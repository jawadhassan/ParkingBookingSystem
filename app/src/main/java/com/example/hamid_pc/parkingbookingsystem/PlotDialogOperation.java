package com.example.hamid_pc.parkingbookingsystem;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class PlotDialogOperation extends DialogFragment {

    private static final String ARG_PLOT_ID = "plot_id";
    private final String TAG = "PlotDialog";
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private String mPLotId;

    public static PlotDialogOperation NewInstance(String plotId) {
        PlotDialogOperation plotDialogOperation = new PlotDialogOperation();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PLOT_ID, plotId);
        plotDialogOperation.setArguments(args);
        return plotDialogOperation;

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mPLotId = (String) getArguments().getSerializable(ARG_PLOT_ID);
        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.dialog_plot_operation)
                .setItems(R.array.plot_dialog_options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 1:
                                Log.d(TAG, "Delete Plot Clicked");

                        }
                    }
                }).create();
    }
}
