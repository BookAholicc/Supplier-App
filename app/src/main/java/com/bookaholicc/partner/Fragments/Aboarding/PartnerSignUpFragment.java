package com.bookaholicc.partner.Fragments.Aboarding;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bookaholicc.partner.R;
import com.bookaholicc.partner.utils.ScreenUtil;
import com.squareup.picasso.Picasso;

import java.util.IllegalFormatException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nandhu on 1/7/17.
 *
 */

public class PartnerSignUpFragment extends Fragment implements View.OnClickListener {


    private View mView;
    private Context mContext;

    @BindView(R.id.ua_login_button)
    Button mLoginButton;
    @BindView(R.id.ua_signup_button) Button mSignUpButtton;


    @BindView(R.id.ua_image)
    ImageView mLogoImage;

    @BindView(R.id.ua_root)
    RelativeLayout mRoot;
    private RootCallback mCallback;
    private String TAG = "PARTNERSIGNUP";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = LayoutInflater.from(mContext).inflate(R.layout.ua_frag_root,container,false);
        ButterKnife.bind(this,mView);

        Picasso.with(mContext)
                .load(R.mipmap.head_logo)
                .resize(ScreenUtil.getScreenWidth(mContext),ScreenUtil.getScreenHeight(mContext))
                .centerCrop()
                .into(mLogoImage);

        startAnimation();
        return mView;
    }


    private void startAnimation() {
        int height = ScreenUtil.getScreenHeight(mContext);
        mLogoImage.setTranslationY(-height);
        mLoginButton.setTranslationY(height);
        mSignUpButtton.setTranslationY(height);
        mLogoImage.animate().translationY(0)
                .setDuration(1600)
                .setInterpolator(new DecelerateInterpolator(1.5f))
                .start();
        mLoginButton.animate().translationY(0)
                .setDuration(1600)
                .setInterpolator(new DecelerateInterpolator(1.5f))
                .start();
        mSignUpButtton.animate().translationY(0)
                .setDuration(1600)
                .setInterpolator(new DecelerateInterpolator(1.5f))
                .start();

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mContext != null){
            mContext = null;
            mCallback = null;
        }
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
        if (mLoginButton != null ){
            mLoginButton.setOnClickListener(this);
        }
        if (mSignUpButtton != null){
            mSignUpButtton.setOnClickListener(this);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mContext = context;
        mCallback = (RootCallback) context;

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }


    private void showSignUpPage() {




        int width = ScreenUtil.getScreenWidth(mContext);
        int height = ScreenUtil.getScreenHeight(mContext);

        mSignUpButtton.animate().translationY(100).setDuration(800)
                .alpha(0f)
                .setInterpolator(new DecelerateInterpolator(1.5f))
                .start();
        mLoginButton.animate().translationX(-width).setDuration(1200)
                .setInterpolator(new DecelerateInterpolator(1.5f))
                .start();
        mLogoImage.animate().translationY(-height).setDuration(1200)
                .alpha(0f)
                .setInterpolator(new DecelerateInterpolator(1f))
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                            try {
                                mCallback.showSigUpFragment();
                            }
                            catch (Exception e){
                                throw  new NullPointerException("Cannot Call callback");
                            }
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                })
                .start();



    }

    private void showLoginPage() {



        int width = ScreenUtil.getScreenWidth(mContext);
        int height = ScreenUtil.getScreenHeight(mContext);

        mLoginButton.animate().translationY(100).setDuration(800)
                .alpha(0f)
                .setInterpolator(new DecelerateInterpolator(1.5f))
                .start();
        mSignUpButtton.animate().translationX(width).setDuration(1200)
                .setInterpolator(new DecelerateInterpolator(1.5f))
                .start();
        mLogoImage.animate().translationY(-height).setDuration(1200)
                .alpha(0f)
                .setInterpolator(new DecelerateInterpolator(1f))
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        try {
                            mCallback.showLoginFragment();
                        }
                        catch (Exception e){
                            throw  new NullPointerException("Cannot Call callback");
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                })
                .start();


    }

    @Override
    public void onClick(View view) {
        if (view.getId()  == R.id.ua_login_button){
            showLoginPage();
        }
        if (view.getId() == R.id.ua_signup_button){
            //Sign Up Button Clicked
           showSignUpPage();
        }
        else{
            //

        }
    }

    public void setRootCallback(RootCallback rootCallback) {
        this.mCallback = rootCallback;
    }

    public interface RootCallback{
        void showLoginFragment();
        void showSigUpFragment();
    }
}


