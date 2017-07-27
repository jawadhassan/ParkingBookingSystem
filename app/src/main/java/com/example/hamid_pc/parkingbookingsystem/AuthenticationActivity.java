package com.example.hamid_pc.parkingbookingsystem;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class AuthenticationActivity extends AppCompatActivity {
    public static final int RC_SIGN_IN = 1;
    private static final String TAG = "AuthenticationActivity";
    private final String ADMIN = "administrator";
    private final String USER = "user";
    private FirebaseAuth mFireAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private DatabaseReference mUserReference;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Query query;

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, AuthenticationActivity.class);
        return intent;

    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mFireAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mUserReference = mFirebaseDatabase.getReference().child("users");

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();


                if (firebaseUser != null) {
                    Log.d(TAG, "In SignedinFirebaseMethod");


                    query = mUserReference.orderByChild("mUuid").equalTo(firebaseUser.getUid());


                    query.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            User userData = dataSnapshot.getValue(User.class);
                            if (userData.getmRole().equalsIgnoreCase(USER)) {
                                Log.d(TAG, "In SigneinOUserMethod");

                                Intent intent = UserActivity.NewIntent(AuthenticationActivity.this);
                                startActivity(intent);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                finish();


                            } else if (userData.getmRole().equalsIgnoreCase(ADMIN)) {
                                Log.d(TAG, "In SignedinAdminMethod");

                                Log.d(TAG, "In Admin Fragment");
                                Intent intent = AdminActivity.NewIntent(AuthenticationActivity.this);
                                startActivity(intent);
                            }
                        }




                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                } else {
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setProviders(AuthUI.GOOGLE_PROVIDER,
                                            AuthUI.EMAIL_PROVIDER)
                                    .build(),
                            RC_SIGN_IN);
                }
            }
        };
    }


    @Override
    public void onResume() {
        super.onResume();
        mFireAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mAuthListener != null) {
            mFireAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {

                Log.d(TAG, "In SignedinMethod");

                FirebaseUser firebaseUser1 = FirebaseAuth.getInstance().getCurrentUser();

                final String mUuid = firebaseUser1.getUid();
                final String mUserName = firebaseUser1.getDisplayName();
                final String mEmail = firebaseUser1.getEmail();
                final String mRole = "user";

                mUserReference.orderByChild("mUuid").equalTo(firebaseUser1.getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {


                        if (!dataSnapshot.exists()) {
                            Log.d(TAG, "In User Creation Method");

                            User user = new User(mUuid, mUserName, mEmail, mRole);
                            mUserReference.push().setValue(user);
                            Intent intent = UserActivity.NewIntent(AuthenticationActivity.this);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


                /*FirebaseUser firebaseUser1 = FirebaseAuth.getInstance().getCurrentUser();
                final String mRole = USER;


                final String mEmail = firebaseUser1.getEmail();
                final String mUserName = firebaseUser1.getDisplayName();
                final String mUuid = firebaseUser1.getUid();



                mUserReference.orderByChild("mUuid").equalTo(firebaseUser1.getUid()).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        if(!dataSnapshot.exists()){


                            User user = new User(mUuid, mUserName, mEmail, mRole);
                            mUserReference.push().setValue(user);
                            Intent intent = UserActivity.NewIntent(AuthenticationActivity.this);
                            startActivity(intent);


                        }else {

                            User userData = dataSnapshot.getValue(User.class);

                            if (userData.getmRole().equalsIgnoreCase("user")) {

                                Intent intent = UserActivity.NewIntent(AuthenticationActivity.this);
                                startActivity(intent);


                            } else if (userData.getmRole().equalsIgnoreCase("administrator")) {
                                Log.d(TAG,"In Admin Fragment");

                                *//*Intent intent = AdminActivity.NewIntent(AuthenticationActivity.this);
                                startActivity(intent);
*//*
                            }}

                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
*/

            } else if (resultCode == RESULT_CANCELED) {
                Log.d(TAG, "Error");
            }

        }
    }
}
