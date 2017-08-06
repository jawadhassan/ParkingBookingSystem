package com.example.hamid_pc.parkingbookingsystem;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


public class AdminUserListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private FirebaseRecyclerAdapter<User, UserViewHolder> mAdapter;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private Query mQuery;

    public static AdminUserListFragment NewInstance() {
        AdminUserListFragment adminUserListFragment = new AdminUserListFragment();
        return adminUserListFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("users");
        mQuery = mDatabaseReference.orderByChild("mRole").equalTo("user");


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_user_list, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_user_list);
        UpdateUI();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);


        return view;
    }


    public void UpdateUI() {
        mAdapter = new FirebaseRecyclerAdapter<User, UserViewHolder>(
                User.class,
                R.layout.list_user,
                UserViewHolder.class,
                mQuery
        ) {
            @Override
            protected void populateViewHolder(UserViewHolder viewHolder, User model, int position) {
                viewHolder.mUserNameTextView.setText(model.getmName());
                viewHolder.mUserEmailTextView.setText(model.getmEmail());
                User user = getItem(position);
                viewHolder.bindView(user);
            }
        };

    }

    public static class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private User mUser;
        private TextView mUserNameTextView;
        private TextView mUserEmailTextView;

        public UserViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mUserNameTextView = (TextView) itemView.findViewById(R.id.text_view_user_name);
            mUserEmailTextView = (TextView) itemView.findViewById(R.id.text_view_user_email);

        }

        @Override
        public void onClick(View v) {
            v.getContext().startActivity(AdminUserDetailActivity.NewIntent(v.getContext(), mUser.getmUuid()));

        }

        public void bindView(User user) {
            mUser = user;
        }
    }

}
