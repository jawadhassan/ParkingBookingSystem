package com.example.hamid_pc.parkingbookingsystem;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class AuthenticationActivity extends AppCompatActivity {
    public static final int RC_SIGN_IN = 1;
    private static final String TAG = "AuthenticationActivity";
    private final String ADMIN = "administrator";
    private final String USER = "user";
    private boolean signed_in;
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
                final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();


                if (firebaseUser != null) {

                    query = mUserReference.orderByChild("mUuid").equalTo(firebaseUser.getUid());

                    query.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            if (dataSnapshot != null) {
                                User userData = dataSnapshot.getValue(User.class);
                                if (userData.getmRole().equalsIgnoreCase(USER)) {

                                    Intent intent = UserActivity.NewIntent(AuthenticationActivity.this);
                                    startActivity(intent);


                                } else if (userData.getmRole().equalsIgnoreCase(ADMIN)) {

                                /*Intent intent = AdminActivity.NewIntent(AuthenticationActivity.this);
                                startActivity(intent);
*/
                                }
                            } else {
                                String mEmail;
                                String mUuid;
                                String mUserName;
                                String mRole = USER;
                                mEmail = firebaseUser.getEmail();
                                mUserName = firebaseUser.getDisplayName();
                                mUuid = firebaseUser.getUid();
                                User user = new User(mUuid, mUserName, mEmail, mRole);
                                mUserReference.push().setValue(user);
                                Intent intent = UserActivity.NewIntent(AuthenticationActivity.this);
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
                String mEmail;
                String mUuid;
                String mUserName;
                String mRole = USER;

                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                if (firebaseUser != null) {
                    mEmail = firebaseUser.getEmail();
                    mUserName = firebaseUser.getDisplayName();
                    mUuid = firebaseUser.getUid();
                    User user = new User(mUuid, mUserName, mEmail, mRole);
                    mUserReference.push().setValue(user);
                    Intent intent = UserActivity.NewIntent(AuthenticationActivity.this);
                    startActivity(intent);

                }


            }
        }
    }
}
