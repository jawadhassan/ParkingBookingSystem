package com.example.hamid_pc.parkingbookingsystem;


import android.os.Bundle;
import android.support.v4.app.Fragment;

public class UserActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return UserFragment.NewInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
