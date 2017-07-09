package com.bookaholicc.partner.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
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
import com.bookaholicc.partner.utils.BooksHandler;

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

public class BooksActivity extends AppCompatActivity implements Response.ErrorListener, Response.Listener<JSONObject>,BooksAdapter.booksCallback {



    @BindView(R.id.book_list)
    RecyclerView mListView;
    private Context mContext;
    ProgressDialog mDialog;


    @BindView(R.id.btoolbar)
    Toolbar mToolbar;
    @BindView(R.id.books_root)
    RelativeLayout mRoot;
    private String TATAGIG = "BOOKSLIST";

    BooksHandler mBooksHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_fragment);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Your Books");
        }
        showProgressView();
        getbooksList();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.books_page, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
     switch (item.getItemId()){
         case android.R.id.home:
             finish();
             onBackPressed();
             break;
         case R.id.add_book:
             startActivity(new Intent(this,AddBooksActivity.class));
             finish();
             break;
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
        Log.d(TAG, "onResponse: list "+mlist);
        if (mlist != null){
            BooksAdapter mAdapter  = new BooksAdapter(getApplicationContext(),mlist,this);
            mListView.setAdapter(mAdapter);
            mListView.setLayoutManager(new LinearLayoutManager(mContext));
        }
        else{
            // Empty Respose
            if (mRoot != null && mRoot.isShown()){

                mRoot.removeAllViews();
                View mNoOrders = View.inflate(this,R.layout.no_books,mRoot);
                Button mAddBooksButton = (Button) mNoOrders.findViewById(R.id.no_add_books);
                mAddBooksButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addBooksActivity();
                    }
                });


            }

        }



    }

    private void addBooksActivity() {
        // Share
        Intent i = new Intent(this,AddBooksActivity.class);
    }

    private List<MiniProduct> getBooks(JSONObject response) {


        Log.d(TAG, "getBooks: Response "+response.toString());
        List<MiniProduct> mlist = new ArrayList<>();

        try {


            JSONArray mObject = response.getJSONArray("books");
           if (mObject.length() == 0){
               Log.d(TAG, "getBooks: REturning Null");
               return null;
           }
           else{
               for (int i = 0; i < mObject.length(); i++) {
                   JSONObject singObj = mObject.getJSONObject(i);
                   mlist.add(new MiniProduct(singObj.getInt(APIUtils.PID)
                           , singObj.getString(APIUtils.PNAME),
                           singObj.getString(APIUtils.IMAGE_URL),
                           singObj.getInt(APIUtils.AMOUNT)));
               }
               return mlist;
           }

        }
        catch (Exception e){
            Log.d(TAG, "Exception in getBooks: "+e.getLocalizedMessage());
            return null;
        }


    }

    @Override
    public void changeQuantity(final int pid) {
        // Show a Alert Dialog WIndow to
        if (mBooksHandler == null){
            mBooksHandler = BooksHandler.getStorageInstance(mContext);
        }
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.edit_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText edt = (EditText) dialogView.findViewById(R.id.edit1);
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();
                int newQu = Integer.parseInt(edt.getText().toString());
                mBooksHandler.changeQuantity(pid,newQu);

            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();



    }

    @Override
    public void changeAvailblityStatus(int pid, boolean status) {
        if (mBooksHandler == null){
            mBooksHandler = BooksHandler.getStorageInstance(mContext);
        }
        mBooksHandler.changAvailbleStatus(pid,status);
    }

    @Override
    public void seeDetails(int pid) {

    }
}
