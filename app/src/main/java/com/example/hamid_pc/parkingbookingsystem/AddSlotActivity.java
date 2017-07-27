package com.example.hamid_pc.parkingbookingsystem;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;


public class AddSlotActivity extends SingleFragmentActivity {


    public static final String EXTRA_ADD_SLOT_ID =
            "com.example.hamid_pc.parkingbookingsystem.add.slot.id";

    public static Intent NewIntent(Context packageContext, String plotId) {
        Intent intent = new Intent(packageContext, AddSlotActivity.class);
        intent.putExtra(EXTRA_ADD_SLOT_ID, plotId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        String plotId = (String) getIntent().getSerializableExtra(EXTRA_ADD_SLOT_ID);
        return AddSlotFragment.NewInstance(plotId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
