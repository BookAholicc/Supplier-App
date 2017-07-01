package com.bookaholicc.partner.Fragments;

import android.app.ProgressDialog;
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
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bookaholicc.partner.Adapter.BooksAdapter;
import com.bookaholicc.partner.Model.MiniProduct;
import com.bookaholicc.partner.Model.MiniProductDescription;
import com.bookaholicc.partner.Model.Product;
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
 * Created by nandhu on 1/7/17.
 *
 *
 */

public class BookFragement extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {

    @BindView(R.id.book_text)
    TextView mBooksText;
    @BindView(R.id.book_list)
    RecyclerView mList;
    private Context mContext;
    ProgressDialog mDialog;

    private String TATAGIG = "BOOKSLIST";

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
        View v = LayoutInflater.from(mContext).inflate(R.layout.book_fragment, container, false);
        ButterKnife.bind(this, v);

        showProgressView();
        getbooksList();


        return v;
    }

    private void showProgressView() {
        mDialog = new ProgressDialog(mContext);
        mDialog.setTitle("Getting Your Books..");
        mDialog.setIndeterminate(true);
        mDialog.show();
    }

    private void hideProgresDialog() {
        if (mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
    }


    private void getbooksList() {
        DataStore mStore = DataStore.getStorageInstance(mContext);
        int partnerId = mStore.getPartnerId();
        JSONObject mJsonObject  = new JSONObject();
        try {
            mJsonObject.put(APIUtils.PARTNER_ID,4);

        }
        catch (Exception e){
            Log.d(TATAGIG, "getbooksList: Get b");
        }
        JsonObjectRequest mJsonObjectRequest  = new JsonObjectRequest(Request.Method.POST,APIUtils.BOOK_LIST_API,mJsonObject,this,this);
        AppRequestQueue mAppRequestQueue =  AppRequestQueue.getInstance(mContext);
        mAppRequestQueue.addToRequestQue(mJsonObjectRequest);
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (mContext == null) {
            mContext = context;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mContext != null) {
            mContext = null;
        }
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
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onResponse(JSONObject response) {
        List<MiniProduct> mlist = getBooks(response);
        BooksAdapter mAdapter  = new BooksAdapter(mContext,mlist);
        mList.setAdapter(mAdapter);

        mList.setLayoutManager(new LinearLayoutManager(mContext));
        mList.setHasFixedSize(true);
        hideProgresDialog();
    }




    private List<MiniProduct> getBooks(JSONObject response) {


        Log.d(TAG, "getBooks: Response "+response.toString());
         List<MiniProduct> mlist = new ArrayList<>();

        try {


            JSONArray mObject = response.getJSONArray("books");
            for (int i = 0; i < mObject.length(); i++) {
                JSONObject singObj = mObject.getJSONObject(i);
                mlist.add(new MiniProduct(singObj.getInt(APIUtils.PID)
                        , singObj.getString(APIUtils.PRODUCT_NAME),
                        singObj.getString(APIUtils.IMAGE_URL),
                        singObj.getInt(APIUtils.AMOUNT)));
            }
            return mlist;
        }
        catch (Exception e){
            Log.d(TAG, "Exception in getBooks: "+e.getLocalizedMessage());
            return null;
        }


    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }
}
