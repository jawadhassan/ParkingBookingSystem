package com.example.hamid_pc.parkingbookingsystem;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class AdminPlotListFragment extends Fragment {


    public static final String TAG = "AdminPlotListActivity";
    private static final int REQUEST_ADD_PLOT = 3;
    public static String DIALOG_OPERATION = "com.example.hamid_pc.parkingbookingsystem.dialog_operation";
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private FirebaseRecyclerAdapter<Plot, PlotViewHolder> mAdapter;
    private RecyclerView mRecyclerView;
    private FloatingActionButton mFAB;
    private DividerItemDecoration mDividerItemDecoration;

    public static AdminPlotListFragment NewInstance() {
        AdminPlotListFragment adminPlotListFragment = new AdminPlotListFragment();
        return adminPlotListFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_admin_plot_list, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.plot_recycler_view);

        mFAB = (FloatingActionButton) view.findViewById(R.id.button_add_parking);
        UpdateUI();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDividerItemDecoration = new DividerItemDecoration(getContext(),
                new LinearLayoutManager(getContext()).getOrientation());
        mRecyclerView.addItemDecoration(mDividerItemDecoration);
        mFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AddPlotActivity.NewIntent(getActivity());
                startActivityForResult(intent, REQUEST_ADD_PLOT);
            }
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("plots");


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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_ADD_PLOT) {
            Toast.makeText(getActivity(), getString(R.string.toast_plot_add_successfull), Toast.LENGTH_LONG).show();
        }

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
            if (appCompatActivity instanceof AdminPlotListActivity) {
                AdminPlotListActivity adminPlotListActivity = (AdminPlotListActivity) appCompatActivity;
                FragmentManager fragmentManager = adminPlotListActivity.getSupportFragmentManager();
                PlotDialogOperation plotDialogOperation = PlotDialogOperation.NewInstance(mPlot.getUuid());
                plotDialogOperation.show(fragmentManager, DIALOG_OPERATION);


            }

        }

        public void bindView(Plot plot) {
            mPlot = plot;
        }
    }
}

