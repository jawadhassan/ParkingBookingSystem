package com.example.hamid_pc.parkingbookingsystem;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import static org.joda.time.DateTimeZone.UTC;


public class UserBookingEntryFragment extends Fragment {

    private static final String DIALOG_DATE = "DialogDate";
    private static final String DIALOG_TIME = "DialogTime";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_TIME = 1;
    private static final String ARG_PLOT_ID = "plot_id";
    private final String TAG = "BookingEntryFragment";
    private DateTime mDate;
    private DateTime mDateTime;
    private Long mDateInMilli;
    private String mPlotId;
    private int mHour;
    private EditText mDateEditText;
    private EditText mTimeEditText;
    private EditText mHourPickerText;
    private Button mSearchButton;
    private DatabaseReference mDatabaseReference;
    private DatabaseReference mBookingReference;
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
        mBookingReference = mFirebaseDatabase.getReference("bookings");
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
                    public void onClick(final View v) {


                        mDate = mDate.hourOfDay().setCopy(mDateTime.getHourOfDay());
                        mDate = mDate.minuteOfHour().setCopy(mDateTime.getMinuteOfHour());
                        mDate = mDate.secondOfMinute().setCopy(mDateTime.getSecondOfMinute());

                        mDateInMilli = mDate.toDateTime(UTC).getMillis();


                        mBookingReference.orderByChild("areaId").equalTo(viewHolder.mArea.getAreaId()).addListenerForSingleValueEvent(new ValueEventListener() {

                            Booking booking = new Booking(mPlotId, viewHolder.mArea.getAreaId(),
                                    mUserId, viewHolder.mArea.getAreaNum(), mDateInMilli, mHour);

                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    for (DataSnapshot preBookedSnapShot : dataSnapshot.getChildren()) {

                                        Booking preBooking = preBookedSnapShot.getValue(Booking.class);
                                        DateTime preBookedStartDateTime = new DateTime(preBooking.getStartDateTime(), DateTimeZone.UTC);
                                        DateTime preBookedEndDateTime = preBookedStartDateTime.plusHours(preBooking.getHour());
                                        DateTime BookingEndDateTime = mDate.plus(mHour);

                                        Interval intervalOne = new Interval(preBookedStartDateTime, preBookedEndDateTime);
                                        Interval intervalTwo = new Interval(mDate, BookingEndDateTime);


                                        if (intervalOne.overlaps(intervalTwo)) {
                                            Log.d(TAG, "Both dates overlap");
                                            // mBookingReference.push().setValue(booking);


                                        } else if (intervalOne.getEnd().isAfter(intervalTwo.getStart())) {
                                            Log.d(TAG, "Start of Inerval One overlaps Interval two");
                                        } else if (intervalOne.getStart().isBefore(intervalTwo.getEnd())) {
                                            Log.d(TAG, "Start of Interval One and End of Interval Overlap");
                                        } else {
                                            Log.d(TAG, "EveryThings seems to be fine");
                                    }

                                        // Inner coditional statement ends here
                                    }

                                } else {
                                    mBookingReference.push().setValue(booking);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });


//                        mAreaReference = mAdapter.getRef(position);
//                        mAreaReference.child("booked").setValue(true);
//                        mAreaReference.child("userId").setValue(mUserId);
//                        mAreaReference.child("bookingStartDate").setValue(mDate);
//                        mAreaReference.child("bookingStartTime").setValue(mDateTime);
//                        mAreaReference.child("bookingHour").setValue(mHour);



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
            mDate = (DateTime) data
                    .getSerializableExtra(DatePickerFragment.EXTRA_DATE);

            DateTimeFormatter fmt = DateTimeFormat.forPattern("d MMMM, yyyy");
            String str = mDate.toString(fmt);
            mDateEditText.setText(str);
        } else if (requestCode == REQUEST_TIME) {
            mDateTime = (DateTime) data
                    .getSerializableExtra(TimePickerFragment.EXTRA_TIME);
            DateTimeFormatter fmt = DateTimeFormat.forPattern("hh:mm");
            String str = mDateTime.toString(fmt);
            mTimeEditText.setText(str);
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

        }
    }
}
