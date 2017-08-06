package com.example.hamid_pc.parkingbookingsystem;


import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

public class AdminUserDetailActivity extends SingleFragmentActivity {

    public static final String EXTRA_USER_ID =
            "com.example.hamid_pc.parkingbookingsystem.user_id";

    public static Intent NewIntent(Context packageContext, String userId) {
        Intent intent = new Intent(packageContext, AdminUserDetailActivity.class);
        intent.putExtra(EXTRA_USER_ID, userId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        String userId = (String) getIntent().getSerializableExtra(EXTRA_USER_ID);
        return AdminUserDetailFragment.NewInstance(userId);
    }
}
