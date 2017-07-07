package com.bookaholicc.partner.Fragments.Aboarding;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bookaholicc.partner.Model.User;
import com.bookaholicc.partner.Network.AppRequestQueue;
import com.bookaholicc.partner.R;
import com.bookaholicc.partner.utils.APIUtils;
import com.bookaholicc.partner.utils.BundleKey;
import com.bookaholicc.partner.utils.StringValidator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nandhu on 2/6/17.
 * Called after Name has been Obtaiend
 * Reoistration always follows Name, Email & Optional things
 */

public class EmailFragment extends Fragment implements View.OnClickListener, Response.Listener<JSONObject>, Response.ErrorListener {

    @BindView(R.id.email_tip)
    TextInputLayout mEmail;
    @BindView(R.id.email_pass_fip)
    TextInputLayout mPassword;

    @BindView(R.id.email_submit_button)
    Button mNextButton;


    @BindView(R.id.phone_number_tip) TextInputLayout mPhoneNumber;
    private Context mContext;
    View mView;
    private String firstName;
    private String lastName;
    private String TAG = "EMAIL";

    private RegistrationCallback mCallback;

    public RegistrationCallback getmCallback() {
        return mCallback;
    }


    private ProgressDialog mProgressDialog  = null;

    public void setmCallback(RegistrationCallback mCallback) {
        this.mCallback = mCallback;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = LayoutInflater.from(mContext).inflate(R.layout.email_fragment, container, false);
        ButterKnife.bind(this, mView);



        /*get the Arguments from */
        if (getArguments() != null){
            firstName = getArguments().getString(BundleKey.ARG_FIRST_NAME);
            lastName = getArguments().getString(BundleKey.ARG_LAST_NAME);
        }

        else{
            throw new NullPointerException("No User Name");
        }



        mNextButton.setOnClickListener(this);


        return mView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mContext != null) {
            mContext = null;
            mCallback = null;
            mProgressDialog = null;
        }
    }


    private void showProgressView() {
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setTitle("Registering , Please Wait..");
        mProgressDialog.setProgressDrawable(ContextCompat.getDrawable(mContext,R.drawable.progress_dialog));
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.show();
    }

    private void hideProgresDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
            mProgressDialog= null;
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
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        mCallback = (RegistrationCallback) context;
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
    public void onClick(View view) {
        if (view.getId() == R.id.email_submit_button){
            //Submit Button has Clicked

            createAccount();
        }
    }


    /** Validate the Fields, if Right then hit the Webservices */
    private void createAccount() {


        String emailAddress = mEmail.getEditText().getText().toString();
        String password = mPassword.getEditText().getText().toString();
        String  phoneNumber = mPhoneNumber.getEditText().getText().toString();
        if (StringValidator.checkeEMail(emailAddress)){
            //correct Email
            if (StringValidator.checkPassword(password)){

                //Hit the webservice

                newjoin(emailAddress,password,firstName,lastName,phoneNumber);


            }
            else{
                Snackbar.make(mView,"Enter Password Correctly",Snackbar.LENGTH_SHORT).show();
            }
        }
        else{
            //Wrong Email
            //give a Shake // TODO: 2/6/17 give a shake
            Snackbar.make(mView,"Enter Email Correcty",Snackbar.LENGTH_SHORT).show();

        }

    }

    private void newjoin(String emailAddress, String password, String firstName, String lastName, String phoneNumber) {
        showProgressView();
        JSONObject mUserObject = new JSONObject();
        try {



            showProgressView();

            mUserObject.put(APIUtils.FIRST_NAME,firstName);
            mUserObject.put(APIUtils.LAST_NAME,lastName);

            mUserObject.put(APIUtils.PARTNER_EMAIL,emailAddress);
            mUserObject.put(APIUtils.PASSWORD,password);
            mUserObject.put(APIUtils.PHONE_NUMBER,phoneNumber);
            JsonObjectRequest mRequest = new JsonObjectRequest(Request.Method.POST,APIUtils.REGISTER_PARTNER,mUserObject,this,this);

            AppRequestQueue mRequestQueue = AppRequestQueue.getInstance(mContext);
            mRequestQueue.addToRequestQue(mRequest);

        }
        catch (Exception e){
            Log.d(TAG, "newjoin: ");
            hideProgresDialog();
        }
    }




    @Override
    public void onResponse(JSONObject response) {
        Log.d(TAG, "onResponse: From Registering "+response.toString());

        parseJson(response);

    }

    private void parseJson(JSONObject response) {
        Log.d(TAG, "Got response "+response.toString());
        try {
            int status  = response.getInt("status");
            if (status==1){
                //Registered
                User u = new User();
                u.setFirstName(response.getString(APIUtils.FIRST_NAME));
                u.setLastName(response.getString(APIUtils.LAST_NAME));
                u.seteMailAddress(response.getString(APIUtils.PARTNER_EMAIL));
                u.setPhoneNumber(response.getString(APIUtils.PHONE_NUMBER));
                u.setPartnerid(response.getInt(APIUtils.PARTNER_ID));
                hideProgresDialog();


                mCallback.registered(u);


            }
        }
        catch (Exception e){
            hideProgresDialog();
            Log.d(TAG, "parseJson: Exception ");
            mCallback.notRegistered();
        }
    }






    @Override
    public void onErrorResponse(VolleyError error) {
        hideProgresDialog();
        showFailedAlert();
    }

    private void showFailedAlert() {

    }

    public interface RegistrationCallback{
        void registered(User u);
        void emailAlreadyExists();
        void phoneAlreadyExists();
        void notRegistered();
    }
}
