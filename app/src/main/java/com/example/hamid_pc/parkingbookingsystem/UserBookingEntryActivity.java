package com.example.hamid_pc.parkingbookingsystem;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

public class UserBookingEntryActivity extends SingleFragmentActivity {

    private final String TAG = "UserBookingEntry";

    public static Intent NewIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, UserBookingEntryActivity.class);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        return UserBookingEntryFragment.NewInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "In User Booking Activity");
    }


}
