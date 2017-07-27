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

public class AddSlotFragment extends Fragment {

    private static final String ARG_PLOT_ID =
            "plot_id";
    private final String TAG = "AddSlotActivity";
    private Button mSubmitButton;
    private EditText mSlotNameEditText;
    private EditText mNumOfAreasEditText;
    private String mSlotName;
    private String mSlotUId;
    private String mPlotId;
    private int mAreaNumber;
    private String mAreaNumberStringValue;
    private Slot mSlot;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private DatabaseReference mKeyReference;

    public static AddSlotFragment NewInstance(String plotId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_PLOT_ID, plotId);
        AddSlotFragment addSlotFragment = new AddSlotFragment();
        addSlotFragment.setArguments(args);
        return addSlotFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPlotId = (String) getArguments().getSerializable(ARG_PLOT_ID);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("slots");


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_add_slot, container, false);
        mSubmitButton = (Button) view.findViewById(R.id.button_submit);
        mSlotNameEditText = (EditText) view.findViewById(R.id.edit_text_slot_name);
        mNumOfAreasEditText = (EditText) view.findViewById(R.id.edit_text_number_area);


        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSlotName = mSlotNameEditText.getText().toString();
                mAreaNumberStringValue = mNumOfAreasEditText.getText().toString();
                try {
                    mAreaNumber = Integer.parseInt(mAreaNumberStringValue);
                } catch (NumberFormatException e) {
                    Log.e(TAG, "Number Format Exception:" + e.toString());

                }

                mKeyReference = mDatabaseReference.push();
                mSlotUId = mKeyReference.getKey();
                mSlot = new Slot(mSlotName, mSlotUId, mPlotId, mAreaNumber);
                mKeyReference.setValue(mSlot);
                Intent intent = AdminActivity.NewIntent(getActivity());
                startActivity(intent);

            }
        });


        return view;
    }
}
