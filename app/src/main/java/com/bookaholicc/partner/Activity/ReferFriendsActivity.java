package com.bookaholicc.partner.Activity;

import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bookaholicc.partner.R;
import com.bookaholicc.partner.utils.ScreenUtil;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nandhu on 8/7/17.
 *
 */

public class ReferFriendsActivity extends AppCompatActivity {



    @BindView(R.id.refer_background)
    ImageView mImage;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.refer_friends_activity);
        ButterKnife.bind(this);
        Picasso.with(this)
                .load(R.mipmap.inviite_bg)
                .resize(ScreenUtil.getScreenWidth(this),ScreenUtil.getScreenHeight(this))
                .centerCrop()
                .into(mImage);
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
