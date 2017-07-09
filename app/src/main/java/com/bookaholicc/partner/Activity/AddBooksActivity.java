package com.bookaholicc.partner.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bookaholicc.partner.Fragments.CameraFrament;
import com.bookaholicc.partner.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nandhu on 9/7/17.
 *
 */

public class AddBooksActivity extends AppCompatActivity {

    @BindView(R.id.update_button)
    Button mUpdateButton;

    @BindView(R.id.camera_preview)
    FrameLayout mPreview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_acitivity);
        ButterKnife.bind(this);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.camera_preview,new CameraFrament())
                .commit();


    }


}
