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

public class UserFragment extends Fragment {

    private static final String TAG = "UserFragment";

    private Button mNewBooking;
    private Button mViewBooking;


    public static UserFragment NewInstance() {
        UserFragment userFragment = new UserFragment();
        return userFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuInflater inflater1 = getActivity().getMenuInflater();
        inflater1.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.miProfile:
                Intent UserIntent = UserDetailActivity.NewIntent(getActivity());
                startActivity(UserIntent);
                return true;
            case R.id.miSignOut:
                FirebaseAuth.getInstance().signOut();
                Intent intent = AuthenticationActivity.newIntent(getActivity());
                startActivity(intent);
                return true;

            case R.id.miFeedback:
                Intent FeedBackIntent = UserFeedbackActivity.NewIntent(getActivity());
                startActivity(FeedBackIntent);
                return true;


        }

        return true;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        mNewBooking = (Button) view.findViewById(R.id.button_new_booking);
        mViewBooking = (Button) view.findViewById(R.id.button_view_booking);


        mNewBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = UserNewBookingActivity.NewIntent(getActivity());
                startActivity(intent);
            }
        });

        mViewBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = UserBookingListActivity.NewIntent(getActivity());
                startActivity(intent);

            }
        });




        setHasOptionsMenu(true);

        return view;
    }
}
