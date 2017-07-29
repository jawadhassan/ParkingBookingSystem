package com.example.hamid_pc.parkingbookingsystem;


import android.app.Activity;
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
    private EditText mNumOfAreaEditText;

    private Button mSubmitButton;


    private String mPlotName;
    private int mNumOfArea;
    private String mPlotUuid;
    private String mAreaId;
    private String mNumOfAreaStringValue;


    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private DatabaseReference mKeyReference;
    private DatabaseReference mAreaReference;
    private Plot mPlot;
    private Area mArea;
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
        mNumOfAreaEditText = (EditText) view.findViewById(R.id.edit_text_area_num);
        mSubmitButton = (Button) view.findViewById(R.id.button_submit);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mPlotName = mPlotNameEditText.getText().toString();
                mNumOfAreaStringValue = mNumOfAreaEditText.getText().toString();
                try {
                    mNumOfArea = Integer.parseInt(mNumOfAreaStringValue);
                } catch (NumberFormatException e) {
                    Log.e(TAG, "Number Format Exception:" + e.toString());

                }

                mKeyReference = mDatabaseReference.push();
                mPlotUuid = mKeyReference.getKey();
                mPlot = new Plot(mPlotName, mNumOfArea, mPlotUuid);
                mKeyReference.setValue(mPlot);

                mAreaReference = mFirebaseDatabase.getReference("areas").child(mPlotUuid);


                for (int i = 0; i < mNumOfArea; i++) {
                    mKeyReference = mAreaReference.push();
                    mAreaId = mKeyReference.getKey();
                    mArea = new Area(mAreaId, mPlotUuid, "", "", "", false);
                    mKeyReference.setValue(mArea);
                }
                sendResult(Activity.RESULT_OK);


            }
        });
        return view;
    }

    private void sendResult(int resultCode) {
        getActivity().setResult(resultCode);
        getActivity().finish();

    }


}
