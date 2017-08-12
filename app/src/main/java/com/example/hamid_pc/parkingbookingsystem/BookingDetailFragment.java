package com.example.hamid_pc.parkingbookingsystem;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;


public class BookingDetailFragment extends Fragment {
    private static final String AREA_NUM = "areaNum";
    private static final String PLOT_ID = "plotId";
    private static final String USER_ID = "userId";
    private static final String START_DATE = "startId";
    private static final String HOUR = "hour";
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mUserReference;
    private DatabaseReference mPlotReference;
    private TextView mPlotTextView;
    private TextView mAreaTextView;
    private TextView mUserTextView;
    private TextView mBookingHourView;
    private TextView mBookingDateTextView;
    private TextView mBookingTimeTextView;
    private int mAreaNum;
    private String mPlotId;
    private String mUserId;
    private Long mStartDate;
    private int mHour;
    private String mPlotName;
    private String mUserName;
    private String TAG = "BookingDetailActivity";

    public static BookingDetailFragment NewInstance(String plotId, String userId, int areaNum, Long StartDate, int hour) {
        BookingDetailFragment bookingDetailFragment = new BookingDetailFragment();
        Bundle args = new Bundle();
        args.putInt(AREA_NUM, areaNum);
        args.putString(PLOT_ID, plotId);
        args.putString(USER_ID, userId);
        args.putLong(START_DATE, StartDate);
        args.putInt(HOUR, hour);
        bookingDetailFragment.setArguments(args);
        return bookingDetailFragment;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.delete_menu, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAreaNum = getArguments().getInt(AREA_NUM);
        mPlotId = getArguments().getString(PLOT_ID);
        mUserId = getArguments().getString(USER_ID);
        Log.d(TAG, mUserId);
        mStartDate = getArguments().getLong(START_DATE);
        mHour = getArguments().getInt(HOUR);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mUserReference = mFirebaseDatabase.getReference("users");
        mPlotReference = mFirebaseDatabase.getReference("plots");


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking_detail, container, false);
        mPlotTextView = (TextView) view.findViewById(R.id.text_view_plot_name);
        mAreaTextView = (TextView) view.findViewById(R.id.text_view_area_num);
        mUserTextView = (TextView) view.findViewById(R.id.text_view_user_name);
        mBookingHourView = (TextView) view.findViewById(R.id.text_view_booking_hour);
        mBookingDateTextView = (TextView) view.findViewById(R.id.text_view_booking_date);
        mBookingTimeTextView = (TextView) view.findViewById(R.id.text_view_booking_time);
        setHasOptionsMenu(true);

        mPlotReference.orderByChild("uuid").equalTo(mPlotId).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Plot plot = dataSnapshot.getValue(Plot.class);
                mPlotName = plot.getPlotName();
                mPlotTextView.setText(mPlotName);
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

        mUserReference.orderByChild("mUuid").equalTo(mUserId).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                User user = dataSnapshot.getValue(User.class);
                mUserName = user.getmName();
                Log.d(TAG, mUserName);
                mUserTextView.setText(mUserName);
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


        mAreaTextView.setText(getResources().getString(R.string.area_num, mAreaNum));


        DateTime startDateTime = new DateTime(Long.valueOf(mStartDate), DateTimeZone.UTC);
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("d MMMM, yyyy");
        String strDate = startDateTime.toString(dateTimeFormatter);

        DateTimeFormatter timeFormatter = DateTimeFormat.forPattern("h:mm");
        String strTime = startDateTime.toString(timeFormatter);

        mBookingDateTextView.setText(strDate);
        mBookingTimeTextView.setText(strTime);


        mBookingHourView.setText(getResources().getString(R.string.booking_hour, mHour));
        return view;
    }
}
