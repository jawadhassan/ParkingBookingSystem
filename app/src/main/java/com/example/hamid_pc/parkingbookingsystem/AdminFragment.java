package com.example.hamid_pc.parkingbookingsystem;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class AdminFragment extends Fragment {

    private final String TAG = "AdminActivity";
    private Button mViewParkingButton;
    private Button mViewBookingButton;
    private Button mViewUserButton;
    private Button mFeedBackButton;

    public static AdminFragment NewInstance() {
        AdminFragment adminFragment = new AdminFragment();
        return adminFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.miSignOut:
                FirebaseAuth.getInstance().signOut();
                Intent intent = AuthenticationActivity.newIntent(getActivity());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                getActivity().finish();
                return true;


        }

        return true;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin, container, false);

        mViewParkingButton = (Button) view.findViewById(R.id.button_view_parking);
        mViewBookingButton = (Button) view.findViewById(R.id.button_view_booking);
        mViewUserButton = (Button) view.findViewById(R.id.button_view_user);
        mFeedBackButton = (Button) view.findViewById(R.id.button_feedback);


        mViewBookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mFeedBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AdminFeedbackActivity.NewIntent(getActivity());
                startActivity(intent);
            }
        });

        mViewParkingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = AdminPlotListActivity.NewIntent(getActivity());
                startActivity(intent);

            }
        });


        mViewUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AdminUserListActivity.NewIntent(getActivity());
                startActivity(intent);
            }
        });

        setHasOptionsMenu(true);

        return view;
    }
}
