package com.bookaholicc.partner.Activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.bookaholicc.partner.CustomUI.ForegroundImageView;
import com.bookaholicc.partner.CustomUI.HelviticaLight;
import com.bookaholicc.partner.CustomUI.WhitenyBooksFont;
import com.bookaholicc.partner.R;
import com.bookaholicc.partner.StorageHelpers.DataStore;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.URI;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by nandhu on 5/7/17.
 *
 */

public class ProfileActivity extends AppCompatActivity{


    private static final int PICK_IMAGE = 1001;
    @BindView(R.id.chn_profile_image)
    CircleImageView mImageView;

    @BindView(R.id.partner_id)
    WhitenyBooksFont mPartnerId;

    @BindView(R.id.p_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.profile_name_value)
    WhitenyBooksFont mName;

    @BindView(R.id.profile_dob) WhitenyBooksFont mDob;

    @BindView(R.id.profile_email_value) WhitenyBooksFont mEmail;

    @BindView(R.id.profile_phone_value) WhitenyBooksFont mPhoneNumber;
    private String TAG = "PROFILE";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_fragment);
        ButterKnife.bind(this);


        DataStore mStore = DataStore.getStorageInstance(this);
        if (mStore.isFirstTime()) {
            // Not Logged show Aboarding Activity
            Intent i  = new Intent(this,FirstActivity.class);
            startActivity(i);
            finish();
        }
        else{

            mPartnerId.setText(""+mStore.getPartnerId());
            mPhoneNumber.setText(""+mStore.getPhoneNumber());
            mName.setText(mStore.getFirstName());
            mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pickImage();
                }


            });
        }

        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null){
            ActionBar mbar = getSupportActionBar();
            mbar.setDisplayShowHomeEnabled(true);
            mbar.setDisplayHomeAsUpEnabled(true);
            mbar.setTitle("Profile");
        }



    }





    private void pickImage() {
        // Pick Image
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

        startActivityForResult(chooserIntent, PICK_IMAGE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {

            if (data == null) {
                //Display an error
                return;
            }
            try{




                Picasso.with(this)
                        .load(data.getData())
                        .into(mImageView);
                Log.d(TAG, "onActivityResult: Git Bitmap String");
            }
            catch (Exception e){
                Log.d(TAG, "onActivityResult: Exception "+e.getLocalizedMessage());
            }
            //Now you can do whatever you want with your inpustream, save it as file, upload to a server, decode a bitmap...
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
