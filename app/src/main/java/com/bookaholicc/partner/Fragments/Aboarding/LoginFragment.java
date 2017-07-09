package com.bookaholicc.partner.Fragments.Aboarding;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bookaholicc.partner.Activity.FirstActivity;
import com.bookaholicc.partner.Model.User;
import com.bookaholicc.partner.Network.AppRequestQueue;
import com.bookaholicc.partner.R;
import com.bookaholicc.partner.utils.APIUtils;
import com.bookaholicc.partner.utils.StringValidator;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.ContentValues.TAG;

/**
 * Created by nandhu on 3/6/17.
 * THe Fragment Must SHow in SHared Manned
 * Uses Email & pass,
 * Branches out to Forgot Password, OTP SMS, Question page
 */

public class LoginFragment extends Fragment implements Response.ErrorListener, Response.Listener<JSONObject> {


    private static final String TAGG = "LOGIN";
    private View mView ;
    private Context mContext;



    @BindView(R.id.login_email_editbox)
    EditText mEmail;
    @BindView(R.id.login_pass_editbox) EditText mPassword;


    @BindView(R.id.login_submit_button) Button mSubmitButton;
    public LoginCallback mCallback;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        mView = LayoutInflater.from(mContext).inflate(R.layout.login_fragment,container,false);
        ButterKnife.bind(this,mView);



        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }


        });


        return mView;
    }

    private void login() {
        String email = mEmail.getText().toString();
        String pass = mPassword.getText().toString();
        if (StringValidator.checkPassword(pass) && StringValidator.checkeEMail(email)){
            doLogin(email,pass);


        }
    }

    private void doLogin(String email, String pass) {
        JSONObject mJsonObject   = new JSONObject();
        try {
                mJsonObject.put(APIUtils.PARTNER_EMAIL,email);
                mJsonObject.put(APIUtils.PASSWORD,pass);
        }
        catch (Exception e){
            Log.d(TAGG, "doLogin: ");
        }


        JsonObjectRequest mJsonObjectRequest = new JsonObjectRequest(Request.Method.POST,APIUtils.LOGIN_API,mJsonObject,this,this);
        AppRequestQueue mAppRequestQueue = AppRequestQueue.getInstance(mContext);
        mAppRequestQueue.addToRequestQue(mJsonObjectRequest);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mContext != null){
            mContext = null;
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
        mCallback = (LoginCallback) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mCallback != null) {

            mCallback = null;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void setLoginCallback(LoginCallback loginCallback) {
        this.mCallback = loginCallback;
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        Toast.makeText(mContext,"Please make sure you have an Activite internet Connection",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        try {


            Log.d(TAG, "onResponse: "+response.toString());
            if (response.getInt("status") == 1) {
                if (mCallback != null){
                    User u =    new User(response.getInt(APIUtils.PARTNER_ID),
                                          response.getString(APIUtils.FIRST_NAME),
                                        response.getString(APIUtils.LAST_NAME),
                                        response.getString(APIUtils.PARTNER_EMAIL),
                                        response.getString(APIUtils.PHONE_NUMBER));
                    mCallback.loggedIn(u);
                }

            }
            else{
                mCallback.inCorrectLoginDetails();
            }
        }
        catch (Exception e){
            Log.d(TAG, "Exception in On Response");
            mCallback.notLoggedIn();
        }
    }

    public interface LoginCallback{
        void loggedIn(User u);
        void notLoggedIn();
        void inCorrectLoginDetails();

    }
}
