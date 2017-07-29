package com.example.hamid_pc.parkingbookingsystem;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.Date;


public class UserBookingEntryFragment extends Fragment {

    private static final String DIALOG_DATE = "DialogDate";
    private static final String DIALOG_TIME = "DialogTime";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_TIME = 1;
    private final String TAG = "BookingEntryFragment";
    private EditText mDateEditText;
    private EditText mTimeEditText;


    public static UserBookingEntryFragment NewInstance() {
        UserBookingEntryFragment userBookingEntryFragment = new UserBookingEntryFragment();
        return userBookingEntryFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking_entry, container, false);
        mDateEditText = (EditText) view.findViewById(R.id.DatePicker);
        mTimeEditText = (EditText) view.findViewById(R.id.TimePicker);
        mDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = new DatePickerFragment();
                dialog.setTargetFragment(UserBookingEntryFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);


            }
        });
        mTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager manager = getFragmentManager();
                TimePickerFragment dialog = new TimePickerFragment();
                dialog.setTargetFragment(UserBookingEntryFragment.this, REQUEST_TIME);
                dialog.show(manager, DIALOG_TIME);

            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data
                    .getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mDateEditText.setText(DateFormat.format("EEEE,MMMM d,yyyy", date));
        } else if (requestCode == REQUEST_TIME) {
            Date date = (Date) data
                    .getSerializableExtra(TimePickerFragment.EXTRA_TIME);
            mTimeEditText.setText(DateFormat.format("h:mm a", date));
        }

    }
}
