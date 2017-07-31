package com.example.hamid_pc.parkingbookingsystem;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

public class UserBookingEntryActivity extends SingleFragmentActivity {

    public static final String EXTRA_PLOT =
            "com.example.hamid_pc.parkingbookingsystem.user_booking_entry";
    private final String TAG = "UserBookingEntry";

    public static Intent NewIntent(Context packageContext, String plotUid) {
        Intent intent = new Intent(packageContext, UserBookingEntryActivity.class);
        intent.putExtra(EXTRA_PLOT, plotUid);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        String plotId = (String) getIntent().getSerializableExtra(EXTRA_PLOT);
        return UserBookingEntryFragment.NewInstance(plotId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "In User Booking Activity");
    }


}
