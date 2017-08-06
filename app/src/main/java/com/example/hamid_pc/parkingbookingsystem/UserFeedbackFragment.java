package com.example.hamid_pc.parkingbookingsystem;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserFeedbackFragment extends Fragment {

    private EditText mEditTextFeedBack;
    private Button mSubmitButton;
    private String mUserId;
    private String mFeedBackId;
    private String mfeedbackString;
    private Feedback mFeedback;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private String TAG = "User FeedbackActivity";

    public static UserFeedbackFragment NewInstance() {
        UserFeedbackFragment userFeedbackFragment = new UserFeedbackFragment();
        return userFeedbackFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("feedbacks");
        mUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Log.d(TAG, "IN User Feedback");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);
        mEditTextFeedBack = (EditText) view.findViewById(R.id.edit_text_user_feedback);


        mSubmitButton = (Button) view.findViewById(R.id.button_feedback_submit);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mfeedbackString = mEditTextFeedBack.getText().toString();
                mFeedback = new Feedback(mUserId, mfeedbackString, "");
                mDatabaseReference.push().setValue(mFeedback);
            }
        });
        return view;
    }
}
