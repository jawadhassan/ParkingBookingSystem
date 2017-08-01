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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.Date;


public class UserBookingEntryFragment extends Fragment {

    private static final String DIALOG_DATE = "DialogDate";
    private static final String DIALOG_TIME = "DialogTime";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_TIME = 1;
    private static final String ARG_PLOT_ID = "plot_id";
    private final String TAG = "BookingEntryFragment";
    private Date mDate;
    private Date mDateTime;
    private String mPlotId;
    private int mHour;
    private EditText mDateEditText;
    private EditText mTimeEditText;
    private EditText mHourPickerText;
    private Button mSearchButton;
    private DatabaseReference mDatabaseReference;
    private DatabaseReference mAreaReference;
    private FirebaseDatabase mFirebaseDatabase;
    private String mUserId;
    private RecyclerView mRecyclerView;
    private FirebaseRecyclerAdapter<Area, AreaViewHolder> mAdapter;
    private Query mQuery;

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
        mDatabaseReference = mFirebaseDatabase.getReference("areas");
        mQuery = mDatabaseReference.orderByChild("plotId").equalTo(mPlotId);
        mUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking_entry, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_area);
        mSearchButton = (Button) view.findViewById(R.id.button_search);
        mDateEditText = (EditText) view.findViewById(R.id.DatePicker);
        mTimeEditText = (EditText) view.findViewById(R.id.TimePicker);
        mHourPickerText = (EditText) view.findViewById(R.id.number_picker_hours);
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
                mHour = Integer.parseInt(mHourPickerText.getText().toString());

            }
        });
        return view;
    }


    public void UpdateUI() {
        mAdapter = new FirebaseRecyclerAdapter<Area, AreaViewHolder>(
                Area.class,
                R.layout.list_area,
                AreaViewHolder.class,
                mQuery
        ) {
            @Override
            protected void populateViewHolder(final AreaViewHolder viewHolder, Area model, final int position) {
                Area area = getItem(position);
                viewHolder.bindView(area);
                viewHolder.mAreaButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mAreaReference = mAdapter.getRef(position);
                        mAreaReference.child("booked").setValue(true);
                        mAreaReference.child("userId").setValue(mUserId);
                        mAreaReference.child("bookingStartDate").setValue(mDate);
                        mAreaReference.child("bookingStartTime").setValue(mDateTime);
                        mAreaReference.child("bookingHour").setValue(mHour);

                        v.setEnabled(false);
                    }
                });

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
            mDate = (Date) data
                    .getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mDateEditText.setText(DateFormat.format("EEEE,MMMM d,yyyy", mDate));
        } else if (requestCode == REQUEST_TIME) {
            mDateTime = (Date) data
                    .getSerializableExtra(TimePickerFragment.EXTRA_TIME);
            mTimeEditText.setText(DateFormat.format("h:mm a", mDateTime));
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

        }

        public void bindView(Area area) {
            mArea = area;
            if (mArea.getBooked()) {
                mAreaButton.setEnabled(false);
            }
        }
    }
}
