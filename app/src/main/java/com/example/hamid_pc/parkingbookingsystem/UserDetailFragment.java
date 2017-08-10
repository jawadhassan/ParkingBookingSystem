package com.example.hamid_pc.parkingbookingsystem;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserDetailFragment extends Fragment {

    private static final String TAG = "UserDetailFragment";
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private TextView mTextViewUser;
    private TextView mTextViewUserEmail;
    private String mUsername;
    private String mUserEmail;
    private String mUid;

    public static UserDetailFragment NewInstance() {
        UserDetailFragment userDetailFragment = new UserDetailFragment();
        return userDetailFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("users");
        mUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_detail, container, false);
        mTextViewUser = (TextView) view.findViewById(R.id.text_view_user_name_value);


        mTextViewUserEmail = (TextView) view.findViewById(R.id.text_view_user_email_value);

        mDatabaseReference.orderByChild("mUuid").equalTo(mUid).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                User user = dataSnapshot.getValue(User.class);
                mTextViewUser.setText(user.getmName());
                mTextViewUserEmail.setText(user.getmEmail());

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return view;
    }
}
