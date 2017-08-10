package com.example.hamid_pc.parkingbookingsystem;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class AdminFeedbackListFragment extends Fragment {


    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private RecyclerView mRecyclerView;
    private DividerItemDecoration mDividerItemDecoration;
    private FirebaseRecyclerAdapter<Feedback, FeedbackViewHolder> mAdapter;

    public static AdminFeedbackListFragment NewInstance() {
        AdminFeedbackListFragment adminFeedbackListFragment = new AdminFeedbackListFragment();
        return adminFeedbackListFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("feedbacks");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_admin_feedback_list, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_feedback);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        UpdateUI();
        mRecyclerView.setAdapter(mAdapter);
        mDividerItemDecoration = new DividerItemDecoration(getContext(),
                new LinearLayoutManager(getContext()).getOrientation());

        return view;
    }

    public void UpdateUI() {
        mAdapter = new FirebaseRecyclerAdapter<Feedback, FeedbackViewHolder>(
                Feedback.class,
                R.layout.list_item_feedback,
                FeedbackViewHolder.class,
                mDatabaseReference

        ) {
            @Override
            protected void populateViewHolder(FeedbackViewHolder viewHolder, Feedback model, int position) {

                viewHolder.mTextView.setText(model.getFeedBack());
                Feedback feedback = getItem(position);
                viewHolder.BindView(feedback);
            }
        };


    }

    public static class FeedbackViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Feedback mFeedback;
        TextView mTextView;


        public FeedbackViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.list_item_feedback);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }

        public void BindView(Feedback feedback) {
            mFeedback = feedback;
        }

    }
}
