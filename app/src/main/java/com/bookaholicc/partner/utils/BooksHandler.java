package com.bookaholicc.partner.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.bookaholicc.partner.StorageHelpers.DataStore;

/**
 * Created by nandhu on 10/7/17.
 */

public class BooksHandler {


    private static BooksHandler mHandler = null;
    private  Context mContext;
    private String TAGI = "BOOKSHANDLER";

    public BooksHandler(Context context) {
        this.mContext = mContext;


    }

    public static synchronized BooksHandler getStorageInstance(Context context) {
        if (mHandler == null) {
            mHandler = new BooksHandler(context);
        }
        return mHandler;
    }



    public  void changeQuantity(int pid, int quantity){
        Log.d(TAGI, "changeQuantity: ");

    }

    public void changAvailbleStatus(int pid, boolean status){

    }









}
