package com.example.hamid_pc.parkingbookingsystem;


import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

public class BookingDetailActivity extends SingleFragmentActivity {

    public static final String EXTRA_AREA_NUM =
            "com.example.hamid_pc.parkingbookingsystem.area.num";
    public static final String EXTRA_PLOT_ID =
            "com.example.hamid_pc.parkingbookingsystem.plot.id";

    public static final String EXTRA_USER_ID =
            "com.example.hamid_pc.parkingbookingsystem.user.id";

    public static final String EXTRA_START_DATE =
            "com.example.hamid_pc.parkingbookingsystem.start.date";

    public static final String EXTRA_HOUR =
            "com.example.hamid_pc.parkingbookingsystem.hour";


    public static Intent NewIntent(Context packageContext, String plotId, String userId, Long startDate, int hour, int areaNum) {
        Intent intent = new Intent(packageContext, BookingDetailActivity.class);
        intent.putExtra(EXTRA_AREA_NUM, areaNum);
        intent.putExtra(EXTRA_PLOT_ID, plotId);
        intent.putExtra(EXTRA_USER_ID, userId);
        intent.putExtra(EXTRA_START_DATE, startDate);
        intent.putExtra(EXTRA_HOUR, hour);

        return intent;
    }

    @Override
    protected Fragment createFragment() {

        String plotId = getIntent().getStringExtra(EXTRA_PLOT_ID);
        String userId = getIntent().getStringExtra(EXTRA_USER_ID);
        Long startDate = getIntent().getLongExtra(EXTRA_START_DATE, 0L);
        int hour = getIntent().getIntExtra(EXTRA_HOUR, 0);
        int areaNum = getIntent().getIntExtra(EXTRA_AREA_NUM, 0);
        return BookingDetailFragment.NewInstance(plotId, userId, areaNum, startDate, hour);
    }
}
