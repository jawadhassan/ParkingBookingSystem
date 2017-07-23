package com.example.hamid_pc.parkingbookingsystem;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class UserNewBookingFragment extends Fragment {

    private Button mPlot;


    public static UserNewBookingFragment NewInstance() {
        UserNewBookingFragment userNewBookingFragment = new UserNewBookingFragment();
        return userNewBookingFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_booking, container, false);
        mPlot = (Button) view.findViewById(R.id.button_plot);
        mPlot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = UserBookingEntryActivity.NewIntent(getActivity());
                startActivity(intent);

            }
        });
        return view;
    }
}
