package com.example.hamid_pc.parkingbookingsystem;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

public class AdminBookingListActivity extends SingleFragmentActivity {

    public static Intent NewIntet(Context packageContext) {
        Intent intent = new Intent(packageContext, AdminBookingListActivity.class);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        return AdminBookingListFragment.NewInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
