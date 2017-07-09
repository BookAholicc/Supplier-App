package com.bookaholicc.partner.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bookaholicc.partner.Network.AppRequestQueue;
import com.bookaholicc.partner.R;
import com.bookaholicc.partner.StorageHelpers.DataStore;
import com.bookaholicc.partner.utils.APIUtils;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nandhu on 5/7/17.
 *
 */

public class NotifcationsActivity extends AppCompatActivity implements Response.ErrorListener, Response.Listener<JSONObject> {

    @BindView(R.id.noti_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.noti_root)
    RelativeLayout mRoot;
    private Object notifications;
    private String TAG = "NOTIFICATION";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.noti_frag);
        ButterKnife.bind(this);


        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            ActionBar ab = getSupportActionBar();
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setDisplayShowHomeEnabled(true);
            ab.setTitle("Notifications");
        }

        //Get Notifiucatios
        getNotifications();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
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

    public void getNotifications() {
        DataStore mStore=  DataStore.getStorageInstance(this);
        int pid = mStore.getPartnerId();
        JSONObject mJsonObject   = new JSONObject();
        try {
            mJsonObject.put(APIUtils.PARTNER_ID,pid);
        }
        catch (Exception e){
            Log.d(TAG, "doLogin: ");
        }


        JsonObjectRequest mJsonObjectRequest = new JsonObjectRequest(Request.Method.POST,APIUtils.LOGIN_API,mJsonObject,this,this);
        AppRequestQueue mAppRequestQueue = AppRequestQueue.getInstance(this);
        mAppRequestQueue.addToRequestQue(mJsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this,"Please make sure you have an Activite internet Connection",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        Log.d(TAG, "onResponse: "+response.toString());

    }
}
