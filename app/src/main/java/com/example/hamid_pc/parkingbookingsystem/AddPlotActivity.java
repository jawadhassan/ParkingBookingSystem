package com.example.hamid_pc.parkingbookingsystem;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;


public class AddPlotActivity extends SingleFragmentActivity {

    public static Intent NewIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, AddPlotActivity.class);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        return AddPlotFragment.NewInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
