package com.example.hamid_pc.parkingbookingsystem;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;


public class UserBookingEntryFragment extends Fragment {

    private static final String DIALOG_DATE = "DialogDate";
    private static final String DIALOG_TIME = "DialogTime";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_TIME = 1;
    private static final String ARG_PLOT_ID = "plot_id";
    static Date sDate;
    static Date sDateTime;
    private final String TAG = "BookingEntryFragment";
    private String mPlotId;
    private EditText mDateEditText;
    private EditText mTimeEditText;
    private Button mSearchButton;
    private DatabaseReference mDatabaseReference;
    private FirebaseDatabase mFirebaseDatabase;
    private RecyclerView mRecyclerView;
    private FirebaseRecyclerAdapter<Area, AreaViewHolder> mAdapter;


    public static UserBookingEntryFragment NewInstance(String plotId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_PLOT_ID, plotId);
        UserBookingEntryFragment userBookingEntryFragment = new UserBookingEntryFragment();
        userBookingEntryFragment.setArguments(args);
        return userBookingEntryFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPlotId = (String) getArguments().getSerializable(ARG_PLOT_ID);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("areas").child(mPlotId);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking_entry, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_area);
        mSearchButton = (Button) view.findViewById(R.id.button_search);
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
        UpdateUI();
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecyclerView.setVisibility(View.VISIBLE);

            }
        });
        return view;
    }


    public void UpdateUI() {
        mAdapter = new FirebaseRecyclerAdapter<Area, AreaViewHolder>(
                Area.class,
                R.layout.list_area,
                AreaViewHolder.class,
                mDatabaseReference
        ) {
            @Override
            protected void populateViewHolder(AreaViewHolder viewHolder, Area model, int position) {
                Area area = getItem(position);
                viewHolder.bindView(area);

            }
        };

        mRecyclerView.setAdapter(mAdapter);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_DATE) {
            sDate = (Date) data
                    .getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mDateEditText.setText(DateFormat.format("EEEE,MMMM d,yyyy", sDate));
        } else if (requestCode == REQUEST_TIME) {
            sDateTime = (Date) data
                    .getSerializableExtra(TimePickerFragment.EXTRA_TIME);
            mTimeEditText.setText(DateFormat.format("h:mm a", sDateTime));
        }

    }

    public static class AreaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Area mArea;
        Button mAreaButton;

        public AreaViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mAreaButton = (Button) itemView.findViewById(R.id.button_area);


        }

        @Override
        public void onClick(View v) {

            mArea.setBooked(true);
        }

        public void bindView(Area area) {
            mArea = area;
            if (mArea.getBooked()) {
                mAreaButton.setEnabled(false);
            }
        }
    }
}
