package com.bookaholicc.partner.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bookaholicc.partner.Adapter.BooksAdapter;
import com.bookaholicc.partner.Model.MiniProduct;
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
 *
 * Created by nandhu on 5/7/17.
 */

public class BooksActivity extends AppCompatActivity implements Response.ErrorListener, Response.Listener<JSONObject> {


    @BindView(R.id.book_text)
    TextView mBooksText;
    @BindView(R.id.book_list)
    RecyclerView mList;
    private Context mContext;
    ProgressDialog mDialog;

    private String TATAGIG = "BOOKSLIST";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_fragment);
        ButterKnife.bind(this);

        showProgressView();
        getbooksList();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    private void showProgressView() {
        mDialog = new ProgressDialog(this);
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
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {
        hideProgresDialog();
        List<MiniProduct> mlist = getBooks(response);
        BooksAdapter mAdapter  = new BooksAdapter(getApplicationContext(),mlist);
        mList.setAdapter(mAdapter);

        mList.setLayoutManager(new LinearLayoutManager(mContext));


    }

    private List<MiniProduct> getBooks(JSONObject response) {


        Log.d(TAG, "getBooks: Response "+response.toString());
        List<MiniProduct> mlist = new ArrayList<>();

        try {


            JSONArray mObject = response.getJSONArray("books");
            for (int i = 0; i < mObject.length(); i++) {
                JSONObject singObj = mObject.getJSONObject(i);
                mlist.add(new MiniProduct(singObj.getInt(APIUtils.PID)
                        , singObj.getString(APIUtils.PNAME),
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
}
