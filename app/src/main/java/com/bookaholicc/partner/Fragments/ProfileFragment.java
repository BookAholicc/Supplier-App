package com.bookaholicc.partner.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bookaholicc.partner.Activity.FirstActivity;
import com.bookaholicc.partner.CustomUI.ForegroundImageView;
import com.bookaholicc.partner.CustomUI.HelviticaLight;
import com.bookaholicc.partner.R;
import com.bookaholicc.partner.StorageHelpers.DataStore;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nandhu on 1/7/17.
 *
 */

public class ProfileFragment extends android.support.v4.app.Fragment {


    private Context mContext = null;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mContext != null){
            mContext = null;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      View mView   = LayoutInflater.from(mContext).inflate(R.layout.profile_fragment,container,false);
      ButterKnife.bind(this,mView);


        return mView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (mContext == null){
            mContext = context;
        }
    }
}
