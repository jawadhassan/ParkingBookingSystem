package com.example.hamid_pc.parkingbookingsystem;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class AdminUserDetailFragment extends Fragment {
    private static final String TAG = "AdminUserDetailFragment";
    private static final String ARG_USER_ID = "user_id";
    Query mQuery;
    private DatabaseReference mDatabaseReference;
    private DatabaseReference mBookingReference;
    private FirebaseDatabase mFirebaseDatabase;
    private TextView mUserNameTextView;
    private TextView mUserEmailTextView;
    private String mUserId;


// TODO: 8/6/2017 Add On Activity Result or Finish so that user could be notified that he deleted the user

    public static AdminUserDetailFragment NewInstance(String userId) {
        AdminUserDetailFragment adminDetailFragment = new AdminUserDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_USER_ID, userId);
        adminDetailFragment.setArguments(args);
        return adminDetailFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_user_detail, container, false);
        mUserNameTextView = (TextView) view.findViewById(R.id.text_view_user_name_value);
        mUserEmailTextView = (TextView) view.findViewById(R.id.text_view_user_email_value);

        mQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                User user = dataSnapshot.getValue(User.class);
                mUserNameTextView.setText(user.getmName());
                mUserEmailTextView.setText(user.getmEmail());
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

        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserId = (String) getArguments().getSerializable(ARG_USER_ID);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("users");
        mBookingReference = mFirebaseDatabase.getReference("bookings");
        mQuery = mDatabaseReference.orderByChild("mUuid").equalTo(mUserId);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuInflater inflater1 = getActivity().getMenuInflater();
        inflater1.inflate(R.menu.delete_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.miDelete:
                mQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot user : dataSnapshot.getChildren()) {

                            user.getRef().removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                mBookingReference.orderByChild("userId").equalTo(mUserId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot booking : dataSnapshot.getChildren()) {
                            booking.getRef().removeValue();
                            getActivity().finish();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        }

        return true;
    }

}
