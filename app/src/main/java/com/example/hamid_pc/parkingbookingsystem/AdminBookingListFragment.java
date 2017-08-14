package com.example.hamid_pc.parkingbookingsystem;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class AdminBookingListFragment extends Fragment {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private DividerItemDecoration mDividerItemDecoration;
    private RecyclerView mRecyclerView;
    private FirebaseRecyclerAdapter<Booking, BookingViewHolder> mAdapter;


    public static AdminBookingListFragment NewInstance() {
        AdminBookingListFragment adminBookingListFragment = new AdminBookingListFragment();
        return adminBookingListFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("bookings");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_booking_list, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.booking_list_recycler_view);
        UpdateUI();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    public void UpdateUI() {
        mAdapter = new FirebaseRecyclerAdapter<Booking, BookingViewHolder>(
                Booking.class,
                R.layout.list_booking,
                BookingViewHolder.class,
                mDatabaseReference
        ) {
            @Override
            protected void populateViewHolder(BookingViewHolder viewHolder, Booking model, final int position) {
                Booking booking = getItem(position);

                Long startDateTimeInMillis = model.getStartDateTime();

                DateTime startDateTime = new DateTime(Long.valueOf(startDateTimeInMillis), DateTimeZone.UTC);
                DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("d MMMM, yyyy");
                String strDate = startDateTime.toString(dateTimeFormatter);
                viewHolder.mBookingDateTextView.setText(strDate);
                dateTimeFormatter = DateTimeFormat.forPattern("hh:mm ");
                String strTime = startDateTime.toString(dateTimeFormatter);
                viewHolder.mBookingTimeTextView.setText(strTime);
                viewHolder.mBookingAreaTextView.setText(getString(R.string.area_num, model.getAreaNum()));
                viewHolder.bindView(booking);

            }
        };


        mDividerItemDecoration = new DividerItemDecoration(getContext(),
                new LinearLayoutManager(getContext()).getOrientation());
        mRecyclerView.addItemDecoration(mDividerItemDecoration);


        mRecyclerView.setAdapter(mAdapter);


    }

    public static class BookingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Booking mBooking;
        TextView mBookingAreaTextView;
        TextView mBookingDateTextView;
        TextView mBookingTimeTextView;

        public BookingViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mBookingAreaTextView = (TextView) itemView.findViewById(R.id.list_item_booking_area);
            mBookingTimeTextView = (TextView) itemView.findViewById(R.id.list_time);
            mBookingDateTextView = (TextView) itemView.findViewById(R.id.list_item_date);


        }

        @Override
        public void onClick(View v) {
            AppCompatActivity appCompatActivity = (AppCompatActivity) v.getContext();
            if (appCompatActivity instanceof AdminBookingListActivity) {
                AdminBookingListActivity adminBookingListActivity = (AdminBookingListActivity) appCompatActivity;
                Intent intent = BookingDetailActivity.NewIntent(adminBookingListActivity,
                        mBooking.getPlotId(), mBooking.getUserId(), mBooking.getStartDateTime(),
                        mBooking.getHour(), mBooking.getAreaNum());


                adminBookingListActivity.startActivity(intent);

            }

        }

        public void bindView(Booking booking) {
            mBooking = booking;
        }


    }
}



