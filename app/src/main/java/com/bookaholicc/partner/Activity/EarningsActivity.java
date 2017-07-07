package com.bookaholicc.partner.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bookaholicc.partner.Adapter.EarningAdapter;
import com.bookaholicc.partner.Model.Earning;
import com.bookaholicc.partner.Network.AppRequestQueue;
import com.bookaholicc.partner.R;
import com.bookaholicc.partner.StorageHelpers.DataStore;
import com.bookaholicc.partner.utils.APIUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.ContentValues.TAG;

/**
 * Created by nandhu on 5/7/17.
 *
 */

public class EarningsActivity extends AppCompatActivity implements Response.ErrorListener, Response.Listener<JSONObject> {

    @BindView(R.id.earning_list)
    RecyclerView mList;

    ProgressDialog mDialog;
    private String TAGI = "EARNING ACTIVITY";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earning_breakdown);
        ButterKnife.bind(this);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Transactions");
        }


        getEarnings();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }




    private void showProgressView() {
        mDialog = new ProgressDialog(this);
        mDialog.setTitle("Getting latest Information");
        mDialog.setIndeterminate(true);
        mDialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void hideProgresDialog() {
        if (mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
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

    private void getEarnings() {
        showProgressView();
        DataStore mStore  = DataStore.getStorageInstance(this);
        int partnerId = mStore.getPartnerId();

        try {

            JSONObject mJsonObject = new JSONObject();
            mJsonObject.put(APIUtils.PARTNER_ID, 4); //// TODO: 2/7/17 cange
            JsonObjectRequest mRequest  = new JsonObjectRequest(Request.Method.POST,APIUtils.PARTNER_EARNINGS_API,mJsonObject,this,this);
            AppRequestQueue mRequestQueue = AppRequestQueue.getInstance(this);
            mRequestQueue.addToRequestQue(mRequest);

        }
        catch (Exception e){
            Log.d(TAGI, "getEarnings: ");
        }

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        hideProgresDialog();

    }

    @Override
    public void onResponse(JSONObject response) {
        Log.d(TAG, "onResponse: From Earning Adapter "+response.toString());

        List<Earning> mEarningsList =  parseData(response);
        //Got the List

        hideProgresDialog();
        EarningAdapter mAdapter = new EarningAdapter(mEarningsList,this);
        mList.setLayoutManager(new LinearLayoutManager(this));
        mList.setAdapter(mAdapter);
        mList.setHasFixedSize(true);
    }

    @Nullable
    private List<Earning> parseData(JSONObject response) {

        try {


            List<Earning> mList = new ArrayList<>();
            JSONArray mArray = response.getJSONArray("transactions");
            for (int i=0; i<mArray.length();i++){
                JSONObject mEarningObj  = mArray.getJSONObject(i);
                mList.add(new Earning(mEarningObj.getString("productName"),
                        mEarningObj.getString("amount"),
                        mEarningObj.getString("imageURL"),
                        mEarningObj.getString("timestamp"),
                        mEarningObj.getString(APIUtils.WINDOWID)));
            }

            return mList;
        }
        catch (Exception e){
            Log.d(TAG, "parseData:Exception  "+e.getLocalizedMessage());
            return null;
        }
    }
}
