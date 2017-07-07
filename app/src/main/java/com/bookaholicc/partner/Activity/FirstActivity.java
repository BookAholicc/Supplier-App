package com.bookaholicc.partner.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.bookaholicc.partner.Fragments.Aboarding.EmailFragment;
import com.bookaholicc.partner.Fragments.Aboarding.LoginFragment;
import com.bookaholicc.partner.Fragments.Aboarding.PartnerSignUpFragment;
import com.bookaholicc.partner.Fragments.Aboarding.SignUpNameFragment;
import com.bookaholicc.partner.MainActivity;
import com.bookaholicc.partner.Model.User;
import com.bookaholicc.partner.R;
import com.bookaholicc.partner.StorageHelpers.DataStore;
import com.bookaholicc.partner.utils.BundleKey;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nandhu on 1/7/17.
 * The Fist Activity which shows up During Registration
 */

public class FirstActivity extends AppCompatActivity implements PartnerSignUpFragment.RootCallback
        ,LoginFragment.LoginCallback ,
        SignUpNameFragment.SignUpCallback,EmailFragment.RegistrationCallback{


    private static final String TAG = "FIRST ACT";
    @BindView(R.id.root_fa)
    FrameLayout mRoot;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_acitivity);
        ButterKnife.bind(this);
        Log.d(TAG, "onCreate: Inside First Activity");

        showFirstActivity();

    }

    private void showFirstActivity() {
        Log.d(TAG, "showFirstActivity: Perfomring Transaction");
        this.getSupportFragmentManager().beginTransaction()
                .replace(R.id.root_fa,new PartnerSignUpFragment())
                .commit();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }





    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
    }



    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }




    @Override
    public void showLoginFragment() {


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.root_fa,new LoginFragment())
                .commit();
    }

    @Override
    public void showSigUpFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.root_fa,new SignUpNameFragment())
                .commit();
    }

    @Override
    public void loggedIn(User u) {
        if (u != null){
            Log.d(TAG, "loggedIn: saving user Details");
            DataStore mStore = DataStore.getStorageInstance(this);
            mStore.setFirstName(u.getFirstName());
            mStore.setLastName(u.getLastName());
            mStore.setEmailId(u.geteMailAddress());
            mStore.setPartnerId(u.getPartnerId());
        }

    }

    @Override
    public void notLoggedIn() {
        Toast.makeText(this,"Oops, Our systems seems to be down  for a while , Please try again later",Toast.LENGTH_LONG).show();

    }

    @Override
    public void inCorrectLoginDetails() {
        Toast.makeText(this,"Wrong  Details",Toast.LENGTH_LONG).show();
    }

    @Override
    public void showEmailFragment(String firstName, String lastName) {

        Bundle b = new Bundle();
        b.putString(BundleKey.ARG_FIRST_NAME,firstName);
        b.putString(BundleKey.ARG_LAST_NAME,lastName);

        EmailFragment mEmailFragment  = new EmailFragment();
        mEmailFragment.setArguments(b);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.root_fa,mEmailFragment)
                .commit();
    }

    @Override
    public void noSignUp() {
        Toast.makeText(this,"Something Wrong Occurred",Toast.LENGTH_LONG).show();
    }

    @Override
    public void registered(User u) {
        if (u != null){
            Log.d(TAG, "loggedIn: saving user Details");
            DataStore mStore = DataStore.getStorageInstance(this);
            mStore.setFirstName(u.getFirstName());
            mStore.setLastName(u.getLastName());
            mStore.setEmailId(u.geteMailAddress());
            mStore.setPhoneNumberTag(u.getPhoneNumber());
            mStore.setPartnerId(u.getPartnerId());
            mStore.setIsFirstTime(false);
        }

        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
        finish();


    }

    @Override
    public void emailAlreadyExists() {
        Toast.makeText(this,"Email already Exists",Toast.LENGTH_LONG).show();
    }

    @Override
    public void phoneAlreadyExists() {
        Toast.makeText(this,"Phone already Exists",Toast.LENGTH_LONG).show();
    }

    @Override
    public void notRegistered() {
        Toast.makeText(this,"Some Error Occured Please Try Again",Toast.LENGTH_LONG).show();
    }
}
