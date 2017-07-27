package com.example.hamid_pc.parkingbookingsystem;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddPlotFragment extends Fragment {


    //TODO:Solve the Screen Adjustability Issue and Ask Someone Experienced


    private EditText mPlotNameEditText;
    private EditText mNumOfSlotEditText;

    private Button mSubmitButton;


    private String mPlotName;
    private int mNumOfSlots;
    private String mPlotUuid;
    private String mNumOfSlotsStringValue;


    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private DatabaseReference mKeyReference;
    private Plot mPlot;
    private String TAG = "AddPlotActivity";


    public static AddPlotFragment NewInstance() {
        AddPlotFragment addPlotFragment = new AddPlotFragment();
        return addPlotFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("plots");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_add_plot, container, false);
        mPlotNameEditText = (EditText) view.findViewById(R.id.edit_text_plot_name);
        mNumOfSlotEditText = (EditText) view.findViewById(R.id.edit_text_plot_number);
        mSubmitButton = (Button) view.findViewById(R.id.button_submit);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mPlotName = mPlotNameEditText.getText().toString();
                mNumOfSlotsStringValue = mNumOfSlotEditText.getText().toString();
                try {
                    mNumOfSlots = Integer.parseInt(mNumOfSlotsStringValue);
                } catch (NumberFormatException e) {
                    Log.e(TAG, "Number Format Exception:" + e.toString());

                }

                mKeyReference = mDatabaseReference.push();
                mPlotUuid = mKeyReference.getKey();
                mPlot = new Plot(mPlotName, mNumOfSlots, mPlotUuid);
                mKeyReference.setValue(mPlot);
                Intent intent = AddSlotActivity.NewIntent(getActivity(), mPlotUuid);
                startActivity(intent);

            }
        });
        return view;
    }
}
