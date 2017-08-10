package com.example.hamid_pc.parkingbookingsystem;


import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

public class AdminFeedbackActivity extends SingleFragmentActivity {


    public static Intent NewIntent(Context packageContext) {
        Intent intnet = new Intent(packageContext, AdminFeedbackActivity.class);
        return intnet;
    }

    @Override
    protected Fragment createFragment() {
        return AdminFeedbackListFragment.NewInstance();
    }
}
