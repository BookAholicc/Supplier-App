package com.bookaholicc.partner.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bookaholicc.partner.Adapter.EarningAdapter;
import com.bookaholicc.partner.Model.Earning;
import com.bookaholicc.partner.R;
import com.bookaholicc.partner.StorageHelpers.DataStore;
import com.bookaholicc.partner.utils.APIUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.ContentValues.TAG;

/**
 * Created by nandhu on 23/6/17.
 *
 * The Page Which Shows Earning of Partner in details ,
 *
 * Always get Timestamp from Requests
 *
 */

public class EarningFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {

    private Context mContext;
    private String TAGI = "EARNINGS";


    @BindView(R.id.earning_list)
    RecyclerView mList;

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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.earning_breakdown,container,false);
        ButterKnife.bind(this,v);


        getEarnings();



        return v;

    }

    private void getEarnings() {
        DataStore mStore  = DataStore.getStorageInstance(mContext);
        String partnerId = mStore.getPartnerId();

        try {

            JSONObject mJsonObject = new JSONObject();
            mJsonObject.put(APIUtils.PARTNER_ID, partnerId);
            JsonObjectRequest mRequest  = new JsonObjectRequest(Request.Method.POST,APIUtils.PARTNER_EARNINGS_API,mJsonObject,this,this);
        }
        catch (Exception e){
            Log.d(TAGI, "getEarnings: ");
        }

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
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (mContext == null){
            mContext = context;
        }
    }

    @Override
    public void onResponse(JSONObject response) {
        List<Earning> mEarningsList =  parseData(response);
        //Got the List

        EarningAdapter mAdapter = new EarningAdapter(mEarningsList,this,mContext);
        mList.setLayoutManager(new LinearLayoutManager(mContext));
        mList.setAdapter(mAdapter);
        mList.setHasFixedSize(true);

    }

    private List<Earning> parseData(JSONObject response) {
        List<Earning> mList = new ArrayList<>();
        mList.add(new Earning("4","2 Weeks","45","sdsdf","asdfdsf"));
        return mList;
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }
}
