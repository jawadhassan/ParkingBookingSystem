package com.example.hamid_pc.parkingbookingsystem;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserPlotListFragment extends Fragment {


    private final String TAG = "UserPlotListActivity";
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private RecyclerView mRecyclerView;
    private FirebaseRecyclerAdapter<Plot, PlotViewHolder> mAdapter;


    public static UserPlotListFragment NewInstance() {
        UserPlotListFragment userPlotListFragment = new UserPlotListFragment();
        return userPlotListFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("plots");
        Log.d(TAG, "In User Plot List");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_plot_list, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.plot_recycler_view);
        UpdateUI();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    public void UpdateUI() {
        mAdapter = new FirebaseRecyclerAdapter<Plot, PlotViewHolder>(
                Plot.class,
                R.layout.list_plot,
                PlotViewHolder.class,
                mDatabaseReference
        ) {
            @Override
            protected void populateViewHolder(PlotViewHolder viewHolder, Plot model, int position) {
                viewHolder.mPlotNameTextView.setText(model.getPlotName());
                viewHolder.mAreaNumTextView.setText("" + model.getNumOfArea());
                Plot plot = getItem(position);
                viewHolder.bindView(plot);

            }


        };

        mRecyclerView.setAdapter(mAdapter);
    }


    public static class PlotViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Plot mPlot;
        TextView mPlotNameTextView;
        TextView mAreaNumTextView;

        public PlotViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mPlotNameTextView = (TextView) itemView.findViewById(R.id.text_view_plot_name);
            mAreaNumTextView = (TextView) itemView.findViewById(R.id.text_view_plot_num);
        }

        @Override
        public void onClick(View v) {
            AppCompatActivity appCompatActivity = (AppCompatActivity) v.getContext();

            if (appCompatActivity instanceof UserPlotListActivity) {
                UserPlotListActivity userPlotListActivity = (UserPlotListActivity) appCompatActivity;
                Intent intent = UserBookingEntryActivity.NewIntent(userPlotListActivity, mPlot.getUuid());
                userPlotListActivity.startActivity(intent);
            }

        }

        public void bindView(Plot plot) {
            mPlot = plot;
        }
    }
}
