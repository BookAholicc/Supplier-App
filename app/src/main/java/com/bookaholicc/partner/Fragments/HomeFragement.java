package com.bookaholicc.partner.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bookaholicc.partner.Network.AppRequestQueue;
import com.bookaholicc.partner.R;
import com.bookaholicc.partner.StorageHelpers.DataStore;
import com.bookaholicc.partner.utils.APIUtils;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nandhu on 23/6/17.
 * The Fragment which shows what is his Earning and May be Which is New Books that he may want to  Buy andRead
 */

public class HomeFragement extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {

    @BindView(R.id.home_amount)
    TextView mAmount;
    @BindView(R.id.textView3)
    TextView textView3;

    @BindView(R.id.home_books_count)
    TextView mProductsCount;

    @BindView(R.id.home_total_order_count)
    TextView mOrderCount;
    @BindView(R.id.home_month)
    TextView mMonth;
    @BindView(R.id.home_bill_breakdown)
    Button mEarningsButton;
    private Context mContext;
    private String TAG = "HOME PARTNER";
    ProgressDialog mDialog;

    public homeFragmentCallbacks mCallbacks;

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

        View v = LayoutInflater.from(mContext).inflate(R.layout.home_frag, container, false);
        ButterKnife.bind(this, v);


        showProgressView();
        getData();

        mEarningsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if (mCallbacks != null){
                        mCallbacks.showEarningsPage();
                    }
            }
        });


        return v;
    }

    private void showProgressView() {
        mDialog = new ProgressDialog(mContext);
         mDialog.setTitle("Getting latest Information");
        mDialog.setIndeterminate(true);
        mDialog.show();
    }

    private void getData() {
        JSONObject mObject = new JSONObject();
        DataStore mStore = DataStore.getStorageInstance(mContext);
        int partnerId = mStore.getPartnerId();
        try {
            mObject.put(APIUtils.PARTNER_ID,4);
        }
        catch (Exception e){
            Log.d(TAG, "getData: Exception in putting JSON File");
        }
        JsonObjectRequest mJsonObjectRequest  = new JsonObjectRequest(Request.Method.POST,APIUtils.PARTNER_HOME_API,mObject,this,this);
        AppRequestQueue mAppRequestQueue = AppRequestQueue.getInstance(mContext);
        mAppRequestQueue.addToRequestQue(mJsonObjectRequest);

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mContext != null) {
            mContext = null;
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (mContext == null) {
            mContext = context;
            mCallbacks = (homeFragmentCallbacks) context;
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
    public void onResponse(JSONObject response) {
        try {

            hideProgresDialog();
            String amount = response.getString(APIUtils.AMOUNT_EARNED);
            String bookCount = response.getString(APIUtils.BOOK_COUNT);
            String orderCount = response.getString(APIUtils.ORDERS_COUNT);
            mAmount.setText(amount);
            mProductsCount.setText(bookCount);
            mOrderCount.setText(orderCount);
        }
        catch (Exception e){
            Log.d(TAG, "onResponse: "+e.getLocalizedMessage());
        }
    }

    private void hideProgresDialog() {
        if (mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d(TAG, "onErrorResponse: Error "+error.getLocalizedMessage());
        hideProgresDialog();
        Toast.makeText(mContext,"Sorry for the delay , we are currently down for while",Toast.LENGTH_SHORT).show();

    }

    public interface homeFragmentCallbacks{
        void showEarningsPage();
        void showBooksPage();
        void showTransactions();
    }
}
